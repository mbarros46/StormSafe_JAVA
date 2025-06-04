package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.TipoUsuario;

public class LoginResponseDTO {
    
    private String token;
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;

    public LoginResponseDTO(String token, String nome, String email, TipoUsuario tipoUsuario) {
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

}
