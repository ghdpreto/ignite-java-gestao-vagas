package br.com.ghdpreto.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {



    // teste nao tem retorno
    @Test // informa que e um teste
    public void deve_ser_possivel_calcular_dois_numeros() {
        var resultado = calculate(2, 3);

        // espero que o resultado seja 5
        assertEquals( resultado, 5);
    }

    @Test
    public void validar_valor_incorreto() {
        var resultado = calculate(2, 3);

        // espero que o resultado nao seja 4
        assertNotEquals( resultado, 4);
    }


    public static int calculate(int num1, int num2) {
        return num1 + num2;
    }


    public static void main(String[] args) {
        var resultado = calculate(2, 3);
    
        System.out.println(resultado);
    }

}
