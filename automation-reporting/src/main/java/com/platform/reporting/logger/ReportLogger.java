package com.platform.reporting.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ReportLogger — structured log helper for test steps.
 * Use inside page objects and test helpers for consistent log format.
 */
public final class ReportLogger {

    private ReportLogger() {}

    public static Logger get(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static void step(Logger log, String stepDescription) {
        log.info("  STEP → {}", stepDescription);
    }

    public static void verify(Logger log, String assertion) {
        log.info("  VERIFY → {}", assertion);
    }
}
