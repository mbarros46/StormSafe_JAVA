package br.com.fiap.stormsafe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "TBL_SENSOR")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "SEQ_TBL_SENSOR", allocationSize = 1)
    @Column(name = "id_sensor")
    private Long id;

    @NotNull(message = "Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoSensor tipoSensor;

    @NotBlank(message = "Campo obrigatório")
    private String localizacao;

    @NotNull(message = "Campo obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusSensor status;

    @OneToMany(mappedBy = "sensor")
    private List<LeituraSensor> leituras;

    // Métodos manuais para evitar erro no Render
    public StatusSensor getStatus() {
        return status;
    }

    public void setStatus(StatusSensor status) {
        this.status = status;
    }
}
