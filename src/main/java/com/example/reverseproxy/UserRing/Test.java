package com.example.reverseproxy.UserRing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        SubmissionQueue<String> SQ = new SubmissionQueue<>();
        CompleteQueue<String> CQ = new CompleteQueue<>();

        // InheritableThreadLocal<Integer> integer = new InheritableThreadLocal<>();
        // integer.set(0);

        ExecutorService providerThreads = Executors.newFixedThreadPool(10);
        providerThreads.execute(() -> {
            while (true) {
                String s = String.valueOf(System.currentTimeMillis());
                SQ.enqueue(s);
                System.out.println("provider provides " +s + " for SQ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!CQ.isEmpty()) {
                    String dequeue = CQ.dequeue();
                    System.out.println("provider get " + dequeue + " from CQ");
                }
            }
        });

        ExecutorService consumerThreads = Executors.newFixedThreadPool(10);
        consumerThreads.execute(() -> {
            while (true) {
                if (!SQ.isEmpty()) {
                    String dequeue = SQ.dequeue();
                    System.out.println("consumer fetch " + dequeue + " from SQ");
                    String process;
                    try {
                        process = process(dequeue);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("processing done, fill to CQ");
                    CQ.enqueue(process);
                }
            }
        });

    }
    static String process(String s) throws InterruptedException {
        Thread.sleep(1000);
        return "PROCESSED-" + s;
    }
}
