package com.platform.core.listener;

import com.platform.core.observability.ObservabilityBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

/**
 * TestEventListener — intercepts all TestNG lifecycle events.
 * Bridges TestNG → ObservabilityBus → Reporting.
 * Register via testng.xml <listeners> block.
 */
public class TestEventListener implements ITestListener, ISuiteListener {

    private static final Logger log = LogManager.getLogger(TestEventListener.class);

    @Override public void onStart(ISuite suite) {
        log.info("===== Suite: {} started =====", suite.getName());
    }

    @Override public void onFinish(ISuite suite) {
        log.info("===== Suite: {} finished =====", suite.getName());
        ObservabilityBus.flush();
    }

    @Override public void onTestStart(ITestResult r) {
        log.info(">> {}", r.getMethod().getMethodName());
        ObservabilityBus.startTest(r.getMethod().getMethodName(), r.getMethod().getDescription());
    }

    @Override public void onTestSuccess(ITestResult r) {
        log.info("<< PASSED: {}", r.getMethod().getMethodName());
        ObservabilityBus.publishResult(r);
    }

    @Override public void onTestFailure(ITestResult r) {
        log.error("<< FAILED: {} | {}", r.getMethod().getMethodName(),
                r.getThrowable() != null ? r.getThrowable().getMessage() : "unknown");
        ObservabilityBus.publishResult(r);
    }

    @Override public void onTestSkipped(ITestResult r) {
        log.warn("<< SKIPPED: {}", r.getMethod().getMethodName());
        ObservabilityBus.publishResult(r);
    }
}
