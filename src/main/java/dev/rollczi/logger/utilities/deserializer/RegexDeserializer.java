package dev.rollczi.logger.utilities.deserializer;

import dev.rollczi.logger.utilities.LogLevel;
import dev.rollczi.logger.utilities.LogRecord;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class RegexDeserializer implements LineDeserializer {

    private final Map<Pattern, LogLevel> patterns;

    private RegexDeserializer(Map<Pattern, LogLevel> patterns) {
        this.patterns = patterns;
    }

    public LogRecord deserialize(String line) {
        for (Map.Entry<Pattern, LogLevel> entry : patterns.entrySet()) {
            if (!entry.getKey().matcher(line).find()) {
                continue;
            }

            return new LogRecord(entry.getValue(), line);
        }

        return new LogRecord(LogLevel.INFO, line);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Pattern, LogLevel> patterns = new ConcurrentHashMap<>();

        public Builder regex(LogLevel level, String regex) {
            patterns.put(Pattern.compile(regex), level);
            return this;
        }

        public Builder regex(LogLevel level, String regex, int flags) {
            patterns.put(Pattern.compile(regex, flags), level);
            return this;
        }

        public Builder regex(LogLevel level, Pattern pattern) {
            patterns.put(pattern, level);
            return this;
        }

        public RegexDeserializer build() {
            return new RegexDeserializer(patterns);
        }

    }

}
