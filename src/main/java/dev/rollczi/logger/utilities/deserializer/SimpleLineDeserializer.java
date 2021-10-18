package dev.rollczi.logger.utilities.deserializer;

import dev.rollczi.logger.utilities.LogLevel;
import dev.rollczi.logger.utilities.LogRecord;

public class SimpleLineDeserializer implements LineDeserializer {

    public LogRecord deserialize(String line) {
        return new LogRecord(LogLevel.INFO, line);
    }

}
