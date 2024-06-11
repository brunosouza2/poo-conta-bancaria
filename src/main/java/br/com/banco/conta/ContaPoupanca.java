package br.com.banco.conta;

import br.com.banco.Banco;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

    private final BigDecimal TAXA_JUROS = new BigDecimal("0.1");

    public ContaPoupanca(Banco banco, String titular) {
        super(banco, titular);
    }

    @Override
    public void depositar(BigDecimal valor) {
        super.depositar(valor);
        aplicarJuros();
    }

    private void aplicarJuros() {
        BigDecimal saldoAtualizadoComTaxa = getSaldo().add(getSaldo().multiply(TAXA_JUROS));
        setSaldo(saldoAtualizadoComTaxa);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "banco=" + getBanco().getNome() +
                ", titular='" + getTitular() + '\'' +
                ", agencia=" + getAgencia() +
                ", numero=" + getNumero() +
                ", saldo=" + getSaldo() +
                ", taxa juros=" + this.TAXA_JUROS +
                '}';
    }
}
