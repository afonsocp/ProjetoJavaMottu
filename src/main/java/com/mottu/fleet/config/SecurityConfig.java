package com.mottu.fleet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // === PÁGINAS PÚBLICAS ===
                .requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                
                // === ADMIN - Acesso total ===
                .requestMatchers("/admin/**", "/usuarios/**").hasRole("ADMIN")
                
                // === AÇÕES DE EXCLUSÃO - Apenas ADMIN e GERENTE ===
                .requestMatchers("/motos/*/delete", "/motoristas/*/delete", "/patios/*/delete").hasAnyRole("ADMIN", "GERENTE")
                
                // === CRIAÇÃO DE REGISTROS - ADMIN e GERENTE ===
                .requestMatchers("/motos/novo", "/motoristas/novo", "/patios/novo").hasAnyRole("ADMIN", "GERENTE")
                
                // === VISUALIZAÇÃO E EDIÇÃO - Todos os perfis ===
                .requestMatchers("/motos/**", "/motoristas/**", "/patios/**").hasAnyRole("ADMIN", "GERENTE", "OPERADOR")
                
                // === FLUXOS OPERACIONAIS - Todos os perfis ===
                .requestMatchers("/alocacoes/**", "/manutencoes/**").hasAnyRole("ADMIN", "GERENTE", "OPERADOR")
                
                // === RELATÓRIOS - ADMIN e GERENTE ===
                .requestMatchers("/relatorios/**").hasAnyRole("ADMIN", "GERENTE")
                
                // === TODAS AS OUTRAS REQUISIÇÕES ===
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/acesso-negado")
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Habilitado para segurança
            .authenticationProvider(authenticationProvider(userDetailsService));
        
        return http.build();
    }
}
