package firsttask;

import java.util.Collections;
import java.util.LinkedList;

public class ThreadQueue {

    private final LinkedList<Integer> queue;

    public ThreadQueue() {
        this.queue = new LinkedList<Integer>();
    }

    public void push(final Integer t) {
        synchronized (this.queue) {
            this.queue.add(t);
            Collections.sort(this.queue);
        }
    }

    public Integer poll() {
        synchronized (this.queue) {
            if (this.queue.isEmpty()) {
                return null;
            }
            return this.queue.removeFirst();
        }
    }
}
