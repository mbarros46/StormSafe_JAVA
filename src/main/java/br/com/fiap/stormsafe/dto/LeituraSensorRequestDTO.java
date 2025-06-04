package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class LeituraSensorRequestDTO {
    
    @NotNull(message = "ID do sensor é obrigatório")
    private Long sensorId;

    @NotNull(message = "Data e hora da leitura são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "Valor da leitura é obrigatório")
    private Double valor;

    public LeituraSensorRequestDTO() {}

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
