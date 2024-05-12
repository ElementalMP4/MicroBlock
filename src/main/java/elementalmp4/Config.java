package elementalmp4;

import elementalmp4.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Config {

    private static final List<String> blacklist;
    private static final String listenPort;
    private static final String forwardAddress;
    private static final boolean tcpEnabled;
    private static final boolean udpEnabled;

    static {
        Properties config = loadConfig();
        blacklist = List.of(config.getProperty("blacklist", "").split(","));
        listenPort = config.getProperty("listenPort");
        forwardAddress = config.getProperty("forwardAddress");
        tcpEnabled = Boolean.parseBoolean(config.getProperty("tcpEnabled"));
        udpEnabled = Boolean.parseBoolean(config.getProperty("udpEnabled"));
    }

    private static Properties loadConfig() {
        Properties config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("microblock.properties");
            config.load(fis);
            fis.close();
            return config;
        } catch (IOException e) {
            Logger.error(e.getMessage());
            System.exit(1);
        }
        return config;
    }

    public static String getListenPort() {
        return listenPort;
    }

    public static String getForwardAddress() {
        return forwardAddress;
    }

    public static List<String> getBlacklist() {
        return blacklist;
    }

    public static boolean isUdpEnabled() {
        return udpEnabled;
    }

    public static boolean isTcpEnabled() {
        return tcpEnabled;
    }
}
