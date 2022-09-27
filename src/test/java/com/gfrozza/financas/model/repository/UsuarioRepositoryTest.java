package com.gfrozza.financas.model.repository;

import com.gfrozza.financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void existsByEmailTest() {
        Usuario usuario = Usuario.builder()
                .nome("Usuario")
                .email("usuario@email.com")
                .build();
        usuarioRepository.save(usuario);

        boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
        Assertions.assertThat(resultado).isTrue();
    }

    @Test
    public void existsByEmailIsValidTest() {
        usuarioRepository.deleteAll();

        boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");

        Assertions.assertThat(resultado).isFalse();
    }
}
