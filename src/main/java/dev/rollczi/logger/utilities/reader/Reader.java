package dev.rollczi.logger.utilities.reader;

import dev.rollczi.logger.utilities.deserializer.ProcessDeserializer;

public interface Reader {

    String getName();

    void registerListener(ReaderListener listener);

    void unregisterListener(ReaderListener listener);

    ProcessDeserializer getDeserializer();

    void shutdown();

}
