package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.Sensor;

public class SensorResponseDTO {
    
    private Long id;
    private String tipoSensor;
    private String localizacao;
    private String status;

    public SensorResponseDTO(Sensor sensor) {
        this.id = sensor.getId();
        this.tipoSensor = sensor.getTipoSensor().name();
        this.localizacao = sensor.getLocalizacao();
        this.status = sensor.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getStatus() {
        return status;
    }
}
