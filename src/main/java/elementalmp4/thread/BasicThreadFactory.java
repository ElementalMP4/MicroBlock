package elementalmp4.thread;

import java.util.concurrent.ThreadFactory;

public class BasicThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
    
}
