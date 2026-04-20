package com.platform.core.base;

import com.platform.core.config.ConfigManager;
import com.platform.core.driver.DriverFactory;
import com.platform.core.observability.ObservabilityBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest — root lifecycle class for all test channels.
 * Handles suite setup, per-test logging, teardown, and observability hooks.
 */
public abstract class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected ConfigManager config;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        config = ConfigManager.getInstance();
        ObservabilityBus.init();
        log.info("===== Suite Starting | env: {} =====", config.get("env", "dev"));
    }

    @AfterSuite(alwaysRun = true)
    public void suiteCleanup() {
        ObservabilityBus.flush();
        log.info("===== Suite Complete =====");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(ITestResult result) {
        String name = result.getMethod().getMethodName();
        log.info("----- Starting: {} -----", name);
        ObservabilityBus.startTest(name, result.getMethod().getDescription());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) {
        ObservabilityBus.publishResult(result);
        DriverFactory.quitDriver();
        log.info("----- Finished: {} | {} -----",
                result.getMethod().getMethodName(),
                result.isSuccess() ? "PASSED" : "FAILED");
    }
}
