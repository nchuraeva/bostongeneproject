package firsttask;

import firsttask.threads.DeletingThread;
import firsttask.threads.WriterThread;

public class AppRunner {

    private DeletingThread deletingThread;
    private WriterThread writerThread;

    public AppRunner() {
        final ThreadQueue threadQueue = new ThreadQueue();
        deletingThread = new DeletingThread(threadQueue);
        writerThread = new WriterThread(threadQueue, this);
    }


    private void start() {
        deletingThread.start();
        writerThread.start();
    }

    public void stop() {
        deletingThread.interrupt();
        writerThread.interrupt();

        System.exit(0);
    }

    public static void main(String[] args) {
        new AppRunner().start();
    }


}
