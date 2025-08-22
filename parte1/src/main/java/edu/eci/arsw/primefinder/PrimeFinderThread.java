package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {
    private boolean runThread = true;
    int a, b;
    private final Object lock = new Object();
    private List<Integer> primes = new LinkedList<Integer>();

    public PrimeFinderThread(int a, int b) {
        super();
        this.a = a;
        this.b = b;
    }

    public void pauseThread() {
        runThread = false;
    }

    public void resumeThread() {
        synchronized (lock) {
            runThread = true;
            lock.notify();
        }
    }


    @Override
    public void run() {
        for (int i = a; i <= b; i++) {
            synchronized (lock) {
                while (!runThread) {
                    try {
                        System.out.println("Hilo pausado... !!!!");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }

            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }


}
