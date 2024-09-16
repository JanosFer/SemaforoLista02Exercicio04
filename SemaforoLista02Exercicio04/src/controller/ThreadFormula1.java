package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ThreadFormula1 extends Thread{
	private final int TID = (int) getId();
	private Semaphore semaforo;
	private int escuderia;
	private static int concluiram;
	private static boolean[] pista = {false, false, false, false, false, false, false}; 
	private String[] equipe = {"Mercedes", "Ferrari", "RB", "McLaren", "Aston Martin", "Williams", "Alpine"}; 
	private static List<String> gridLargada = new ArrayList<String>();
	
	public ThreadFormula1(Semaphore semaforo, int escuderia) {
		this.semaforo = semaforo;
		if(escuderia <= 7) {
			this.escuderia = escuderia;
		}else {
			this.escuderia = escuderia - 7;
		}
	}
	
	public void run(){
		if(!pista[escuderia-1]) {
			try {
				semaforo.acquire();
				pista[escuderia-1] = true;
				treino();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforo.release();
				pista[escuderia-1] = false;
			}
		}
		
		if(concluiram == 14) {
			organizaGridLargada();
			concluiram++;
		}
	}
	
	private void treino() {
		int voltas = 0, melhorTempo = 0;
		while(voltas < 3){
			try {
				int tempo = (int)(Math.random() * 900) + 100;
				System.out.println(TID + "# Carro da escuderia " + equipe[escuderia-1] + " completou a " + (voltas + 1) + "a volta em " + tempo + "ms");
				if(voltas == 0) {
					melhorTempo = tempo;
				}else if(voltas >= 0 && tempo < melhorTempo) {
					melhorTempo = tempo;
				}
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			voltas++;
		}
		concluiram++;
		gridLargada.add(TID + "# Carro da escuderia " + equipe[escuderia-1] + " com o tempo de " + melhorTempo);
	}
	
	private void organizaGridLargada() {
		int n = gridLargada.size();
		
		for(int i = 0; i < n - 1; i++) {
			for(int j = i + 1; j < n; j++) {
				int tamanhoi = gridLargada.get(i).length();
				int tamanhoj = gridLargada.get(j).length();
				int tempoi = Integer.parseInt(gridLargada.get(i).substring(tamanhoi - 3));
				int tempoj = Integer.parseInt(gridLargada.get(j).substring(tamanhoj - 3));
				
				if(tempoj < tempoi) {
					String aux = gridLargada.get(i);
					gridLargada.set(i, gridLargada.get(j));
					gridLargada.set(j, aux);
				}
			}
		}
		
		int i = 1;
		for(String carro : gridLargada) {
			System.out.println(i+ "° Lugar " + carro + "ms");
			i++;
		}
	}
}
