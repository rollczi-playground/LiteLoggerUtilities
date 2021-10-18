package dev.rollczi.logger.utilities.reader;

import dev.rollczi.logger.utilities.LogRecord;
import dev.rollczi.logger.utilities.deserializer.SimpleErrorDeserializer;
import dev.rollczi.logger.utilities.deserializer.SimpleLineDeserializer;
import dev.rollczi.logger.utilities.deserializer.ProcessDeserializer;
import dev.rollczi.logger.utilities.deserializer.LineDeserializer;
import dev.rollczi.logger.utilities.Logger;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class ProcessReader implements Reader {

    private final String name;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Set<ReaderListener> listeners = ConcurrentHashMap.newKeySet();
    private final ProcessDeserializer processDeserializer;

    ProcessReader(String name, Process process, Set<ReaderListener> listeners, ProcessDeserializer processDeserializer) {
        this.listeners.addAll(listeners);
        this.name = name;
        this.processDeserializer = processDeserializer;

        this.registerStream(process.getInputStream(), processDeserializer::getLineDeserialize);
        this.registerStream(process.getErrorStream(), processDeserializer::getErrorDeserialize);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void registerListener(ReaderListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(ReaderListener listener) {
        listeners.remove(listener);
    }

    @Override
    public ProcessDeserializer getDeserializer() {
        return processDeserializer;
    }

    private void registerStream(InputStream stream, Function<String, LogRecord> function) {
        executorService.submit(new RunnableDirectInputReader(stream, line -> {
            LogRecord record = function.apply(line);
            listeners.forEach(listener -> listener.publishReadiedLog(record));
        }));
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    public static class Builder {
        private final String name;
        private final Process process;
        private final Set<ReaderListener> listeners = new HashSet<>();
        private LineDeserializer lineDeserializer = new SimpleLineDeserializer();
        private LineDeserializer errorDeserializer = new SimpleErrorDeserializer();

        public Builder(String name, Process process) {
            this.name = name;
            this.process = process;
        }

        public Builder registerLogger(Logger logger) {
            listeners.add(logger::publishLog);
            return this;
        }

        public Builder registerListener(ReaderListener listener) {
            listeners.add(listener);
            return this;
        }

        public Builder deserializeLine(LineDeserializer lineDeserializer) {
            this.lineDeserializer = lineDeserializer;
            return this;
        }

        public Builder deserializeError(LineDeserializer errorDeserializer) {
            this.errorDeserializer = errorDeserializer;
            return this;
        }

        public ProcessReader build() {
            return new ProcessReader(name, process, listeners, new ProcessDeserializer(lineDeserializer, errorDeserializer));
        }
    }

}
