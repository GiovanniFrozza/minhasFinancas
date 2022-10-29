package com.gfrozza.financas.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;
    private Long Usuario;
    private String tipoLancamentoEnum;
    private String statusLancamentoEnum;
}
