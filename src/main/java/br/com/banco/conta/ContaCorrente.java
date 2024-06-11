package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.LimiteEspecialInvalidoException;
import br.com.banco.conta.Exception.SaldoInsuficienteException;

import java.math.BigDecimal;

import static br.com.banco.conta.util.BigDecimalUtil.*;

public class ContaCorrente extends Conta {

    private BigDecimal limite = BigDecimal.ZERO;

    public ContaCorrente(Banco banco, String titular, BigDecimal limite) {
        super(banco, titular);
        setLimite(limite);
    }

    public void setLimite(BigDecimal limite) {
        if (limite.compareTo(BigDecimal.ZERO) < 0) throw new LimiteEspecialInvalidoException("O limite não pode ser negativo.");
        this.limite = padronizarCasasDecimais(limite);
    }

    public BigDecimal getLimite() {
        return limite;
    }

    @Override
    public void sacar(BigDecimal valor) {
        BigDecimal saldoTotalDisponivel = getSaldo().add(getLimite());
        if (valor.compareTo(saldoTotalDisponivel) > 0) throw new SaldoInsuficienteException("Saldo insuficiente para realizar operação.");
        if (valor.compareTo(getSaldo()) > 0 && valor.compareTo(saldoTotalDisponivel) <= 0) {
           BigDecimal valorExcedente = valor.subtract(getSaldo());
           BigDecimal valorSaldoConta = getSaldo().subtract(valor.subtract(valorExcedente));
           setSaldo(valorSaldoConta);
           this.limite = padronizarCasasDecimais(this.limite.subtract(valorExcedente));
           return;
        }
        super.sacar(valor);
    }

    @Override
    public void mostraSaldo() {
        super.mostraSaldo();
    }

    @Override
    public String toString() {
        return "Conta{" +
                "banco=" + getBanco().getNome() +
                ", titular='" + getTitular() + '\'' +
                ", agencia=" + getAgencia() +
                ", numero=" + getNumero() +
                ", saldo=" + getSaldo() +
                ", limite=" + getLimite() +
                '}';
    }
}
