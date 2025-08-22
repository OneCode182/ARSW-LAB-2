package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int time = 5000;
		List<PrimeFinderThread> primeFinderThreadList = new ArrayList<>();
		int numberThreads = 3;
		int intervalA = 0;
		int interval = 30000000 / numberThreads;
		int intervalB;
		boolean threadsExecution = true;

		for (int i = 0; i < numberThreads; i++){
			intervalB = (i == numberThreads - 1) ? 30000000 : intervalA + interval;
			primeFinderThreadList.add(new PrimeFinderThread(intervalA, intervalB));
			intervalA = intervalB;
		}

		for (PrimeFinderThread primeFinderThread : primeFinderThreadList){
			primeFinderThread.start();
		}

		while (threadsExecution) {
			threadsExecution = false;

			for (PrimeFinderThread primeFinderThread : primeFinderThreadList) {
				if (primeFinderThread.isAlive()) {
					threadsExecution = true;
					break;
				}
			}

			if (!threadsExecution) break;

			try {
				Thread.sleep(time);

				for (PrimeFinderThread primeFinderThread : primeFinderThreadList) {
					primeFinderThread.pauseThread();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			getPrintTotalPrimes(primeFinderThreadList);

			System.out.println("====== Presiona ENTER para continuar... ======");
			new Scanner(System.in).nextLine();

			for (PrimeFinderThread primeFinderThread : primeFinderThreadList) {
				primeFinderThread.resumeThread();
			}
		}


		for (PrimeFinderThread primeFinderThread : primeFinderThreadList) {
			try {
				primeFinderThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getPrintTotalPrimes(List<PrimeFinderThread> primeFinderThreadList){
		int totalPrimes = 0;
		int numberThread = 1;
		for (PrimeFinderThread primeFinderThread : primeFinderThreadList) {
			int totalPrimesForThread = primeFinderThread.getPrimes().size();
			System.out.println("Total de primos encontrados por el hilo numero " + numberThread + ": " + totalPrimesForThread);
			totalPrimes += totalPrimesForThread;
			numberThread++;
		}
		System.out.println("Total de primos encontrados por los " + primeFinderThreadList.size() + " hilos: " + totalPrimes);
	}
	
}
