package it.uniroma3.icr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)

		.passwordEncoder(new BCryptPasswordEncoder())
		.usersByUsernameQuery("select username,password,TRUE from Student where username = ?")
		.authoritiesByUsernameQuery("select username,role from Student where username = ?")
		;
 
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username,password,TRUE from Administrator where username = ?")
		.authoritiesByUsernameQuery("select username,role from Administrator where username = ?");
  
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
	  
	
	 		
		 http.authorizeRequests()
		 	.antMatchers("/").permitAll()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/user/**").access("hasRole('ROLE_USER')")
			.and()
			.formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/role")
            .failureUrl("/login?error")
			.and()
            .logout()
            .permitAll();
		 
//	        http
//            .sessionManagement()
//                .maximumSessions(1)
//                    .maxSessionsPreventsLogin(true)
//                    .sessionRegistry(sessionRegistry());
		 
		
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/resources/**", "/static/**", "/css/**","/fonts/**","/img/**","/sass/**", "/js/**", "/images/**");
	}
	
//	// Work around https://jira.spring.io/browse/SEC-2855
//	@Bean
//	public SessionRegistry sessionRegistry() {
//	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
//	    return sessionRegistry;
//	}	
	
}