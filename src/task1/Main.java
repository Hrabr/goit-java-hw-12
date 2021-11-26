package task1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

 class Water {
    String input;

    public String getInput() {
        return input;
    }


    Water(String input) {
        this.input = input;
    }

    Semaphore h = new Semaphore(2);
    Semaphore o = new Semaphore(1);
    CyclicBarrier h2o = new CyclicBarrier(3, new BarAction());

    public void releaseOxygen() throws InterruptedException, BrokenBarrierException {
        o.acquire();
        System.out.print("O");
        h2o.await();
        o.release();
    }

    public void releaseHydrogen() throws InterruptedException, BrokenBarrierException {
        h.acquire();
        System.out.print("H");
        h2o.await();
        h.release();
    }

    public static void main(String[] args) {
        Water water = new Water("OOOOHHHHOOHHHHHHHH");
        for (int i = 0; i < water.getInput().length(); i++) {
            if (water.getInput().charAt(i) == 'H') {
                new Thread(() -> {
                    try {
                        water.releaseHydrogen();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();

            } else {
                new Thread(() -> {
                    try {
                        water.releaseOxygen();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}

class BarAction implements Runnable {
    public void run() {
        System.out.print(" ");
    }
}
