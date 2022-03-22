package com.fpt.OnlineQuiz.config;

import com.fpt.OnlineQuiz.model.Role;
import com.fpt.OnlineQuiz.model.Screen;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.RoleService;
import com.fpt.OnlineQuiz.service.ScreenService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ScreenService screenService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * PersistentTokenRepository for Remember-me function
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Configuration
    @Order(2)
    public class UserSecurityConfig extends WebSecurityConfigurerAdapter {


        /**
         * Configuration for Authentication & Authorization
         *
         * @param http http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            sharedConfigure(http);
            http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();

            http.formLogin().permitAll()
                    .loginProcessingUrl(Constants.LINK_ACCOUNT_LOGIN_PROCESS)
                    .failureUrl(Constants.LINK_ACCOUNT_LOGIN_FAILURE)
                    .defaultSuccessUrl(Constants.LINK_HOME)
                    .loginPage(Constants.LINK_ACCOUNT_CONTROLLER + Constants.LINK_LOGIN)
                    .and()
                    .exceptionHandling().accessDeniedPage(Constants.LINK_ACCOUNT_CONTROLLER + Constants.LINK_LOGIN)
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher(Constants.LINK_ACCOUNT_LOGOUT))
                    .deleteCookies("remember-me", "JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl(Constants.LINK_HOME)
                    .and()
                    .rememberMe().tokenRepository(persistentTokenRepository()) //
                    .tokenValiditySeconds(24 * 60 * 60).key(Constants.REMEMBER_ME_KEY); // 24h
        }
    }

    @Configuration
    @Order(1)
    public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        /**
         * Configuration for Authentication & Authorization
         *
         * @param http http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            sharedConfigure(http);

            //get all screens
            List<Screen> screens = screenService.findAll();
            Map<String, ArrayList<String>> map = new HashMap<>();
            for (Screen screen : screens) {
                //add role array of the screen
                map.put(screen.getLink(), new ArrayList<>());
                ArrayList<String> roleNames = map.get(screen.getLink());
                for (Role role : screen.getRoles()) {
                    roleNames = map.get(screen.getLink());
                    roleNames.add(role.getName());
                }
                String[] roleArray = roleNames.toArray(new String[0]);
                for (int i = 0; i < roleArray.length; i++) {
                    System.out.print(roleArray[i] + " ");
                }
                System.out.println(screen.getLink());
                if (roleArray.length > 0) {
                    http.antMatcher("/admin/**").authorizeRequests().antMatchers(screen.getLink()).hasAnyAuthority(roleArray);
                }
            }
            http.antMatcher("/admin/**").authorizeRequests().antMatchers("/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN);
            //note: more specific 1st, hasAuthority() gets overridden
            configureManagementRoles(http);

            //warning: call anyRequest() only once
            //note: [http.antMatcher()] needed to configure http for multiple login
        }

    }

    protected void sharedConfigure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //avoid CookieTheftException
        http.authorizeRequests().antMatchers(
                "/js/**", "/assets/**", "/fonts/**",
                "/css/**", "/scss/**", "/syntax-highlighter/**",
                "/img/**", "/sql/**").permitAll();
        http.authorizeRequests().antMatchers("/admin/forget_pass").permitAll();
        http.authorizeRequests().antMatchers("/admin/forget_pass_action").permitAll();
        http.authorizeRequests().antMatchers("/admin/resetPassword").permitAll();
        http.sessionManagement().maximumSessions(1);
    }

    public void configureManagementRoles(HttpSecurity http) throws Exception {
        http.formLogin().permitAll()
                .loginProcessingUrl(Constants.LINK_ADMIN_LOGIN)
                .failureUrl(Constants.LINK_ADMIN_LOGIN_FAILURE)
                .defaultSuccessUrl(Constants.LINK_ADMIN_DASHBOARD)
                .loginPage(Constants.LINK_ADMIN_LOGIN)
                .and()
                .exceptionHandling().accessDeniedPage(Constants.LINK_ADMIN_LOGIN)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(Constants.LINK_ADMIN_LOGOUT))
                .deleteCookies("remember-me", "JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl(Constants.LINK_ADMIN_LOGIN)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository()) //
                .tokenValiditySeconds(24 * 60 * 60).key(Constants.REMEMBER_ME_KEY); // 24h
    }
}
