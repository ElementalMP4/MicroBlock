package elementalmp4;

import elementalmp4.proxy.TCPProxy;
import elementalmp4.proxy.UDPProxy;

public class Main {

    public static void main(String[] args) {
        if (Config.isTcpEnabled()) {
            TCPProxy tcpProxy = new TCPProxy();
            tcpProxy.start();
        }
        if (Config.isUdpEnabled()) {
            UDPProxy udpProxy = new UDPProxy();
            udpProxy.start();
        }
    }

}
