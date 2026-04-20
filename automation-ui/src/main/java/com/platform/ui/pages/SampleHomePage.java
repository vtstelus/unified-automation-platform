package com.platform.ui.pages;

import com.platform.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

/**
 * SampleHomePage — example Page Object. Replace with your real application pages.
 */
public class SampleHomePage extends BasePage {

    @FindBy(css = "h1")
    private WebElement heading;

    private final By searchInput = By.name("q");

    public SampleHomePage() { super(); }

    public String getHeadingText() {
        return getText(heading);
    }

    public boolean isLoaded() {
        return isDisplayed(heading);
    }

    public void search(String keyword) {
        type(searchInput, keyword);
        log.info("Searched for: {}", keyword);
    }
}
