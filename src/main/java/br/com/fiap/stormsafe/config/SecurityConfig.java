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
                        .requestMatchers(
                                "/login/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()
                        // Alteração: Permitir acesso ao POST de /api/usuarios
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() 
                        // Alteração: Protege a rota GET para listar /users (somente ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMIN")
                        // Garante que qualquer outra rota de /usuarios necessite de autenticação
                        .requestMatchers("/api/usuarios/**").authenticated()

                        // Permite o acesso aos alertas para todos os usuários
                        .requestMatchers(HttpMethod.GET, "/alert/**").permitAll() 
                        .requestMatchers("/alert/**").authenticated() // Requer autenticação para modificações nos alertas

                        // Permite o acesso às rotas de evacuação públicas
                        .requestMatchers(HttpMethod.GET, "/evacuationRoute/**").permitAll() 
                        .requestMatchers("/evacuationRoute/**").hasRole("ADMIN") // Somente administradores podem atualizar as rotas

                        // Permite o acesso a informações dos sensores
                        .requestMatchers(HttpMethod.GET, "/sensor/**").permitAll() 
                        .requestMatchers("/sensor/**").authenticated() // Requer autenticação para atualizações nos sensores

                        // Permite que todos vejam localizações de usuários
                        .requestMatchers(HttpMethod.GET, "/userLocation/**").permitAll() 
                        .requestMatchers("/userLocation/**").authenticated() // Requer autenticação para modificar dados de localização dos usuários
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
