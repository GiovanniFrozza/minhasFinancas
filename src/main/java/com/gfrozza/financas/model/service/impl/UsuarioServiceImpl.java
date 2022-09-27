package com.gfrozza.financas.model.service.impl;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.exceptions.ErroAutenticacaoException;
import com.gfrozza.financas.model.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.apache.tomcat.jni.Proc.fork;

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

        if(!usuario.isPresent()) {
            throw new ErroAutenticacaoException("Usuário não encontrado.");
        }
        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha inválida.");
        }

        /*
        usuario.map(x -> x.getSenha().equals(senha))
               .orElseThrow(() -> new ErroAutenticacaoException("Senha inválida"));

         Testar
         */

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

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
