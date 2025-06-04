package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.stormsafe.model.Alerta;

public class AlertaResponseDTO {
    private Long id;
    private Long regiaoId;
    private String mensagem;
    private String nivelCriticidade;
    private LocalDateTime dataHora;
    private String status;

    public AlertaResponseDTO(Alerta alerta) {
        this.id = alerta.getId();
        this.regiaoId = alerta.getRegiao().getId();
        this.mensagem = alerta.getMensagem();
        this.nivelCriticidade = alerta.getNivelCriticidade().name();
        this.dataHora = alerta.getDataHora();
        this.status = alerta.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public Long getRegiaoId() {
        return regiaoId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getNivelCriticidade() {
        return nivelCriticidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }


}
