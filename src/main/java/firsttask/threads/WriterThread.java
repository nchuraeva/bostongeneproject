package firsttask.threads;

import firsttask.AppRunner;
import firsttask.ConvertStringToNumber;
import firsttask.ThreadQueue;

import java.util.Scanner;

public class WriterThread extends Thread {

    private Scanner scanner;

    private AppRunner appRunner;

    private ThreadQueue threadQueue;
    public WriterThread(ThreadQueue threadQueue, AppRunner runner) {
        scanner = new Scanner(System.in);
        this.threadQueue = threadQueue;
        this.appRunner = runner;

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is started!");
        String str;
        while ( scanner.nextLine() != null && !interrupted()) {

            str = scanner.nextLine();

            if("STOP".equals(str)) {
                appRunner.stop();
            }
            try {
                int number = new ConvertStringToNumber().convertToNumber(str);
                threadQueue.push(number);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
