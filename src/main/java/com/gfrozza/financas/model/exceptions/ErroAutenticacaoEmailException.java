package com.gfrozza.financas.model.exceptions;

public class ErroAutenticacaoEmailException extends RuntimeException {

    public ErroAutenticacaoEmailException(String mensagem) {
        super(mensagem);
    }
}
