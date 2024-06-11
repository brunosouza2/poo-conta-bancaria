package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.DepositoInvalidoException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta poupança")
class ContaPoupancaTest {

    private Banco banco;
    private ContaPoupanca conta;

    @BeforeEach
    public void beforeEach() {
        banco = new Banco("Santander");
        conta = new ContaPoupanca(banco, "Bruno");
    }

    @Nested
    class Deposito {

        @Test
        void deveLancarExcecaoSeNegativo() {
            Class<DepositoInvalidoException> excecaoEsperada = DepositoInvalidoException.class;

            assertThrows(excecaoEsperada, () -> conta.depositar(new BigDecimal("-10.0")));
        }

        @Test
        void deveLancarExcecaoSeZero() {
            Class<DepositoInvalidoException> excecaoEsperada = DepositoInvalidoException.class;

            assertThrows(excecaoEsperada, () -> conta.depositar(new BigDecimal("0.0")));
        }

        @Test
        void deveAtualizarOSaldoAoDepositar() {
            BigDecimal deposito = new BigDecimal("100.00");
            /*
                O saldo esperado é maior pois esta modalidade de conta
                aplica uma taxa de juros de 0.1%

             */
            BigDecimal saldoEsperado = new BigDecimal("110.00");

            conta.depositar(deposito);

            assertEquals(saldoEsperado, conta.getSaldo(), "O saldo da conta não foi atualizado corretamente.");
        }
    }

    @Nested
    class Juros {
        @Test
        void deveAplicarTaxaJurosAoSaldo() {
            var banco = new Banco("Santander");
            var conta = new ContaPoupanca(banco, "Pedro Henrique");
            var saldoEsperado = new BigDecimal("550.00");

            conta.depositar(new BigDecimal("500"));

            assertEquals(saldoEsperado, conta.getSaldo(), "O valor do saldo não foi o esperado.");
        }
    }




}