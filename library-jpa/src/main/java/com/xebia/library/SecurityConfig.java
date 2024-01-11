package com.xebia.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class level annotation indicates that this class contains one or more bean methods
 * annotated with @Bean producing beans to be managed by the Spring container.
 */
@Configuration
public class SecurityConfig {

  /**
   * SecurityFilterChain is a filter chain that is configured to match any request. It is used to
   * configure the security filter chain. `HttpSecurity` is a builder that allows us to configure
   * the security filter chain. It is automatically created and wired by Spring Security.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorize ->
                authorize
                    // Allow all requests to the h2-console.
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                    .permitAll()
                    // All the other requests need to be authenticated.
                    .anyRequest()
                    .authenticated())
        // Disable frame options to allow h2-console to be embedded in an iframe.
        .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
        // Configure that we want to use basic authentication.
        .httpBasic(Customizer.withDefaults());

    return httpSecurity.build();
  }

  /**
   * InMemoryUserDetailsManager is an implementation of UserDetailsService that stores users in
   * memory. We can also use a database to store users.
   */
  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    UserDetails jerry =
        /**
         * DO NOT USE THIS IN PRODUCTION with plaintext passwords, see {@link
         * User#withDefaultPasswordEncoder()}
         */
        User.withDefaultPasswordEncoder()
            .username("jerry")
            .password("1234")
            .roles("LIBRARIAN")
            .build();
    UserDetails tom =
        User.withDefaultPasswordEncoder()
            .username("tom")
            .password("test1234")
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(jerry, tom);
  }
}
