package test.com.hospital;

import com.hospital.model.*;
import com.hospital.service.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class GerenciadorDePagamentosTest {

    @Test
    public void testCalcularValorFinalPixComDesconto() {
        GerenciadorDePagamentos ger = new GerenciadorDePagamentos();
        double valorBase = 1000.0;
        double resultado = ger.calcularValorFinal(valorBase, 1);
        assertEquals("O desconto do PIX de 10% falhou", 900.0, resultado, 0.001);
    }

    @Test
    public void testCalcularValorFinalParceladoComJuros() {
        GerenciadorDePagamentos ger = new GerenciadorDePagamentos();
        double valorBase = 1000.0;
        double resultado = ger.calcularValorFinal(valorBase, 2);

        assertEquals("O calculo de 8% de juros falhou", 1080.0, resultado, 0.001);
    }

    @Test
    public void testCalcularValorFinalCartaoAVista() {
        GerenciadorDePagamentos ger = new GerenciadorDePagamentos();
        double valorBase = 1000.0;
        double resultado = ger.calcularValorFinal(valorBase, 3);

        assertEquals("O calculo do cartao a vista deveria manter o valor base", 1000.0, resultado, 0.001);
    }
}