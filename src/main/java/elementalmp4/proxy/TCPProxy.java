package elementalmp4.proxy;

import elementalmp4.Config;
import elementalmp4.logger.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPProxy extends Proxy {

    public TCPProxy() {
        super("TCP");
    }

    @Override
    public void startProxy() {
        Logger.info("Starting TCP firewall...");
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(Config.getListenPort()))) {
            Logger.success("TCP firewall listening on port %s, forwarding to %s", Config.getListenPort(), Config.getForwardAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleTCPConnection(clientSocket, Config.getForwardAddress()));
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleTCPConnection(Socket clientSocket, String forwardAddress) {
        try {
            InetAddress clientAddress = clientSocket.getInetAddress();
            String srcIP = clientAddress.getHostAddress();

            if (isBlacklisted(srcIP)) {
                Logger.info("Dropped TCP connection from blacklisted IP: " + srcIP);
                clientSocket.close();
                return;
            }

            Logger.info("Forwarding TCP connection from IP: " + srcIP);
            Socket forwardSocket = new Socket(forwardAddress.split(":")[0], Integer.parseInt(forwardAddress.split(":")[1]));
            threadFactory.newThread(() -> {
                try {
                    forwardData(clientSocket.getInputStream(), forwardSocket.getOutputStream());
                } catch (IOException e) {
                    Logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }).start();
            forwardData(forwardSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
