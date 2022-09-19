package com.gfrozza.financas.model.service.impl;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);
        if(existe) {
            throw new RegraNegocioException("Já existe um usuario cadastrado com este email.");
        }
    }
}
