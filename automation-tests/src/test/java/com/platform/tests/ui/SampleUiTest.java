package com.platform.tests.ui;

import com.platform.core.retry.RetryController;
import com.platform.ui.base.BaseUiTest;
import com.platform.ui.pages.SampleHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SampleUiTest — UI smoke and regression tests.
 * Extend BaseUiTest; use Page Objects only — no driver calls in test class.
 */
public class SampleUiTest extends BaseUiTest {

    @Test(groups = {"smoke", "ui"}, retryAnalyzer = RetryController.class,
          description = "Verify home page loads and heading is visible")
    public void verifyHomePageLoads() {
        driver().get(getBaseUrl());
        SampleHomePage homePage = new SampleHomePage();
        Assert.assertTrue(homePage.isLoaded(), "Home page heading should be visible");
        log.info("Home page loaded — title: {}", homePage.getPageTitle());
    }

    @Test(groups = {"regression", "ui"},
          description = "Verify page title is not empty")
    public void verifyPageTitle() {
        driver().get(getBaseUrl());
        String title = driver().getTitle();
        Assert.assertNotNull(title, "Page title must not be null");
        Assert.assertFalse(title.isEmpty(), "Page title must not be empty");
    }
}
