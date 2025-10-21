package arep.edu.co.part;

import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/proxy")
public class ProxyService {

    private static final String AGENTE = "Mozilla/5.0";
    private int contador = 0;
    private String[] servidores = {
        "http://54.166.212.135:8080",
        "http://34.234.92.170:8081",
        "http://52.23.158.164:8082"
    };

    @PostMapping("/factors")
    public String proxyFactores(@RequestParam int value) {
        try {
            String servidor = obtenerServidor();
            String direccion = servidor + "/factors?value=" + value;
            return enviarPeticion(direccion);
        } catch (IOException e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping("/primes")
    public String proxyPrimos(@RequestParam int value) {
        try {
            String servidor = obtenerServidor();
            String direccion = servidor + "/primes?value=" + value;
            return enviarPeticion(direccion);
        } catch (IOException e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private String obtenerServidor() {
        String servidor = servidores[contador];
        contador = (contador + 1) % servidores.length;
        return servidor;
    }

    private String enviarPeticion(String direccion) throws IOException {
        URL url = new URL(direccion);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setRequestProperty("User-Agent", AGENTE);
        int codigo = conexion.getResponseCode();
        if (codigo == HttpURLConnection.HTTP_OK) {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = entrada.readLine()) != null) respuesta.append(linea);
            entrada.close();
            return respuesta.toString();
        } else {
            return "{\"error\":\"" + codigo + "\"}";
        }
    }
}
