package br.com.fiap.stormsafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.UsuarioRepository;


@Service
public class AuthService implements UserDetailsService { // Implementa UserDetailsService em vez de UserDetails
    
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo e-mail
        Usuario usuario = repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado com email: " + username)
        );
        return usuario; // Retorna o usuário que implementa UserDetails
    }
}
