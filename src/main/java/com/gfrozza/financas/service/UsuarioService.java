package com.gfrozza.financas.service;

import com.gfrozza.financas.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);


}
