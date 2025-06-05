package br.com.fiap.stormsafe.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Token;
import br.com.fiap.stormsafe.model.Usuario;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    
    private final Algorithm algorithm = Algorithm.HMAC256("secret"); // Em produção, use variável de ambiente segura

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public Token createToken(Usuario usuario) {
        Instant expiresAt = generateExpirationDate();

        String token = JWT.create()
                .withSubject(usuario.getId().toString())
                .withClaim("email", usuario.getEmail())
                .withClaim("tipoUsuario", usuario.getTipoUsuario().name())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(token, usuario.getEmail());
    }

    public Usuario getUsuarioFromToken(String token) {
        var verifiedToken = JWT.require(algorithm).build().verify(token);

        return Usuario.builder()
                .id(Long.valueOf(verifiedToken.getSubject()))
                .email(verifiedToken.getClaim("email").asString())
                .tipoUsuario(TipoUsuario.valueOf(verifiedToken.getClaim("tipoUsuario").asString()))
                .build();
    }
}
