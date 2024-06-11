package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.LimiteDeSaqueAtingidoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.TreeMap;

public class ContaSalario extends Conta {

    private final Map<Month, Integer> contagemSaquesMap;
    private Month mes;

    public ContaSalario(Banco banco, String titular) {
        super(banco, titular);
        this.contagemSaquesMap = new TreeMap<>();
        this.mes = LocalDate.now().getMonth();
        insereMesAtual(this.mes, 0);
    }

    public void setMes(Month mes) {
        this.mes = mes;
    }

    public Month getMes() {
        return mes;
    }

    public Map<Month, Integer> getContagemSaquesMap() {
        return contagemSaquesMap;
    }

    private void insereMesAtual(Month mes, Integer valor) {
        contagemSaquesMap.put(mes, valor);
    }

    private boolean isMesAtual(Month mes) {
        Month mesAgora = LocalDate.now().getMonth();
        return mes.equals(mesAgora);
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (!isMesAtual(this.mes)) {
            this.mes = LocalDate.now().getMonth();
            insereMesAtual(this.mes, 0);
        }

        Integer contagemSaquesMesAtual = contagemSaquesMap.getOrDefault(this.mes, 0);
        if (contagemSaquesMesAtual > 0) throw new LimiteDeSaqueAtingidoException("Limite de saque atingido.");
        super.sacar(valor);
        contagemSaquesMap.replace(this.mes, contagemSaquesMesAtual + 1);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
