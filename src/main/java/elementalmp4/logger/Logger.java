package elementalmp4.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String RED = "\033[1;31m";
    private static final String GREEN = "\033[1;32m";
    private static final String YELLOW = "\033[1;33m";
    private static final String BLUE = "\033[1;34m";
    private static final String RESET = "\033[1;0m";
    private static final String GREY = "\033[1;90m";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void info(String format, Object... args) {
        log(LogLevel.INFO, format.formatted(args));
    }

    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public static void warn(String format, Object... args) {
        log(LogLevel.WARN, format.formatted(args));
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void error(String format, Object... args) {
        log(LogLevel.ERROR, format.formatted(args));
    }

    public static void success(String message) {
        log(LogLevel.SUCCESS, message);
    }

    public static void success(String format, Object... args) {
        log(LogLevel.SUCCESS, format.formatted(args));
    }

    private static void log(LogLevel level, String message) {
        String dateTimeFormat = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String thread = Thread.currentThread().getName();
        System.out.println("%s[%s]%s [%s] [%-10s] : %s".formatted(GREY, dateTimeFormat, RESET, level.getLevelString(), thread, message));
    }

    private enum LogLevel {
        INFO(YELLOW + "INFO" + RESET),
        WARN(BLUE + "WARN" + RESET),
        ERROR(RED + "FAIL" + RESET),
        SUCCESS(GREEN + " OK " + RESET);

        private final String levelString;

        LogLevel(String levelString) {
            this.levelString = levelString;
        }

        public String getLevelString() {
            return this.levelString;
        }
    }

}
