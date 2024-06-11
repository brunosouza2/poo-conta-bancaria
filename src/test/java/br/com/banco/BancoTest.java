package br.com.banco;

import br.com.banco.conta.TransacoesContaBancaria;
import br.com.banco.conta.ContaCorrente;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BancoTest {

    @Test
    void deveInserirNovaConta() {
        Banco banco = new Banco("Itaú");
        TransacoesContaBancaria conta =
                new ContaCorrente(banco, "Lucas Murilo", BigDecimal.ZERO);
        List<TransacoesContaBancaria> contas = new ArrayList<>(){
            {
                add(conta);
            }
        };

        assertEquals(contas, banco.getContas());
    }

}