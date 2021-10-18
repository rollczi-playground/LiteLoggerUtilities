package dev.rollczi.logger.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {

    private final String name;
    private final List<LogRecord> records = new ArrayList<>();
    private final Set<LoggerListener> listeners = ConcurrentHashMap.newKeySet();

    Logger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void registerListener(LoggerListener loggerListener) {
        listeners.add(loggerListener);
    }

    public void unregisterListener(LoggerListener loggerListener) {
        listeners.remove(loggerListener);
    }

    public void publishLog(LogLevel level, String message) {
        LogRecord record = new LogRecord(level, message);

        publishLog(record);
    }

    public List<LogRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public void publishLog(LogRecord record) {
        records.add(record);

        for (LoggerListener listener : listeners) {
            listener.log(record);
        }
    }
}
