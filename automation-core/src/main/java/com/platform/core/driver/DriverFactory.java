package com.platform.core.driver;

import com.platform.core.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory — creates and manages thread-safe WebDriver instances.
 * Each thread gets its own isolated driver via ThreadLocal.
 */
public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driverThread.get() == null) initDriver();
        return driverThread.get();
    }

    private static void initDriver() {
        ConfigManager cfg = ConfigManager.getInstance();
        String browser   = cfg.getBrowser().toLowerCase();
        boolean headless = cfg.isHeadless();
        log.info("Initialising WebDriver — browser: {} headless: {} thread: {}",
                browser, headless, Thread.currentThread().getId());

        WebDriver driver = switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("-headless");
                yield new FirefoxDriver(opts);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver();
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                opts.addArguments("--start-maximized", "--disable-notifications");
                if (headless) opts.addArguments("--headless=new");
                yield new ChromeDriver(opts);
            }
        };
        driverThread.set(driver);
        log.info("WebDriver ready: {}", driver.getClass().getSimpleName());
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
            log.info("WebDriver quit — thread: {}", Thread.currentThread().getId());
        }
    }
}
