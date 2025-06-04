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
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers("/users/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/alert/**").permitAll() // Permite o acesso aos alertas para todos os usuários
                        .requestMatchers("/alert/**").authenticated() // Requer autenticação para modificações nos alertas

                        .requestMatchers(HttpMethod.GET, "/evacuationRoute/**").permitAll() // Permite o acesso às rotas de evacuação públicas
                        .requestMatchers("/evacuationRoute/**").hasRole("ADMIN") // Somente administradores podem atualizar as rotas

                        .requestMatchers(HttpMethod.GET, "/sensor/**").permitAll() // Permite o acesso a informações dos sensores
                        .requestMatchers("/sensor/**").authenticated() // Requer autenticação para atualizações nos sensores

                        .requestMatchers(HttpMethod.GET, "/userLocation/**").permitAll() // Permite que todos vejam localizações de usuários
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
