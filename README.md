# LiteLoggerUtilities
### A small utilities to running, reading and logging processes.
Run and manage processes easier and more conveniently!

#### Logger
```java
LoggersManager loggersManager = new LoggersManager();
Logger logger = loggersManager.newLogger("lite_logger");

logger.registerListener(record -> {
    //send the record wherever you want
});
```
#### Process
```java
ProcessCreator exeCreator = ProcessCreator.normalCreator("test.exe", "path\\to\\file");
ProcessCreator exeCreator = ProcessCreator.normalCreator("test.exe"); //user.dir folder
ProcessCreator javaCreator = ProcessCreator.javaCreator("test.jar", "-Xms256m", "-Xmx2048m");

ProcessService processService = new ProcessService();

processService.handleNewProcess("java_process", javaCreator).peek(process -> {
    //if all is successful you will get your java process
});
//...
processService.shutdown();
```
#### Reader
The reader deserialize logs this way by default:<br>
`process -> inputStream -> LogRecord(LogLevel.INFO, String)`<br>
`process -> errorStream -> LogRecord(LogLevel.SEVERE, String)`<br>
```java
ReaderService readerService = new ReaderService();
ProcessReader processReader = new ProcessReader.Builder("lite_process", process)
        .registerListener(record -> logger.publishLog(record))
        .build();

readerService.registerReader(processReader);
//...
readerService.shutdown();
```
#### Custom log deserializer in Reader
If you want you can add your own log deserializer for inputStream and errorStream.
```java
ReaderService readerService = new ReaderService();
Regex regex = Regex.STANDARD_BRACKETS;
RegexDeserializer deserializer = RegexDeserializer.builder()
        .regex(LogLevel.WARNING, regex.create("WARN", Pattern.CASE_INSENSITIVE))
        .build();

ProcessReader processReader = new ProcessReader.Builder("lite_process", process)
        .deserializeLine(deserializer)
        .deserializeError(line -> new LogRecord(LogLevel.WARNING, line))
        .registerListener(logger::publishLog)
        .build();

readerService.registerReader(processReader);
//...
readerService.shutdown();
```