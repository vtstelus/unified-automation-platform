package com.platform.ui.base;

import com.platform.core.constants.FrameworkConstants;
import com.platform.core.driver.DriverFactory;
import com.platform.reporting.logger.ReportLogger;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage — root Page Object class for all UI pages.
 * Provides explicit-wait interaction helpers, screenshot support, and logging.
 * All page classes extend this; never instantiate directly.
 */
public abstract class BasePage {

    protected final Logger log = ReportLogger.get(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    // ── Core interactions ──────────────────────────────────────────

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log.debug("Clicked: {}", element);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        log.debug("Typed '{}' into: {}", text, element);
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try { return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed(); }
        catch (TimeoutException | NoSuchElementException e) { return false; }
    }

    protected boolean isDisplayed(By locator) {
        try { wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); return true; }
        catch (TimeoutException | NoSuchElementException e) { return false; }
    }

    protected void waitForPageToLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
        waitForPageToLoad();
    }

    protected String getPageTitle() { return driver.getTitle(); }
    protected String getCurrentUrl() { return driver.getCurrentUrl(); }
}
