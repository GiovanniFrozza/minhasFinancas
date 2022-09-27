package com.gfrozza.financas.model.repository;

import com.gfrozza.financas.model.entity.Usuario;
import com.gfrozza.financas.model.stub.UsuarioStub;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void existsByEmailTest() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        entityManager.persist(usuario);

        boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
        Assertions.assertThat(resultado).isTrue();
    }

    @Test
    public void existsByEmailIsValidTest() {
        boolean resultado = usuarioRepository.existsByEmail("usuario@email.com");
        Assertions.assertThat(resultado).isFalse();
    }

    @Test
    public void saveTest() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void findByEmailTest() {
        Usuario usuario = UsuarioStub.usuarioCreate();

        entityManager.persist(usuario);

        Optional<Usuario> retorno = usuarioRepository.findByEmail("usuario@email.com");
        Assertions.assertThat(retorno.isPresent()).isTrue();
    }

    @Test
    public void retornaVazioAoBuscarUsuarioPorEmailTest() {
        Optional<Usuario> retorno = usuarioRepository.findByEmail("usuario@email.com");
        Assertions.assertThat(retorno.isPresent()).isFalse();
    }

}
