package br.com.banco.conta.Exception;

public class SaqueInvalidoException extends RuntimeException {

    public SaqueInvalidoException(String message) {
        super(message);
    }
}
