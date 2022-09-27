package com.gfrozza.financas.model.controller;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @GetMapping(value = "/findAll")
    @ResponseBody
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }
}
