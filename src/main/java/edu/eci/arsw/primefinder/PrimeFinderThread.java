package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    private int a, b;
    private List<Integer> primes;
    private Control controlMonitor;

    public PrimeFinderThread(int a, int b, Control controlMonitor) {
        this.a = a;
        this.b = b;
        this.controlMonitor = controlMonitor;
        this.primes = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            controlMonitor.checkPaused();

            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
        }
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }
}
