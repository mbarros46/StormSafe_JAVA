package br.com.fiap.stormsafe.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "TBL_ALERTA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @SequenceGenerator(name = "alerta_seq", sequenceName = "SEQ_TBL_ALERTA", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @NotNull(message = "Campo obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;

    @NotBlank(message = "Campo obrigatório")
    private String mensagem;

    @NotNull(message = "Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private NivelCriticidade nivelCriticidade;

    @NotNull(message = "Campo obrigatório")
    private LocalDateTime dataHora;

    @NotNull(message = "Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusAlerta status;
}