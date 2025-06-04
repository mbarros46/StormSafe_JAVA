package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.StatusSensor;
import br.com.fiap.stormsafe.model.TipoSensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorRequestDTO {
    
@NotNull(message = "Tipo do sensor é obrigatório")
    private TipoSensor tipoSensor;

    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;

    @NotNull(message = "Status do sensor é obrigatório")
    private StatusSensor status;

    private Long rioId; // Para associar o sensor a um rio

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public StatusSensor getStatus() {
        return status;
    }

    public void setStatus(StatusSensor status) {
        this.status = status;
    }

    public Long getRioId() {
        return rioId;
    }

    public void setRioId(Long rioId) {
        this.rioId = rioId;
    }
}
