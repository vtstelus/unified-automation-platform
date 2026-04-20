package com.platform.api.client;

import com.platform.core.base.BaseTest;
import com.platform.core.data.lifecycle.TestDataLifecycleManager;
import com.platform.core.observability.ObservabilityBus;
import com.platform.reporting.extent.ExtentReportManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * BaseApiTest — base for all API test classes.
 * Manages report bridge registration and data lifecycle per test method.
 */
public abstract class BaseApiTest extends BaseTest {

    protected TestDataLifecycleManager dataManager;

    @BeforeSuite(alwaysRun = true)
    @Override
    public void suiteSetup() {
        ObservabilityBus.registerBridge(new ExtentReportManager());
        super.suiteSetup();
    }

    @BeforeMethod(alwaysRun = true)
    public void apiSetup() {
        dataManager = new TestDataLifecycleManager();
    }

    @AfterMethod(alwaysRun = true)
    public void apiCleanup() {
        if (dataManager != null) dataManager.cleanup();
    }
}
