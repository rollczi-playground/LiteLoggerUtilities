package dev.rollczi.logger.utilities.reader;

import panda.std.Option;
import panda.std.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class RunnableDirectInputReader implements Runnable {

    private final BufferedReader bufferedReader;
    private final Consumer<String> nextLineConsumer;

    RunnableDirectInputReader(InputStream inputStream, Consumer<String> nextLineConsumer) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.nextLineConsumer = nextLineConsumer;
    }

    @Override
    public void run() {
        while (true) {
            Result<Option<String>, IOException> result = Result.attempt(IOException.class, () -> Option.of(bufferedReader.readLine()));

            if (result.isErr()) {
                result.getError().printStackTrace();
                return;
            }

            Option<String> option = result.get();

            if (option.isEmpty()) {
                return;
            }

            nextLineConsumer.accept(option.get());
        }
    }

}
