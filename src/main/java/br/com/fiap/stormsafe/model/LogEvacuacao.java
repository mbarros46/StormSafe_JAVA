package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_LOGEVACUACAO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class LogEvacuacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logevacuacao_seq")
    @SequenceGenerator(name = "logevacuacao_seq", sequenceName = "SEQ_TBL_LOGEVACUACAO", allocationSize = 1)
    @Column(name = "id_log")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;  // Associação com o Usuario

    @ManyToOne
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;  // Associação com a Regiao

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "Descrição é obrigatória")
    private String descricao;
}