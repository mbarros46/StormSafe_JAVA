package br.com.fiap.stormsafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.stormsafe.model.LogEvacuacao;

public class LogEvacuacaoResponseDTO  {
    
    private Long id;
    private Long usuarioId;
    private Long regiaoId;
    private LocalDateTime dataHora;
    private String descricao;

    public LogEvacuacaoResponseDTO(LogEvacuacao log) {
        this.id = log.getId();
        this.usuarioId = log.getUsuario() != null ? log.getUsuario().getId() : null;
        this.regiaoId = log.getRegiao() != null ? log.getRegiao().getId() : null;
        this.dataHora = log.getDataHora();
        this.descricao = log.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getRegiaoId() {
        return regiaoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }
}