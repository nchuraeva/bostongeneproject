package firsttask.threads;

import firsttask.ThreadQueue;


public class DeletingThread extends Thread {

    private ThreadQueue threadQueue;

    public  DeletingThread (ThreadQueue threadQueue) {
        this.threadQueue = threadQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is started!");
        while (!interrupted()) {
            try {
                Integer number = threadQueue.poll();
                if (number != null) {
                    System.out.println(number + " is deleted");
                }
                System.out.println(Thread.currentThread().getName() + " is sleeping!");
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupting!");
                return;
            }
        }

    }

}
