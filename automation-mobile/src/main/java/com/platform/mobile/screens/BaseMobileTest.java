package com.platform.mobile.screens;

import com.platform.core.base.BaseTest;
import com.platform.core.driver.MobileDriverFactory;
import com.platform.core.observability.ObservabilityBus;
import com.platform.reporting.extent.ExtentReportManager;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

/**
 * BaseMobileTest — base for all mobile test classes.
 */
public abstract class BaseMobileTest extends BaseTest {

    @BeforeSuite(alwaysRun = true)
    @Override
    public void suiteSetup() {
        ObservabilityBus.registerBridge(new ExtentReportManager());
        super.suiteSetup();
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void afterTest(ITestResult result) {
        ObservabilityBus.publishResult(result);
        MobileDriverFactory.quitDriver();
    }

    protected AppiumDriver driver() {
        return MobileDriverFactory.getDriver();
    }
}
