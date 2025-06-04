package br.com.fiap.stormsafe.dto;

import br.com.fiap.stormsafe.model.Regiao;

public class RegiaoResponseDTO {
    

    private Long id;
    private String nome;
    private Double latitude;
    private Double longitude;

    public RegiaoResponseDTO(Regiao regiao) {
        this.id = regiao.getId();
        this.nome = regiao.getNome();
        this.latitude = regiao.getLatitude();
        this.longitude = regiao.getLongitude();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}