package com.mottu.fleet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class TestSecurityConfig {
    
    @Bean
    @Primary
    public UserDetailsService testUserDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin@neomoto.com")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        
        return new InMemoryUserDetailsManager(admin);
    }
}
