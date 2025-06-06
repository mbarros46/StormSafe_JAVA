package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "TBL_ROTAEVACUACAO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class RotaEvacuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rota_seq")
    @SequenceGenerator(name = "rota_seq", sequenceName = "SEQ_TBL_ROTAEVACUACAO", allocationSize = 1)
    @Column(name = "id_rota")
    private Long id;

    @NotNull(message = "Região é obrigatória")
    @ManyToOne
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "Coordenadas são obrigatórias")
    @Column(columnDefinition = "TEXT")
    private String coordenadas;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusRota status;
}
    

