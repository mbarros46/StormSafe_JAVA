package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.stormsafe.model.NivelCriticidade;
import br.com.fiap.stormsafe.model.StatusAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

/**
 * DTO para criação de um novo alerta.
 * Contém os dados necessários para registrar um alerta no sistema.
 */
public class AlertaRequestDTO {

    /**
     * ID da região relacionada ao alerta.
     */
    @NotNull(message = "ID da região é obrigatório")
    private Long regiaoId;

    /**
     * Mensagem descritiva do alerta.
     */
    @NotBlank(message = "Mensagem do alerta é obrigatória")
    private String mensagem;

    /**
     * Nível de criticidade do alerta.
     */
    @NotNull(message = "Nível de criticidade é obrigatório")
    private NivelCriticidade nivelCriticidade;

    /**
     * Data e hora do alerta.
     * Deve ser no passado ou presente.
     */
    @NotNull(message = "Data e hora do alerta são obrigatórias")
    @PastOrPresent(message = "A data e hora do alerta não podem estar no futuro")
    private LocalDateTime dataHora;

    /**
     * Status atual do alerta.
     */
    @NotNull(message = "Status do alerta é obrigatório")
    private StatusAlerta status;

    // Construtor padrão necessário para frameworks como Spring
    public AlertaRequestDTO() {}

    // Getters e Setters
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
