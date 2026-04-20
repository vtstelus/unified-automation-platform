package com.platform.mobile.providers;

import io.appium.java_client.AppiumDriver;

/**
 * DeviceProvider — pluggable device provider interface.
 * Implement for Local, BrowserStack, SauceLabs, or any cloud provider.
 */
public interface DeviceProvider {
    AppiumDriver createDriver();
    String getProviderName();
}
