package arsw.threads;

public class RegistroLlegada {

	private int ultimaPosicionAlcanzada=1;

	private String ganador=null;

	public synchronized int asignarPosicion(String nombreGalgo){
		int ubicacion = ultimaPosicionAlcanzada;
		ultimaPosicionAlcanzada++;
		if (ubicacion == 1) ganador = nombreGalgo;
		return ubicacion;
	}
	
	public synchronized String getGanador() {
		return ganador;
	}

	public synchronized  int getUltimaPosicionAlcanzada() {
		return ultimaPosicionAlcanzada;
	}

}
