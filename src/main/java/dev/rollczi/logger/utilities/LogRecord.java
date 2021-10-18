package dev.rollczi.logger.utilities;

public class LogRecord {

    private final LogLevel level;
    private final String message;

    public LogRecord(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

}
