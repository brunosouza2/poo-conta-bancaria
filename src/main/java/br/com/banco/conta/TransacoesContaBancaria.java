package br.com.banco.conta;

import java.math.BigDecimal;

public interface TransacoesContaBancaria {

    void depositar(BigDecimal valor);
    void sacar(BigDecimal valor);
    void mostraSaldo();
}
