package com.platform.mobile.screens;

import com.platform.core.constants.FrameworkConstants;
import com.platform.core.driver.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BaseScreen — root screen class for all mobile screen objects.
 * Platform-agnostic: works for Android and iOS via Appium 2.
 */
public abstract class BaseScreen {

    protected final Logger log = LogManager.getLogger(getClass());
    protected final AppiumDriver driver;
    protected final WebDriverWait wait;

    protected BaseScreen() {
        this.driver = MobileDriverFactory.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT_SECONDS));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void tap(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log.debug("Tapped: {}", element);
    }

    protected void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        log.debug("Entered: {}", text);
    }

    protected String readText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected boolean isVisible(WebElement element) {
        try { return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}
