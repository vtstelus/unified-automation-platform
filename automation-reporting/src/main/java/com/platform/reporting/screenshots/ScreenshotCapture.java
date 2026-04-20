package com.platform.reporting.screenshots;

import com.platform.core.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ScreenshotCapture — captures screenshots and returns byte[] for report embedding.
 * Driver is passed as parameter — no hidden coupling to DriverFactory.
 */
public final class ScreenshotCapture {

    private static final Logger log = LogManager.getLogger(ScreenshotCapture.class);

    private ScreenshotCapture() {}

    public static byte[] captureAsBytes(WebDriver driver) {
        try {
            byte[] shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            log.debug("Screenshot captured: {} bytes", shot.length);
            return shot;
        } catch (Exception e) {
            log.warn("Screenshot failed: {}", e.getMessage());
            return new byte[0];
        }
    }

    public static String captureToFile(WebDriver driver, String fileName) {
        try {
            Files.createDirectories(Path.of(FrameworkConstants.SCREENSHOT_PATH));
            byte[] shot = captureAsBytes(driver);
            Path dest   = Path.of(FrameworkConstants.SCREENSHOT_PATH, fileName + ".png");
            Files.write(dest, shot);
            log.info("Screenshot saved: {}", dest);
            return dest.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not save screenshot: " + fileName, e);
        }
    }
}
