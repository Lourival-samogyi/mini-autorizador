package br.com.vr.miniautorizador.core.commons.exception;

public class CartaoJaExistenteException extends RuntimeException{

    public CartaoJaExistenteException(String s) {
        super(s);
    }
}
