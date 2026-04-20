package com.platform.core.observability;

/**
 * ReportBridge — interface that decouples automation-core from automation-reporting.
 * The reporting module provides the implementation and registers it at startup.
 */
public interface ReportBridge {
    void initReport();
    void startTest(String testName, String description);
    void logPass(String message);
    void logFail(String message, Throwable throwable);
    void logSkip(String message);
    void attachScreenshot(byte[] screenshot);
    void flushReport();
}
