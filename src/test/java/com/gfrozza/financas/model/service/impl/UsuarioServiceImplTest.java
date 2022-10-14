package com.gfrozza.financas.model.service.impl;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.exceptions.ErroAutenticacaoEmailException;
import com.gfrozza.financas.exceptions.ErroAutenticacaoSenhaException;
import com.gfrozza.financas.exceptions.RegraNegocioException;
import com.gfrozza.financas.model.repository.UsuarioRepository;
import com.gfrozza.financas.model.stub.UsuarioStub;
import com.gfrozza.financas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @SpyBean
    UsuarioServiceImpl usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;

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

    @Test(expected = RegraNegocioException.class)
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail("usuario@email.com");

        usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
    }

    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuario));

        var retorno = usuarioService.autenticar("usuario@email.com", "123456");
        Assertions.assertThat(retorno).isNotNull();
    }

    @Test(expected = ErroAutenticacaoEmailException.class)
    public void emailInformadoNaoEncontrado() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());
        usuarioService.autenticar("us@email.com", "123456");
    }

    @Test(expected = ErroAutenticacaoSenhaException.class)
    public void senhaInformadaIncorreta() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(UsuarioStub.usuarioCreate()));
        usuarioService.autenticar("usuario@email.com", "w");
    }

    @Test
    public void salvarUsuarioTest() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        Mockito.doNothing().when(usuarioService).validarEmail(anyString());
        when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        var resultado = usuarioService.salvarUsuario(usuario);
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getNome()).isEqualTo("Usuario");
        Assertions.assertThat(resultado.getSenha()).isEqualTo("123456");
        Assertions.assertThat(resultado.getEmail()).isEqualTo("usuario@email.com");

    }
}
