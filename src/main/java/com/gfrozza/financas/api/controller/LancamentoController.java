package com.gfrozza.financas.api.controller;

import com.gfrozza.financas.api.dto.LancamentoDTO;
import com.gfrozza.financas.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.entity.Lancamento;
import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.enums.StatusLancamentoEnum;
import com.gfrozza.financas.model.enums.TipoLancamentoEnum;
import com.gfrozza.financas.service.LancamentoService;
import com.gfrozza.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){
        try{
            return new ResponseEntity(this.lancamentoService.salvar(converter(lancamentoDTO)), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO lancamentoDTO) {
        return this.lancamentoService.obterPorId(id).map(entity -> {
            try{
                Lancamento lancamento = converter(lancamentoDTO);
                lancamento.setId(entity.getId());
                lancamentoService.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lançamento não encontrado na base da dados.", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO lancamentoDTO){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(lancamentoDTO.getId());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setAno(lancamentoDTO.getAno());
        lancamento.setMes(lancamentoDTO.getMes());
        lancamento.setValor(lancamentoDTO.getValor());
        lancamento.setUsuario(usuarioService.obterPorId(lancamentoDTO.getId()).orElseThrow(
                () -> new RegraNegocioException("Usuário não encontrato.")));
        lancamento.setTipoLancamentoEnum(TipoLancamentoEnum.valueOf(lancamentoDTO.getTipoLancamentoEnum()));
        lancamento.setStatusLancamentoEnum(StatusLancamentoEnum.valueOf(lancamentoDTO.getStatusLancamentoEnum()));
        return lancamento;
    }
}
