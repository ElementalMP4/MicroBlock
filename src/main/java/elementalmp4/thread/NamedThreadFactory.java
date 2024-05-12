package elementalmp4.thread;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private long idx;
    private final String name;

    public NamedThreadFactory(String name) {
        this.name = name;
        this.idx = 0;
    }

    private String getNameFormatted() {
        idx++;
        return "%s-%d".formatted(name, idx);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(getNameFormatted());
        return t;
    }
}