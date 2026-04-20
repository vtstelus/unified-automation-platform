package com.platform.reporting.artifacts;

import com.platform.core.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ArtifactPublisher — ensures artifact output directories exist
 * and summarises what was published after a run.
 */
public final class ArtifactPublisher {

    private static final Logger log = LogManager.getLogger(ArtifactPublisher.class);

    private ArtifactPublisher() {}

    public static void prepareOutputDirectories() {
        createDir(FrameworkConstants.SCREENSHOT_PATH);
        createDir(FrameworkConstants.ARTIFACT_PATH);
        createDir("target/logs/");
        createDir("target/reports/");
        log.info("Output directories ready");
    }

    private static void createDir(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            log.warn("Could not create directory {}: {}", path, e.getMessage());
        }
    }

    public static void summarise() {
        log.info("Artifacts available at:");
        log.info("  Report     → {}", FrameworkConstants.REPORT_PATH);
        log.info("  Screenshots→ {}", FrameworkConstants.SCREENSHOT_PATH);
        log.info("  Logs       → {}", FrameworkConstants.LOG_PATH);
    }
}
