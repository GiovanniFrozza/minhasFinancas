package com.gfrozza.financas.model.service.impl;


import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.model.service.UsuarioService;
import com.gfrozza.financas.model.stub.UsuarioStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void validarEmailTest() {
        usuarioRepository.deleteAll();
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void validaEmailJaCadastradoTest() {
        Usuario usuario = UsuarioStub.usuarioCreate();
        usuarioRepository.save(usuario);
        usuarioService.validarEmail(usuario.getEmail());
    }

}
