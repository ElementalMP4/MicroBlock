package elementalmp4.proxy;

import elementalmp4.Config;
import elementalmp4.logger.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPProxy extends Proxy {

    public UDPProxy() {
        super("UDP");
    }

    @Override
    public void startProxy() {
        Logger.info("Starting UDP firewall...");
        try {
            DatagramSocket socket = new DatagramSocket(Integer.parseInt(Config.getListenPort()));
            byte[] buffer = new byte[1500];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            Logger.success("UDP firewall listening on port %s, forwarding to %s", Config.getListenPort(), Config.getListenPort());
            while (true) {
                socket.receive(packet);
                String srcIP = packet.getAddress().getHostAddress();
                if (isBlacklisted(srcIP)) {
                    Logger.info("Dropped UDP packet from blacklisted IP: %s", srcIP);
                    continue;
                }

                InetAddress forwardAddressInet = InetAddress.getByName(Config.getForwardAddress().split(":")[0]);
                int forwardPort = Integer.parseInt(Config.getForwardAddress().split(":")[1]);
                DatagramPacket forwardPacket = new DatagramPacket(packet.getData(), packet.getLength(), forwardAddressInet, forwardPort);
                socket.send(forwardPacket);
            }

        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
