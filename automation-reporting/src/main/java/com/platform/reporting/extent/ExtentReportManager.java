package com.platform.reporting.extent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.platform.core.constants.FrameworkConstants;
import com.platform.core.observability.ReportBridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

/**
 * ExtentReportManager — implements ReportBridge and provides rich HTML reporting.
 * Thread-safe: each thread maintains its own ExtentTest instance.
 * Register this with ObservabilityBus at suite startup.
 */
public final class ExtentReportManager implements ReportBridge {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public synchronized void initReport() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.REPORT_PATH);
            spark.config().setDocumentTitle("Unified Automation Platform — Test Report");
            spark.config().setReportName("Automation Results");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Platform", "Unified Automation Platform v1.0");
            log.info("ExtentReport initialised at: {}", FrameworkConstants.REPORT_PATH);
        }
    }

    @Override
    public void startTest(String testName, String description) {
        testThread.set(extent.createTest(testName, description));
    }

    @Override public void logPass(String message) {
        if (testThread.get() != null) testThread.get().pass(message);
    }

    @Override public void logFail(String message, Throwable throwable) {
        if (testThread.get() != null) {
            testThread.get().fail(message);
            if (throwable != null) testThread.get().fail(throwable);
        }
    }

    @Override public void logSkip(String message) {
        if (testThread.get() != null) testThread.get().skip(message);
    }

    @Override public void attachScreenshot(byte[] screenshot) {
        if (testThread.get() != null && screenshot.length > 0) {
            String b64 = Base64.getEncoder().encodeToString(screenshot);
            testThread.get().fail("Failure screenshot",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(b64).build());
        }
    }

    @Override public synchronized void flushReport() {
        if (extent != null) { extent.flush(); log.info("ExtentReport flushed"); }
    }
}
