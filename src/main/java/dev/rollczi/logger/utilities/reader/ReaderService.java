package dev.rollczi.logger.utilities.reader;

import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReaderService {

    private final Map<String, Reader> readers = new ConcurrentHashMap<>();

    public void registerReader(Reader reader) {
        readers.put(reader.getName(), reader);
    }

    public Collection<Reader> getReaders() {
        return Collections.unmodifiableCollection(readers.values());
    }

    public Option<Reader> getReader(String name) {
        return Option.of(readers.get(name));
    }

    public void shutdown() {
        readers.forEach((name, reader) -> reader.shutdown());
    }
}
