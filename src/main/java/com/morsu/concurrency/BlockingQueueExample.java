package com.morsu.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable{
    private final BlockingQueue<String> queue;
    private boolean exit;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String message = "";

            try {
                for (int i = 0; i < 100; i++) {
                    message = Integer.toString(i);
                    queue.put(message);
                    System.out.println("Produced Message: " + message);
                    Thread.sleep(10);
                }

                queue.put("exit");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("Producer exited!");
    }


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}

class Consumer implements Runnable {

    private final BlockingQueue<String> queue;
    private boolean exit;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String message;
            while(!(message = queue.take()).equals("exit")) {
                System.out.println("Message Received: " + message);
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer exited!");
    }


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
