package dev.rollczi.logger.utilities.process;

public class ProcessServiceConfiguration {

    private int maximumPoolSize = 15;

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

}
