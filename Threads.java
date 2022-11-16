package com.monkeyhide;
import java.util.concurrent.Semaphore;

public class Threads {
    public static void main(String[] args) throws InterruptedException {
        final int iterNum = 100;
        Semaphore s = new Semaphore(0);

        Thread t1 = new Thread() {
            public void run() {
                try {
                    System.out.println("Thread one sleeps");
                    sleep(3000);
                } catch (Exception e) {};
                for (int i = 0; i < iterNum; i++) {
                    synchronized (Threads.class) {
                        System.out.println("Hello from thread 1");
                        if (s.availablePermits() == 0) {
                            s.release();
                        }
                        try {
                            Threads.class.notify();
                            if (i < iterNum - 1) {
                                Threads.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                synchronized (Threads.class) {
                    for (int i = 0; i < iterNum; i++) {
                        System.out.println("Hello from thread 2");
                        Threads.class.notify();
                        try {
                            if (i < iterNum - 1) {
                                Threads.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        t1.start();
        s.acquire();
        t2.start();
    };
}
