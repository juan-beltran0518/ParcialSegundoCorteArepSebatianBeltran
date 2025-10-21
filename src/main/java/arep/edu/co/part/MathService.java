package arep.edu.co.part;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MathService {

    @GetMapping(value = "/factors", produces = "application/json")
    public String factores(@RequestParam("value") int valor) {
        if (valor <= 0) return "{\"error\":\"valor debe ser positivo\"}";
        List<Integer> listaFactores = new ArrayList<>();
        listaFactores.add(1);
        for (int i = 2; i <= valor / 2; i++) {
            if (valor % i == 0) listaFactores.add(i);
        }
        if (valor != 1) listaFactores.add(valor);
        String salida = listaAtexto(listaFactores);
        return "{\"operation\":\"factors\",\"input\":" + valor + ",\"output\":\"" + salida + "\"}";
    }

    @GetMapping(value = "/primes", produces = "application/json")
    public String primos(@RequestParam("value") int valor) {
        if (valor <= 0) return "{\"error\":\"valor debe ser positivo\"}";
        List<Integer> listaPrimos = new ArrayList<>();
        if (valor >= 1) listaPrimos.add(1);
        for (int i = 2; i <= valor; i++) {
            if (esPrimo(i)) listaPrimos.add(i);
        }
        String salida = listaAtexto(listaPrimos);
        return "{\"operation\":\"primes\",\"input\":" + valor + ",\"output\":\"" + salida + "\"}";
    }

    private boolean esPrimo(int numero) {
        int divisores = 0;
        for (int i = 1; i <= numero; i++) {
            if (numero % i == 0) divisores++;
            if (divisores > 2) return false;
        }
        return divisores == 2;
    }

    private String listaAtexto(List<Integer> lista) {
        return lista.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}
