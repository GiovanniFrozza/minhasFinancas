package com.gfrozza.financas.model.service.impl;


import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.model.service.UsuarioService;
import com.gfrozza.financas.model.stub.UsuarioStub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    UsuarioService usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test
    public void validarEmailTest() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void validaEmailJaCadastradoTest() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);
        usuarioService.validarEmail("email@email.com");
    }

}
