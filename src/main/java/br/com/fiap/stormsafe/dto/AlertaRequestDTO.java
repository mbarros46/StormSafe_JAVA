package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.stormsafe.model.NivelCriticidade;
import br.com.fiap.stormsafe.model.StatusAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlertaRequestDTO {
    
    @NotNull(message = "ID da região é obrigatório")
    private Long regiaoId;

    @NotBlank(message = "Mensagem do alerta é obrigatória")
    private String mensagem;

    @NotNull(message = "Nível de criticidade é obrigatório")
    private NivelCriticidade nivelCriticidade;

    @NotNull(message = "Data e hora do alerta são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "Status do alerta é obrigatório")
    private StatusAlerta status;

    public AlertaRequestDTO() {}

    public Long getRegiaoId() {
        return regiaoId;
    }

    public void setRegiaoId(Long regiaoId) {
        this.regiaoId = regiaoId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public NivelCriticidade getNivelCriticidade() {
        return nivelCriticidade;
    }

    public void setNivelCriticidade(NivelCriticidade nivelCriticidade) {
        this.nivelCriticidade = nivelCriticidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusAlerta getStatus() {
        return status;
    }

    public void setStatus(StatusAlerta status) {
        this.status = status;
    }
}