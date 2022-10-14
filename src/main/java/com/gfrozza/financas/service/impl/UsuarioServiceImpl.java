package com.gfrozza.financas.service.impl;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.exceptions.ErroAutenticacaoEmailException;
import com.gfrozza.financas.exceptions.ErroAutenticacaoSenhaException;
import com.gfrozza.financas.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if(usuario.isEmpty()) {
            throw new ErroAutenticacaoEmailException("Usuário não encontrado.");
        }

        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoSenhaException("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);
        if(existe) {
            throw new RegraNegocioException("Já existe um usuario cadastrado com este email.");
        }
    }

}
