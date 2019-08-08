package com.morsu.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        new Thread(new Worker(latch)).start();
        new Thread(new CountDown(latch)).start();
        Thread.sleep(4000);
    }
}

class Worker implements Runnable {

    private final CountDownLatch latch;

    Worker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Latch Released");
    }
}

class CountDown implements Runnable {
    private final CountDownLatch latch;

    CountDown(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Counting down latch");
            latch.countDown();
            Thread.sleep(1000);
            System.out.println("Counting down latch");
            latch.countDown();
            Thread.sleep(1000);
            System.out.println("Counting down latch");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}