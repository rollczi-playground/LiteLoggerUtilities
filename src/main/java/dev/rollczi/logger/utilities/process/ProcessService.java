package dev.rollczi.logger.utilities.process;

import panda.std.Option;
import panda.std.Result;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessService {

    private final ExecutorService executorService;
    private final Map<String, Process> processInRunTime = new ConcurrentHashMap<>();
    private final ProcessServiceConfiguration config;

    public ProcessService() {
        this(new ProcessServiceConfiguration());
    }

    private ProcessService(ProcessServiceConfiguration config) {
        this.config = config;
        this.executorService = Executors.newFixedThreadPool(config.getMaximumPoolSize());
    }

    public Result<Process, ErrorInfo> handleNewProcess(String name, ProcessCreator processCreator) {
        return Option.attempt(IOException.class, () -> processCreator.create().start())
                .peek(process -> processInRunTime.put(name, process))
                .peek(process -> executorService.submit((Callable<Integer>) process::waitFor))
                .map(Result::<Process, ErrorInfo>ok)
                .orElseGet(() -> Result.error(ErrorInfo.START_ERROR));
    }

    public Collection<Process> getCollectionProcess() {
        return Collections.unmodifiableCollection(processInRunTime.values());
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public enum ErrorInfo {
        START_ERROR
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ProcessServiceConfiguration config = new ProcessServiceConfiguration();

        public Builder config(ProcessServiceConfiguration config) {
            if (config == null) {
                throw new NullPointerException();
            }

            this.config = config;
            return this;
        }

        public ProcessService create() {
            return new ProcessService(config);
        }

    }

}
