package com.gfrozza.financas.entity;

import com.gfrozza.financas.enums.StatusLancamentoEnum;
import com.gfrozza.financas.enums.TipoLancamentoEnum;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Lancamento {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;
    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamentoEnum tipoLancamentoEnum;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamentoEnum statusLancamentoEnum;
}
