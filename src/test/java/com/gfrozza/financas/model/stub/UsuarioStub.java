package com.gfrozza.financas.model.stub;

import com.gfrozza.financas.model.entity.Usuario;

public class UsuarioStub {
    public static Usuario usuarioCreate() {
        return Usuario.builder()
                    .nome("Usuario")
                    .email("usuario@email.com")
                    .senha("123456")
                .build();
    }
}
