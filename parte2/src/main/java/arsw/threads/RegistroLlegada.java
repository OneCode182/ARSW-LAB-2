package arsw.threads;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegistroLlegada {

    private int ultimaPosicionAlcanzada = 1;
    private String ganador = null;

    // Guardar resultados por orden de llegada
    private final Map<Integer, String> resultados = new LinkedHashMap<>();

    public int asignarPosicion(String nombreGalgo) {
        int ubicacion = ultimaPosicionAlcanzada;
        ultimaPosicionAlcanzada++;
        if (ubicacion == 1) {
            ganador = nombreGalgo;
        }
        resultados.put(ubicacion, nombreGalgo);
        return ubicacion;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public int getUltimaPosicionAlcanzada() {
        return ultimaPosicionAlcanzada;
    }

    // Método para imprimir resultados en orden
    public synchronized void imprimirResultados() {
        System.out.println("\n=== RESULTADOS FINALES ===");
        resultados.forEach((pos, galgo) -> {
            System.out.println("Posición " + pos + ": " + galgo);
        });
    }
}
