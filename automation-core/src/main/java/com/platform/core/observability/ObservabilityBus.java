package com.platform.core.observability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

/**
 * ObservabilityBus — central event bus streaming execution events
 * to logging and the reporting layer.
 * Decoupled: core does not depend on automation-reporting directly;
 * the reporting module wires in via the ReportBridge.
 */
public final class ObservabilityBus {

    private static final Logger log = LogManager.getLogger(ObservabilityBus.class);
    private static ReportBridge reportBridge;

    private ObservabilityBus() {}

    public static void registerBridge(ReportBridge bridge) {
        reportBridge = bridge;
        log.info("ReportBridge registered: {}", bridge.getClass().getSimpleName());
    }

    public static void init() {
        log.info("ObservabilityBus initialised");
        if (reportBridge != null) reportBridge.initReport();
    }

    public static void startTest(String testName, String description) {
        if (reportBridge != null) reportBridge.startTest(testName, description);
    }

    public static void publishResult(ITestResult result) {
        String name = result.getMethod().getMethodName();
        switch (result.getStatus()) {
            case ITestResult.SUCCESS -> { log.info("PASSED: {}", name); if (reportBridge != null) reportBridge.logPass(name); }
            case ITestResult.FAILURE -> { log.error("FAILED: {}", name); if (reportBridge != null) reportBridge.logFail(name, result.getThrowable()); }
            case ITestResult.SKIP   -> { log.warn("SKIPPED: {}", name); if (reportBridge != null) reportBridge.logSkip(name); }
        }
    }

    public static void flush() {
        if (reportBridge != null) reportBridge.flushReport();
        log.info("ObservabilityBus flushed");
    }
}
