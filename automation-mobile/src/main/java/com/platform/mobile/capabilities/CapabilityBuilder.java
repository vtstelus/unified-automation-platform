package com.platform.mobile.capabilities;

import com.platform.core.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * CapabilityBuilder — builds Appium 2 device capabilities from config.
 * Provider-decoupled: same interface works for local and BrowserStack.
 */
public final class CapabilityBuilder {

    private static final Logger log = LogManager.getLogger(CapabilityBuilder.class);

    private CapabilityBuilder() {}

    public static DesiredCapabilities build(String platform, String mode) {
        ConfigManager cfg = ConfigManager.getInstance();
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName",           platform);
        caps.setCapability("appium:automationName",  "ios".equalsIgnoreCase(platform) ? "XCUITest" : "UIAutomator2");
        caps.setCapability("appium:deviceName",      cfg.get("device.name", "Android Emulator"));
        caps.setCapability("appium:platformVersion", cfg.get("device.version", "13.0"));
        caps.setCapability("appium:app",             cfg.get("app.path"));
        caps.setCapability("appium:newCommandTimeout", 300);
        caps.setCapability("appium:autoGrantPermissions", true);

        if ("browserstack".equalsIgnoreCase(mode)) {
            caps.setCapability("browserstack.user",    cfg.get("browserstack.user"));
            caps.setCapability("browserstack.key",     cfg.get("browserstack.key"));
            caps.setCapability("browserstack.debug",   "true");
            caps.setCapability("browserstack.networkLogs", "true");
            log.info("BrowserStack capabilities applied");
        }

        log.info("Capabilities built — platform: {} mode: {}", platform, mode);
        return caps;
    }
}
