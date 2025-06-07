package br.com.fiap.stormsafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TBL_REGIAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Regiao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regiao_seq")
    @SequenceGenerator(name = "regiao_seq", sequenceName = "SEQ_TBL_REGIAO", allocationSize = 1)
    @Column(name = "id_regiao")
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotNull(message = "Campo obrigatório")
    private Double latitude;

    @NotNull(message = "Campo obrigatório")
    private Double longitude;

    @OneToMany(mappedBy = "regiao")
    private List<RotaEvacuacao> rotas;
}
