package com.gfrozza.financas.api.controller;

import com.gfrozza.financas.api.dto.AtualizaStatusDTO;
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

import java.util.List;
import java.util.Optional;

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

    @PutMapping("{id}/atualizar-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO atualizaStatusDTO) {
        return this.lancamentoService.obterPorId(id).map( entity -> {
            StatusLancamentoEnum statusSelecionado = StatusLancamentoEnum.valueOf(atualizaStatusDTO.getStatus());
            if(statusSelecionado == null) {
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido.");
            }
            try {
                entity.setStatusLancamentoEnum(statusSelecionado);
                this.lancamentoService.atualizar(entity);
                return ResponseEntity.ok(entity);
            } catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lançamento não encontrado na base da dados.", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return lancamentoService.obterPorId(id).map(entidade -> {
            lancamentoService.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( () ->
            new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "usuario") Long idUsuario) {

        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if(!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado com o código informado.");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamento> lancamentos = lancamentoService.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }

    private Lancamento converter(LancamentoDTO lancamentoDTO){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(lancamentoDTO.getId());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setAno(lancamentoDTO.getAno());
        lancamento.setMes(lancamentoDTO.getMes());
        lancamento.setValor(lancamentoDTO.getValor());

        Usuario usuario = usuarioService.obterPorId(lancamentoDTO.getUsuario()).orElseThrow(
                () -> new RegraNegocioException("Usuário não encontrato."));

        lancamento.setUsuario(usuario);
        if(lancamentoDTO.getTipoLancamentoEnum() != null) {
            lancamento.setTipoLancamentoEnum(TipoLancamentoEnum.valueOf(lancamentoDTO.getTipoLancamentoEnum()));
        }
        if(lancamentoDTO.getStatusLancamentoEnum() != null) {
            lancamento.setStatusLancamentoEnum(StatusLancamentoEnum.valueOf(lancamentoDTO.getStatusLancamentoEnum()));

        }

        return lancamento;
    }
}
