package com.platform.ui.base;

import com.platform.core.base.BaseTest;
import com.platform.core.driver.DriverFactory;
import com.platform.reporting.extent.ExtentReportManager;
import com.platform.core.observability.ObservabilityBus;
import org.testng.annotations.BeforeSuite;

/**
 * BaseUiTest — base class for all UI test classes.
 * Wires the ExtentReport bridge and navigates to baseUrl before each test.
 */
public abstract class BaseUiTest extends BaseTest {

    @BeforeSuite(alwaysRun = true)
    @Override
    public void suiteSetup() {
        ObservabilityBus.registerBridge(new ExtentReportManager());
        super.suiteSetup();
    }

    protected String getBaseUrl() {
        return config.getBaseUrl();
    }

    protected org.openqa.selenium.WebDriver driver() {
        return DriverFactory.getDriver();
    }
}
