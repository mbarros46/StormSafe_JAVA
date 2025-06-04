package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.StatusRota;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RotaEvacuacaoRequestDTO {
    
    @NotBlank(message = "Descrição da rota é obrigatória")
    private String descricao;

    @NotBlank(message = "Coordenadas da rota são obrigatórias")
    private String coordenadas; // JSON string das coordenadas

    @NotNull(message = "Status da rota é obrigatório")
    private StatusRota status;

    @NotNull(message = "Id da região é obrigatório")
    private Long regiaoId;

    public RotaEvacuacaoRequestDTO() {}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public StatusRota getStatus() {
        return status;
    }

    public void setStatus(StatusRota status) {
        this.status = status;
    }

    public Long getRegiaoId() {
        return regiaoId;
    }

    public void setRegiaoId(Long regiaoId) {
        this.regiaoId = regiaoId;
    }
}
