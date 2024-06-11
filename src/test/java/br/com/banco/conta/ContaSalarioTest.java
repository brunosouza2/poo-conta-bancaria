package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.LimiteDeSaqueAtingidoException;
import br.com.banco.conta.Exception.SaldoInsuficienteException;
import br.com.banco.conta.Exception.SaqueInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ContaSalarioTest {

    private Banco banco;
    private ContaSalario conta;

    @BeforeEach
    public void beforeEach() {
        banco = new Banco("Santander");
        conta = new ContaSalario(banco, "Bruno");
    }

    @Nested
    class Saque {

        @Test
        void saqueDentroDoLimite() {
            BigDecimal valorSaque = new BigDecimal("100");
            BigDecimal saldoEsperado = new BigDecimal("50.00");
            conta.depositar(new BigDecimal("150"));

            conta.sacar(valorSaque);

            assertEquals(saldoEsperado, conta.getSaldo());
        }

        @Test
        void lancaExcecaoSeLimiteDeSaqueAtingido() {
            BigDecimal valorPrimeiroSaque = new BigDecimal("100");
            BigDecimal valorSegundoSaque = new BigDecimal("20");
            Class<LimiteDeSaqueAtingidoException> excecaoEsperada = LimiteDeSaqueAtingidoException.class;
            conta.depositar(new BigDecimal("150"));

            // Primeiro Saque
            conta.sacar(valorPrimeiroSaque);

            // Segundo Saque com exceção
            assertThrows(excecaoEsperada, () -> conta.sacar(valorSegundoSaque));
        }

        @Test
        void resetaSaquesNoNovoMes() {
            BigDecimal primeiroSaque = new BigDecimal("100");
            Month mesSeguinte = LocalDate.now().getMonth().plus(1);
            Integer contagemSaqueAtual = 0;
            Integer contagemSaqueEsperada = 0;

            conta.depositar(new BigDecimal("200"));
            // Efetuando primeiro saque
            conta.sacar(primeiroSaque);
            contagemSaqueAtual  = conta.getContagemSaquesMap().getOrDefault(conta.getMes(), 0);

            // Efetuando troca de mês
            conta.setMes(mesSeguinte);
            contagemSaqueAtual  = conta.getContagemSaquesMap().getOrDefault(conta.getMes(), 0);

            assertEquals(contagemSaqueEsperada, contagemSaqueAtual);
        }

        @Test
        void lancaExcecaoSeSaqueMaiorQueSaldo() {
            BigDecimal valorSaque = new BigDecimal("100");
            Class<SaldoInsuficienteException> excecaoEsperada = SaldoInsuficienteException.class;

            conta.depositar(new BigDecimal("50"));

            assertThrows(excecaoEsperada, () -> conta.sacar(valorSaque));
        }

        @Test
        void lancaExcecaoSeSaqueIgualAZero() {
            BigDecimal valorSaque = new BigDecimal("0");
            Class<SaqueInvalidoException> excecaoEsperada = SaqueInvalidoException.class;

            conta.depositar(new BigDecimal("100"));

            assertThrows(excecaoEsperada, () -> conta.sacar(valorSaque));
        }

    }

}