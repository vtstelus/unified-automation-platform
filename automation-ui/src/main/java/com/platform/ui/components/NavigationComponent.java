package com.platform.ui.components;

import com.platform.ui.base.BasePage;
import org.openqa.selenium.By;

/**
 * NavigationComponent — reusable navigation bar component.
 * Shared across multiple pages using the component pattern.
 */
public class NavigationComponent extends BasePage {

    private final By logoLocator = By.cssSelector(".logo");
    private final By menuItems   = By.cssSelector("nav a");

    public NavigationComponent() { super(); }

    public boolean isLogoDisplayed() { return isDisplayed(logoLocator); }

    public void clickMenuItem(String itemText) {
        driver.findElements(menuItems).stream()
            .filter(el -> el.getText().equalsIgnoreCase(itemText))
            .findFirst()
            .ifPresent(this::click);
    }
}
