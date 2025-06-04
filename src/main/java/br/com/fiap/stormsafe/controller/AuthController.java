package br.com.fiap.stormsafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.stormsafe.model.Credentials;
import br.com.fiap.stormsafe.model.Token;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.service.TokenService;


@RestController
public class AuthController {

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        var auth = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var user = (Usuario) authenticationManager.authenticate(auth).getPrincipal();

        return tokenService.createToken(user);
    }
}