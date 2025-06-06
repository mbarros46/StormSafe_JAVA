package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "TBL_REGIAO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Regiao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regiao_seq")
    @SequenceGenerator(name = "regiao_seq", sequenceName = "SEQ_TBL_REGIAO", allocationSize = 1)
    @Column(name = "id_regiao")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    private Double longitude;

    @OneToMany(mappedBy = "regiao")
    private List<RotaEvacuacao> rotas;
}
