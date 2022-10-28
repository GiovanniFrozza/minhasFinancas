package com.gfrozza.financas.service;

import com.gfrozza.financas.model.entity.Lancamento;
import com.gfrozza.financas.model.enums.StatusLancamentoEnum;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamentoEnum statusLancamentoEnum);

    void validar(Lancamento lancamento);
}