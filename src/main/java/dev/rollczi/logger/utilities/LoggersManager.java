package dev.rollczi.logger.utilities;

import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggersManager {

    private final Map<String, Logger> loggers = new ConcurrentHashMap<>();

    public Logger newLogger(String name) {
        if (loggers.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        Logger logger = new Logger(name);
        loggers.put(name, logger);
        return logger;
    }

    public Option<Logger> getLogger(String name) {
        return Option.of(this.loggers.get(name));
    }

    public Collection<Logger> getLoggers() {
        return Collections.unmodifiableCollection(loggers.values());
    }

}
