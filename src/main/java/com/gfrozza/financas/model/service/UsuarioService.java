package com.gfrozza.financas.model.service;

import com.gfrozza.financas.model.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    List<Usuario> findAll();


}
