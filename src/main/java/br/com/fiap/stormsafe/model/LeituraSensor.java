package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_LEITURA_SENSOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class LeituraSensor {
    

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitura_sensor_seq")
    @SequenceGenerator(name = "leitura_sensor_seq", sequenceName = "SEQ_TBL_LEITURA_SENSOR", allocationSize = 1)
    @Column(name = "id_leitura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sensor", nullable = false)
    private Sensor sensor;  // Aqui a associação é com o objeto Sensor

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "Valor da leitura é obrigatório")
    private Double valor;

}