package workerThread;

import java.util.ArrayList;

public class WorkerThread {

    public static void main(String[] args) {

        BlockingQueue blockingQueue = new BlockingQueue();

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Runnable task = blockingQueue.get();
                    task.run();
                }
            }
        });

        worker.start();

        for (int i = 0; i < 5; i++) {
            blockingQueue.put(getTask());
        }
    }


    public static Runnable getTask() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("The task is started");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("The task is finished");
            }
        };
    }




    static class BlockingQueue {

        ArrayList<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Runnable task = tasks.getFirst();
            tasks.remove(task);

            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }
}