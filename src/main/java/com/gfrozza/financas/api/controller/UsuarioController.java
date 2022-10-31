package com.gfrozza.financas.api.controller;

import com.gfrozza.financas.api.dto.UsuarioDTO;
import com.gfrozza.financas.exceptions.ErroAutenticacaoEmailException;
import com.gfrozza.financas.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.service.UsuarioService;
import com.gfrozza.financas.service.impl.LancamentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LancamentoServiceImpl lancamentoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.builder()
                    .email(usuarioDTO.getEmail())
                    .nome(usuarioDTO.getNome())
                    .senha(usuarioDTO.getSenha())
                .build();
            try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO usuarioDTO) {
        try{
            Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return new ResponseEntity<>(usuarioAutenticado, HttpStatus.OK);
        }catch (ErroAutenticacaoEmailException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioService.obterPorId(id);

        if(!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
