package dev.rollczi.logger.utilities.utils;

import java.util.regex.Pattern;

public class Regex {

    public static final Regex STANDARD_BRACKETS = new Regex("\\[([^\\[\\]]*?[^a-zA-Z\\[\\]]{1})?{CONTENT}([^a-zA-Z\\[\\]]{1}[^\\[\\]]*?)?\\]");

    private final String regexPattern;

    public Regex(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    public Pattern create(String content) {
        return Pattern.compile(regexPattern.replaceAll("\\{CONTENT}", content));
    }

    public Pattern create(String content, int flags) {
        return Pattern.compile(regexPattern.replaceAll("\\{CONTENT}", content), flags);
    }

}
