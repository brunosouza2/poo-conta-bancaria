package br.com.banco.conta.Exception;

public class DepositoInvalidoException extends RuntimeException {

    public DepositoInvalidoException(String message) {
        super(message);
    }
}
