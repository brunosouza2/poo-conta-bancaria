package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.DepositoInvalidoException;
import br.com.banco.conta.Exception.SaldoInsuficienteException;
import br.com.banco.conta.Exception.SaqueInvalidoException;
import br.com.banco.conta.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Objects;

import static br.com.banco.conta.util.BigDecimalUtil.*;

public abstract class Conta implements TransacoesContaBancaria {

    private Banco banco;
    private String titular;
    private int agencia = 0;
    private int numero = 0;
    private BigDecimal saldo = BigDecimal.ZERO;

    public Conta(Banco banco, String titular) {
        this.banco = banco;
        this.titular = titular;
        agencia++;
        numero++;
        this.banco.adicionarConta(this);
    }

    public Banco getBanco() {
        return banco;
    }

    public String getTitular() {
        return titular;
    }


    public int getAgencia() {
        return agencia;
    }


    public int getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return padronizarCasasDecimais(saldo);
    }

    protected void setBanco(Banco banco) {
        this.banco = banco;
    }

    protected void setTitular(String titular) {
        this.titular = titular;
    }

    protected void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    protected void setNumero(int numero) {
        this.numero = numero;
    }

    protected void setSaldo(BigDecimal saldo) {
        this.saldo = padronizarCasasDecimais(saldo);
    }

    @Override
    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0 || valor.compareTo(BigDecimal.ZERO) == 0) {
            throw new DepositoInvalidoException("O valor do depósito deve ser maior que 0.");
        }
        saldo = padronizarCasasDecimais(saldo.add(valor));
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(saldo) > 0) throw new SaldoInsuficienteException("O saldo é insuficiente para operação.");
        if (valor.compareTo(BigDecimal.ZERO) == 0) throw new SaqueInvalidoException("O valor para saque é inválido.");
        saldo = padronizarCasasDecimais(saldo.subtract(valor));
    }

    @Override
    public void mostraSaldo() {
        System.out.printf("Saldo: %.1f", getSaldo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conta conta)) return false;
        return getAgencia() == conta.getAgencia() && getNumero() == conta.getNumero() && Objects.equals(getTitular(), conta.getTitular());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitular(), getAgencia(), getNumero());
    }

    @Override
    public String toString() {
        return "Conta{" +
                "banco=" + banco +
                ", titular='" + titular + '\'' +
                ", agencia=" + agencia +
                ", numero=" + numero +
                ", saldo=" + saldo +
                '}';
    }
}
