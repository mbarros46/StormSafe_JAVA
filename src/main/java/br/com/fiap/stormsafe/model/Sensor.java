package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TBL_SENSOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Sensor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "SEQ_TBL_SENSOR", allocationSize = 1)
    @Column(name = "id_sensor")
    private Long id;

    @NotNull(message = "Tipo do sensor é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoSensor tipoSensor;

    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;

    @NotNull(message = "Status do sensor é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusSensor status;

    @OneToMany(mappedBy = "sensor")
    private List<LeituraSensor> leituras;


}
