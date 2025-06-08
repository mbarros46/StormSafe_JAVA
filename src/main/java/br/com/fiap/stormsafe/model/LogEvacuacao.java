package br.com.fiap.stormsafe.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_LOGEVACUACAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LogEvacuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_evacuacao_seq")
    @SequenceGenerator(name = "log_evacuacao_seq", sequenceName = "SEQ_TBL_LOGEVACUACAO", allocationSize = 1)
    @Column(name = "id_log_evacuacao")
    private Long id;

    @NotNull(message = "Campo obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @NotNull(message = "Campo obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;

    @NotNull(message = "Campo obrigatório")
    private LocalDateTime dataHora;

    @NotBlank(message = "Campo obrigatório")
    private String descricao;
}