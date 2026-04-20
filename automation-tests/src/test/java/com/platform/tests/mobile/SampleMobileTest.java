package com.platform.tests.mobile;

import com.platform.core.retry.RetryController;
import com.platform.mobile.screens.BaseMobileTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SampleMobileTest — mobile smoke tests.
 * Extend BaseMobileTest; add Screen Object interactions here.
 */
public class SampleMobileTest extends BaseMobileTest {

    @Test(groups = {"smoke", "mobile"}, retryAnalyzer = RetryController.class,
          description = "Verify app launches and driver context is available")
    public void verifyAppLaunch() {
        String context = driver().getContext();
        Assert.assertNotNull(context, "Driver context should not be null after launch");
        log.info("App context: {}", context);
    }
}
