package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.dto.LoginRequest;
import br.com.fiap.stormsafe.dto.LoginResponse;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.UsuarioRepository;
import br.com.fiap.stormsafe.config.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UsuarioRepository usuarioRepository, JWTUtil jwtUtil, BCryptPasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    public LoginResponse authenticate(LoginRequest request) {
        Usuario user = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(request.getSenha(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token);
    }
}
