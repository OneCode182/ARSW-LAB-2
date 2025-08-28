package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
    private boolean isStop;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
        this.isStop = true;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
            synchronized (regl) {
                while (!isStop) {
                    regl.wait();
                }
            }

			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {
                synchronized (regl) {
                    carril.finish();
                    int ubicacion = regl.asignarPosicion(this.getName());
                    System.out.println("Galgo #" + this.getName() + " | POS: " + ubicacion);
                }

				//System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
			}
		}
	}



    public void modExec(boolean execute) {
        this.isStop = execute;
    }

	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
