package org.skydemo.core.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class LogbackConfigurator {

    public static void configureFileLog(Path logFilePath) {
        // Create a logger context
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Create a file appender
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("FILE");

        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(loggerContext);
        fileEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        fileEncoder.start();

        fileAppender.setEncoder(fileEncoder);
        fileAppender.setFile(logFilePath.toString());
        fileAppender.start();

        // Create a logger and add appenders
        Logger logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.DEBUG);
        logger.addAppender(fileAppender);
    }
}
