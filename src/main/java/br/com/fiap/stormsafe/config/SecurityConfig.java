package br.com.fiap.stormsafe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter){
        this.authFilter = authFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Acesso livre para Swagger e login
                        .requestMatchers(
                                "/login/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()

                        // Permite cadastro de novos usuários sem autenticação
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // Permite ADMIN e USER verem a lista de usuários (ajuste principal)
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()

                        // Outras rotas de usuários requerem autenticação
                        .requestMatchers("/api/usuarios/**").authenticated()

                        // Alertas: leitura pública, edição autenticada
                        .requestMatchers(HttpMethod.GET, "/alert/**").permitAll()
                        .requestMatchers("/alert/**").authenticated()

                        // Rotas de evacuação: leitura pública, edição só para ADMIN
                        .requestMatchers(HttpMethod.GET, "/evacuationRoute/**").permitAll()
                        .requestMatchers("/evacuationRoute/**").hasRole("ADMIN")

                        // Sensores: leitura pública, edição autenticada
                        .requestMatchers(HttpMethod.GET, "/sensor/**").permitAll()
                        .requestMatchers("/sensor/**").authenticated()

                        // Localização de usuários: leitura pública, edição autenticada
                        .requestMatchers(HttpMethod.GET, "/userLocation/**").permitAll()
                        .requestMatchers("/userLocation/**").authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
