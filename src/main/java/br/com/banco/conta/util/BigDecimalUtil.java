package br.com.banco.conta.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    private static final int CASAS_DECIMAIS = 2;
    private static final RoundingMode ARREDONDAMENTO = RoundingMode.HALF_UP;

    public static BigDecimal padronizarCasasDecimais(BigDecimal valor) {
        return valor.setScale(CASAS_DECIMAIS, ARREDONDAMENTO);
    }
}
