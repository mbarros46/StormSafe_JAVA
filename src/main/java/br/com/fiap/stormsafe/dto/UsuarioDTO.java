
package br.com.fiap.stormsafe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long idUsuario;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    private String telefone;

    @NotBlank
    private String tipoUsuario;
}
