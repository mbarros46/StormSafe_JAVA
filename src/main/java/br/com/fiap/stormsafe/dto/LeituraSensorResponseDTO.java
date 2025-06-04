package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.stormsafe.model.LeituraSensor;

public class LeituraSensorResponseDTO {
    
private Long id;
    private Long sensorId;
    private LocalDateTime dataHora;
    private Double valor;

    public LeituraSensorResponseDTO(LeituraSensor leitura) {
        this.id = leitura.getId();
        this.sensorId = leitura.getSensor().getId();
        this.dataHora = leitura.getDataHora();
        this.valor = leitura.getValor();
    }

    public Long getId() {
        return id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Double getValor() {
        return valor;
    }
}