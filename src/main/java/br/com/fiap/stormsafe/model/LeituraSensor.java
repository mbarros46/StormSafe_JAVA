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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_LEITURASENSOR")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitura_sensor_seq")
    @SequenceGenerator(name = "leitura_sensor_seq", sequenceName = "SEQ_TBL_LEITURASENSOR", allocationSize = 1)
    @Column(name = "id_leitura_sensor")
    private Long id;

    @NotNull(message = "Campo obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @NotNull(message = "Campo obrigatório")
    private LocalDateTime dataHora;

    @NotNull(message = "Campo obrigatório")
    private Double valor;
}