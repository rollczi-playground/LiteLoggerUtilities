package dev.rollczi.logger.utilities;

public enum LogLevel {

    INFO("\u001B[37m"),
    WARNING("\u001B[33m"),
    SEVERE("\u001B[31m"),
    DEBUG("\u001B[35m");

    private final String codeColor;

    LogLevel(String codeColor) {
        this.codeColor = codeColor;
    }

    public String getCodeColor() {
        return codeColor;
    }

}
