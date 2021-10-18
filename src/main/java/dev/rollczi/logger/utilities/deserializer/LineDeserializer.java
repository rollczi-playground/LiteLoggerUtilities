package dev.rollczi.logger.utilities.deserializer;

import dev.rollczi.logger.utilities.LogRecord;

public interface LineDeserializer {

    LogRecord deserialize(String line);

}
