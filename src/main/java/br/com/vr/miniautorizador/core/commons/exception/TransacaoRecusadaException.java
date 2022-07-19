package br.com.vr.miniautorizador.core.commons.exception;

public class TransacaoRecusadaException extends RuntimeException{

    public TransacaoRecusadaException(String message) {
        super(message);
    }
}
