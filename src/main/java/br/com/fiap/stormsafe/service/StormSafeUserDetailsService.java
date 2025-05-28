package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StormSafeUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    public StormSafeUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(usuario.getEmail(), usuario.getTipoUsuario(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
