package view;

import java.util.concurrent.Semaphore;

import controller.ThreadFormula1;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(5);
		for(int i = 0; i < 14; i++) {
			ThreadFormula1 t = new ThreadFormula1(semaforo, (i+1));
			t.start();
		}
	}

}
