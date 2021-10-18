package dev.rollczi.logger.utilities.reader;

import dev.rollczi.logger.utilities.LogRecord;

@FunctionalInterface
public interface ReaderListener {

    void publishReadiedLog(LogRecord logRecord);

}
