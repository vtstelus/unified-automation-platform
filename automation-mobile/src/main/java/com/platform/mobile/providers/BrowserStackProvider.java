package com.platform.mobile.providers;

import com.platform.core.config.ConfigManager;
import com.platform.mobile.capabilities.CapabilityBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * BrowserStackProvider — real device cloud provider implementation.
 */
public class BrowserStackProvider implements DeviceProvider {

    private static final Logger log = LogManager.getLogger(BrowserStackProvider.class);

    @Override
    public AppiumDriver createDriver() {
        ConfigManager cfg = ConfigManager.getInstance();
        String platform   = cfg.getPlatform().toLowerCase();
        String hubUrl     = cfg.get("browserstack.hub");
        try {
            var caps = CapabilityBuilder.build(platform, "browserstack");
            AppiumDriver driver = "ios".equalsIgnoreCase(platform)
                    ? new IOSDriver(new URL(hubUrl), caps)
                    : new AndroidDriver(new URL(hubUrl), caps);
            log.info("BrowserStack driver created — platform: {}", platform);
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid BrowserStack hub URL: " + hubUrl, e);
        }
    }

    @Override public String getProviderName() { return "BrowserStack"; }
}
