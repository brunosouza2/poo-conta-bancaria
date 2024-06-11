package br.com.banco;

import br.com.banco.conta.Conta;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Banco {

    String nome;
    private final List<Conta> contas;

    public Banco(String nome) {
        setNome(nome);
        this.contas = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setNome(String nome) {
        Objects.requireNonNull(nome);
        this.nome = nome;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    @Override
    public String toString() {
        return "Banco{" +
                "nome='" + nome + '\'' +
                ", contas=" + contas +
                '}';
    }
}
