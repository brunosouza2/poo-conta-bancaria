package br.com.banco.conta.Exception;

public class LimiteDeSaqueAtingidoException extends RuntimeException {

    public LimiteDeSaqueAtingidoException(String message) {
        super(message);
    }
}
