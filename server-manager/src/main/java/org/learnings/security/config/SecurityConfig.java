package org.learnings.security.config;

import org.learnings.security.login.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration 
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER) 
class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {    
     http.csrf().disable()
     .authorizeRequests()
     .anyRequest().permitAll();
     }*/
    

	//This method is called after configuring AuthenticationManagerBuilder.
    @Override 
    protected void configure(HttpSecurity http) throws Exception {

    	//Determine which URL is valid for authentication.
        http.csrf().disable().authorizeRequests()
        .antMatchers("/login.html").permitAll()
        .antMatchers("/js/*").permitAll()
        .antMatchers("/css/*").permitAll()
        .anyRequest()
        	.fullyAuthenticated();

        // configuration for form login.
        // login page
        // login processing url
        // failure handler
        // success handler
        // who is permitted
        //
        http.formLogin().loginProcessingUrl("/login").loginPage("/login.html")
        .failureHandler((request, response, authentication) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        })
        .successHandler((request, response, authentication) -> {
        	response.setStatus(HttpStatus.OK.value());
        })
        .permitAll().and().httpBasic();

        // configuration for logout
        http.logout().logoutUrl("/logout")
        .logoutSuccessHandler((request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
        })
        .permitAll();

    }
    
    
    @Override 
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Returns the object manages user authentication.
    	//UserDetailsService implements AuthenticationManagerBuilder
    	//Spring calling this method first at its initialization
    	auth.userDetailsService(userDetailsService);
    }
    

/*    @Override 
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }*/
}