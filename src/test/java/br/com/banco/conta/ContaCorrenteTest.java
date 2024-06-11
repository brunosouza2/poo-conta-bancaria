package br.com.banco.conta;

import br.com.banco.Banco;
import br.com.banco.conta.Exception.DepositoInvalidoException;
import br.com.banco.conta.Exception.SaldoInsuficienteException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta corrente")
class ContaCorrenteTest {

    private Banco banco;
    private ContaCorrente conta;

    @BeforeEach
    public void beforeEach() {
        banco = new Banco("Itaú");
        conta = new ContaCorrente(banco, "Carlos Luiz", new BigDecimal("100.00"));
    }

    @Nested
    @DisplayName("Deposito")
    class Deposito {

        @Test
        void deveLancarExcecaoSeDepositoZero() {
            Class<DepositoInvalidoException> excecaoEsperada = DepositoInvalidoException.class;
            BigDecimal valorDeposito = BigDecimal.ZERO;
            assertThrows(excecaoEsperada, () -> conta.depositar(valorDeposito));
        }

        @Test
        void deveLancarExcecaoSeDepositoNegativo() {
            Class<DepositoInvalidoException> excecaoEsperada = DepositoInvalidoException.class;
            BigDecimal valorDeposito = new BigDecimal("-10.00");
            assertThrows(excecaoEsperada, () -> conta.depositar(valorDeposito));
        }

        @Test
        void depositoDeveAcrescentarAoSaldo() {
            BigDecimal valorDeposito = BigDecimal.TEN;
            BigDecimal saldoEsperado = new BigDecimal("10.00");

            conta.depositar(valorDeposito);

            assertEquals(saldoEsperado, conta.getSaldo(), "O valor do saldo não foi o esperado.");
        }

    }

    @Nested
    @DisplayName("Saque")
    class Saque {

        @BeforeEach
        public void inserirSaldo() {
            conta.depositar(new BigDecimal("500"));
        }

        @Test
        void deveLancarExcecaoSeSaqueMaiorQueSaldo() {
            Class<SaldoInsuficienteException> excecaoEsperada = SaldoInsuficienteException.class;
            BigDecimal valorSaque = new BigDecimal("1000");

            conta.setLimite(BigDecimal.ZERO);

            assertThrows(excecaoEsperada, () -> conta.sacar(valorSaque), "Saldo insuficiente para realizar operação.");
        }

        @Test
        void deveEfetuarSaqueSeHouverLimite() {
            BigDecimal valorSaque = new BigDecimal("600");
            BigDecimal limiteRestante = new BigDecimal("50.00");
            BigDecimal saldoRestante = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

            conta.setLimite(new BigDecimal("150"));
            conta.sacar(valorSaque);

            assertEquals(saldoRestante, conta.getSaldo(), "Valor do saldo não esperado.");
            assertEquals(limiteRestante, conta.getLimite(), "Valor do limite não esperado.");
        }

        @Test
        void deveEfetuarSaqueSeHouverSaldo() {
            BigDecimal valorSaque = new BigDecimal("400");
            BigDecimal valorSaldoEsperado = new BigDecimal("100.00");

            conta.sacar(valorSaque);

            assertEquals(valorSaldoEsperado, conta.getSaldo(), "Valor do saldo não esperado.");
        }

    }

    @Nested
    class Saldo {

        private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

        @BeforeEach
        public void setup() {
            System.setOut(new PrintStream(outputStreamCaptor));
            conta = new ContaCorrente(banco, "Luiz Carlos", BigDecimal.ZERO);
            conta.depositar(BigDecimal.TEN);
        }

        @Test
        void deveMostrarSaldo() {
            String mensagemEsperada = "Saldo: 10,0";

            conta.mostraSaldo();

            assertEquals(mensagemEsperada, outputStreamCaptor.toString());
        }


    }



}