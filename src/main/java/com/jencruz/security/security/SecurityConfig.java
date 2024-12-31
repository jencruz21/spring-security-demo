package com.jencruz.security.security;

import com.jencruz.security.filter.AuthenticationFilter;
import com.jencruz.security.filter.ExceptionHandlerFilter;
import com.jencruz.security.filter.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private CustomAuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/api/v1/user/*").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(this.getAuthenticationFilter())
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionConfigure -> sessionConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    public AuthenticationFilter getAuthenticationFilter() {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/authenticate");

        return authenticationFilter;
    }
}
