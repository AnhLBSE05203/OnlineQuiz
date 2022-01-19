package com.fpt.OnlineQuiz.config;

import java.util.List;

import javax.sql.DataSource;

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

import com.fpt.OnlineQuiz.model.Role;
import com.fpt.OnlineQuiz.model.Screen;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.RoleService;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig {
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DataSource dataSource;

	/**
	 * BCryptPasswordEncoder Bean
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	/**
	 * PersistentTokenRepository for Remember-me function
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Configuration
	@Order(1)
	public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

		/**
		 * Configuration for Authentication & Authorization
		 * @param http http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			List<Role> roles = roleService.findAll();
			if (roles != null) {
				for (Role role : roles) {
					List<Screen> screens = role.getScreens();
					String roleName = role.getName();
					for (Screen screen : screens) {
						String link = screen.getLink();
						http.authorizeRequests().antMatchers(link).hasAuthority(roleName);
					}
				}
			}
			http.authorizeRequests()
					.anyRequest().permitAll()
					.and().formLogin()
					.loginProcessingUrl(Constants.LINK_LOGIN_PROCESS)
					.failureUrl(Constants.LINK_LOGIN_FAILURE)
					.defaultSuccessUrl(Constants.LINK_HOME)
					.loginPage(Constants.LINK_LOGIN)
					.and()
					.exceptionHandling().accessDeniedPage(Constants.LINK_ACCESS_DENIED)
					.and()
					.logout().logoutRequestMatcher(new AntPathRequestMatcher(Constants.LINK_LOGOUT))
					.deleteCookies("remember-me", "JSESSIONID")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutSuccessUrl(Constants.LINK_HOME)
					.and()
					.rememberMe().tokenRepository(persistentTokenRepository()) //
					.tokenValiditySeconds(24 * 60 * 60); // 24h
		}
	}

	@Configuration
	public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		/**
		 * Configuration for Authentication & Authorization
		 * @param http http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			List<Role> roles = roleService.findAll();
			if (roles != null) {
				for (Role role : roles) {
					List<Screen> screens = role.getScreens();
					String roleName = role.getName();
					for (Screen screen : screens) {
						String link = screen.getLink();
						http.authorizeRequests().antMatchers(link).hasAuthority(roleName);
					}
				}
			}
			http.authorizeRequests()
					.anyRequest().permitAll()
					.and().formLogin()
					.loginProcessingUrl(Constants.LINK_ADMIN_LOGIN_PROCESS)
					.failureUrl(Constants.LINK_LOGIN_FAILURE)
					.defaultSuccessUrl(Constants.LINK_ADMIN_DASHBOARD)
					.loginPage(Constants.LINK_ADMIN_LOGIN)
					.and()
					.exceptionHandling().accessDeniedPage(Constants.LINK_ACCESS_DENIED)
					.and()
					.logout().logoutRequestMatcher(new AntPathRequestMatcher(Constants.LINK_LOGOUT))
					.deleteCookies("remember-me", "JSESSIONID")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutSuccessUrl(Constants.LINK_ADMIN_LOGIN)
					.and()
					.rememberMe().tokenRepository(persistentTokenRepository()) //
					.tokenValiditySeconds(24 * 60 * 60); // 24h
		}

	}

}
