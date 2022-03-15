package com.groupproject.nstu.cookbook.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/dish/**").hasRole( "ADMIN")
                .antMatchers("/cuisine/**").hasRole( "ADMIN")
                .antMatchers("/ingredient/**").hasRole( "ADMIN")
                .antMatchers("/dishType/**").hasRole( "ADMIN")
                .antMatchers("/dishContent/**").hasRole( "ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                //.antMatchers("/**").permitAll()
                .and().formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                .password("admin")
                .authorities("ROLE_ADMIN");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://localhost:80",
                "http://localhost",
                "http://example.ru",
                "https://example.ru"));
        configuration.setAllowedMethods(
                Collections.unmodifiableList(
                        Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                Collections.unmodifiableList(
                        Arrays.asList("Authorization", "Cache-Control", "Content-Type")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
