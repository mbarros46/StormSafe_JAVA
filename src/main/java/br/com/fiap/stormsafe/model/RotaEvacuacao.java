
package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RotaEvacuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRota;

    @NotBlank
    private String descricao;

    @NotBlank
    private String statusRota;

    @ManyToOne
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;
}
