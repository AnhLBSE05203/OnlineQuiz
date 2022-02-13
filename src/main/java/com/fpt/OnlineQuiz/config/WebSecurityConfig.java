package com.fpt.OnlineQuiz.config;

import java.util.List;

import javax.sql.DataSource;

import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
public class WebSecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	};
	@Configuration
	@Order(2)
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AccountService accountService;
        @Autowired
        private RoleService roleService;
        @Autowired
        private DataSource dataSource;
		public UserSecurityConfig(){
			super();
		}
        /**
         * PersistentTokenRepository for Remember-me function
         * @return
         */
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
            db.setDataSource(dataSource);
            return db;
        }
		/**
		 * Configuration for Authentication & Authorization
		 *
		 * @param http http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			sharedConfigure(http);
			configureForRole(roleService, Constants.ROLE_USER, http);
			configureForRole(roleService, Constants.ROLE_EXPERT, http);
			configureForRole(roleService, Constants.ROLE_SALES, http);

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
					.tokenValiditySeconds(24 * 60 * 60)
					.key("uniqueAndSecret"); // 24h
		}
	}

	@Configuration
	@Order(1)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AccountService accountService;
        @Autowired
        private RoleService roleService;
        @Autowired
        private DataSource dataSource;

		public AdminSecurityConfig(){
			super();
		}
        /**
         * PersistentTokenRepository for Remember-me function
         * @return
         */
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
            db.setDataSource(dataSource);
            return db;
        }
		/**
		 * Configuration for Authentication & Authorization
		 * @param http http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			sharedConfigure(http);
			configureForRole(roleService, Constants.ROLE_ADMIN, http);

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
					.tokenValiditySeconds(24 * 60 * 60); // 24h
		}

	}
	protected static void sharedConfigure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//avoid CookieTheftException
		http.authorizeRequests().antMatchers(
				"/js/**", "/assets/**", "/fonts/**",
				"/css/**", "/scss/**", "/syntax-highlighter/**",
				"/img/**", "/sql/**").permitAll();
	}

	public static void configureForRole(RoleService roleService, String roleName, HttpSecurity http) throws Exception {
		Role role = roleService.findRoleByName(roleName);

		List<Screen> screens = role.getScreens();
		for (Screen screen : screens) {
			String link = screen.getLink();
			http.antMatcher(link).authorizeRequests().anyRequest().hasAuthority(roleName);
		}
	}
}
