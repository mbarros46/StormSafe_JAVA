package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "TBL_ROTA_EVACUACAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RotaEvacuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rota_evacuacao_seq")
    @SequenceGenerator(name = "rota_evacuacao_seq", sequenceName = "SEQ_TBL_ROTA_EVACUACAO", allocationSize = 1)
    @Column(name = "id_rota_evacuacao")
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    @Column(columnDefinition = "TEXT")
    private String coordenadas;

    @NotNull(message = "Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusRota status;

    @NotNull(message = "Campo obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;
}
    

