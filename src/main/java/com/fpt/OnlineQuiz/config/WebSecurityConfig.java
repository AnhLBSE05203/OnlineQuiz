package com.fpt.OnlineQuiz.config;

import com.fpt.OnlineQuiz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/manage", "/manage/order", "/manage/book")
                .hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOYEE")
                .antMatchers("/manage/employee")
                .hasAuthority("ROLE_MANAGER")
                .anyRequest().permitAll()
                .and().formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login")
                .defaultSuccessUrl("/home")
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("remember-me", "JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/home")
                .and()
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(24 * 60 * 60); // 24h
    }
}
