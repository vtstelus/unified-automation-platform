package com.platform.core.driver;

import com.platform.core.config.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * MobileDriverFactory — thread-safe Appium driver for Android and iOS.
 * Supports local Appium server and BrowserStack cloud.
 */
public final class MobileDriverFactory {

    private static final Logger log = LogManager.getLogger(MobileDriverFactory.class);
    private static final ThreadLocal<AppiumDriver> mobileThread = new ThreadLocal<>();

    private MobileDriverFactory() {}

    public static AppiumDriver getDriver() {
        if (mobileThread.get() == null) initDriver();
        return mobileThread.get();
    }

    private static void initDriver() {
        ConfigManager cfg      = ConfigManager.getInstance();
        String platform        = cfg.getPlatform().toLowerCase();
        String mode            = cfg.getDriverMode();
        String hubUrl          = mode.equalsIgnoreCase("browserstack")
                ? cfg.get("browserstack.hub")
                : cfg.get("appium.server.url", "http://localhost:4723");

        log.info("Initialising MobileDriver — platform: {} mode: {} thread: {}",
                platform, mode, Thread.currentThread().getId());

        DesiredCapabilities caps = buildCapabilities(cfg, platform, mode);
        try {
            AppiumDriver driver = "ios".equalsIgnoreCase(platform)
                    ? new IOSDriver(new URL(hubUrl), caps)
                    : new AndroidDriver(new URL(hubUrl), caps);
            mobileThread.set(driver);
            log.info("MobileDriver ready — platform: {}", platform);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid hub URL: " + hubUrl, e);
        }
    }

    private static DesiredCapabilities buildCapabilities(ConfigManager cfg, String platform, String mode) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName",         platform);
        caps.setCapability("appium:automationName","ios".equalsIgnoreCase(platform) ? "XCUITest" : "UIAutomator2");
        caps.setCapability("appium:deviceName",    cfg.get("device.name", "Android Emulator"));
        caps.setCapability("appium:platformVersion",cfg.get("device.version", "13.0"));
        caps.setCapability("appium:app",           cfg.get("app.path"));
        if ("browserstack".equalsIgnoreCase(mode)) {
            caps.setCapability("browserstack.user",  cfg.get("browserstack.user"));
            caps.setCapability("browserstack.key",   cfg.get("browserstack.key"));
            caps.setCapability("browserstack.debug", "true");
        }
        return caps;
    }

    public static void quitDriver() {
        AppiumDriver driver = mobileThread.get();
        if (driver != null) {
            driver.quit();
            mobileThread.remove();
            log.info("MobileDriver quit — thread: {}", Thread.currentThread().getId());
        }
    }
}
