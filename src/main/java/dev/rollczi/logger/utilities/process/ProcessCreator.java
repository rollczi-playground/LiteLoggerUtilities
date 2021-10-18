package dev.rollczi.logger.utilities.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProcessCreator {

    private final String fileName;
    private final String path;
    private final List<String> args;
    private final ProcessType type;

    ProcessCreator(String fileName, String path, List<String> args, ProcessType type) {
        this.fileName = fileName;
        this.path = path;
        this.args = args;
        this.type = type;
    }

    public ProcessBuilder create() {
        ArrayList<String> endArgs = new ArrayList<>(args);

        endArgs.add(path + "\\" + fileName);

        return new ProcessBuilder(endArgs);
    }

    public ProcessType getType() {
        return type;
    }

    public static ProcessCreator normalCreatorPath(String fileName, String path) {
        return new ProcessCreator(fileName, path, Collections.emptyList(), ProcessType.NORMAL);
    }

    public static ProcessCreator normalCreator(String fileName) {
        return new ProcessCreator(fileName, System.getProperty("user.dir"), Collections.emptyList(), ProcessType.NORMAL);
    }

    public static ProcessCreator javaCreatorPath(String fileName, String path, String... flags) {
        List<String> args = new ArrayList<>();

        args.add("java");
        args.addAll(Arrays.asList(flags));
        args.add("-jar");

        return new ProcessCreator(fileName, path, args, ProcessType.JAVA);
    }

    public static ProcessCreator javaCreator(String fileName, String... flags) {
        return javaCreatorPath(fileName, System.getProperty("user.dir"), flags);
    }

}
