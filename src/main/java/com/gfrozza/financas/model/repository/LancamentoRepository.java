package com.gfrozza.financas.model.repository;

import com.gfrozza.financas.model.entity.Lancamento;
import com.gfrozza.financas.model.enums.TipoLancamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = " select sum(l.valor) from Lancamento l join l.usuario u " +
            "where u.id =:idUsuario and " +
            "l.tipoLancamentoEnum =:tipoLancamentoEnum " +
            "group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario,
                                                   @Param("tipoLancamentoEnum") TipoLancamentoEnum tipoLancamentoEnum);
}
