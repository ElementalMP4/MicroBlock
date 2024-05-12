package elementalmp4.proxy;

import elementalmp4.Config;
import elementalmp4.thread.BasicThreadFactory;
import elementalmp4.thread.NamedThreadFactory;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Proxy {

    protected final ExecutorService executor;
    protected final BasicThreadFactory threadFactory;

    protected boolean isBlacklisted(String srcIP) {
        return Config.getBlacklist().contains(srcIP);
    }

    protected void forwardData(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer,0, bytesRead);
            out.flush();
        }
    }

    public Proxy(String proxyName) {
        executor = Executors.newFixedThreadPool(100, new NamedThreadFactory(proxyName));
        threadFactory = new BasicThreadFactory();
    }

    public void start() {
        executor.submit(this::startProxy);
    }

    protected abstract void startProxy();


}
