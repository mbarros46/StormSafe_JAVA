package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.RotaEvacuacao;

public class RotaEvacuacaoResponseDTO {
    
private Long id;
    private String descricao;
    private String coordenadas;
    private String status;
    private Long regiaoId;
    private String nomeRegiao;

    public RotaEvacuacaoResponseDTO(RotaEvacuacao rota) {
        this.id = rota.getId();
        this.descricao = rota.getDescricao();
        this.coordenadas = rota.getCoordenadas();
        this.status = rota.getStatus().name();
        if (rota.getRegiao() != null) {
            this.regiaoId = rota.getRegiao().getId();
            this.nomeRegiao = rota.getRegiao().getNome();
        }
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public String getStatus() {
        return status;
    }

    public Long getRegiaoId() {
        return regiaoId;
    }

    public String getNomeRegiao() {
        return nomeRegiao;
    }
}