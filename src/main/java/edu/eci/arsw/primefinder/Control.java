package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private boolean paused = false;
    private PrimeFinderThread[] pft;

    private Control() {
        pft = new PrimeFinderThread[NTHREADS];

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            pft[i] = new PrimeFinderThread(
                    i * NDATA,
                    (i + 1) * NDATA,
                    this
            );
        }
        pft[i] = new PrimeFinderThread(
                i * NDATA,
                MAXVALUE + 1,
                this
        );
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (PrimeFinderThread t : pft) {
            t.start();
        }

        Scanner sc = new Scanner(System.in);

        while (areThreadsAlive()) {
            try {
                Thread.sleep(TMILISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pauseSystem();

            System.out.println("\nPrograma pausado");
            System.out.println("Primos encontrados: " + getTotalPrimes());
            System.out.println("Presione ENTER para continuar");
            sc.nextLine();

            resumeSystem();
        }
    }

    private boolean areThreadsAlive() {
        for (PrimeFinderThread t : pft) {
            if (t.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int getTotalPrimes() {
        int total = 0;
        for (PrimeFinderThread t : pft) {
            total += t.getPrimes().size();
        }
        return total;
    }

    public synchronized void checkPaused() {
        while (paused) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void pauseSystem() {
        paused = true;
    }

    private synchronized void resumeSystem() {
        paused = false;
        notifyAll();
    }
}
