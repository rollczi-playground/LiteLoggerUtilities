package dev.rollczi.logger.utilities.deserializer;

import dev.rollczi.logger.utilities.LogRecord;

public class ProcessDeserializer {

    private final LineDeserializer lineDeserializer;
    private final LineDeserializer errorDeserializer;

    public ProcessDeserializer(LineDeserializer lineDeserializer, LineDeserializer errorDeserializer) {
        this.lineDeserializer = lineDeserializer;
        this.errorDeserializer = errorDeserializer;
    }

    public LogRecord getLineDeserialize(String line) {
        return lineDeserializer.deserialize(line);
    }

    public LogRecord getErrorDeserialize(String error) {
        return errorDeserializer.deserialize(error);
    }

}
