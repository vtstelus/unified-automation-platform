package com.platform.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager — resolves environment-specific configuration.
 * Loads config/{env}.properties + common.properties.
 * System property overrides file property at runtime.
 */
public final class ConfigManager {

    private static final Logger log = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties props = new Properties();

    private ConfigManager() {
        String env = System.getProperty("env", "dev");
        load("config/common.properties");
        load("config/" + env + ".properties");
        log.info("ConfigManager initialised — env: {}", env);
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }

    private void load(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is != null) { props.load(is); log.debug("Loaded: {}", path); }
            else log.warn("Config file not found: {}", path);
        } catch (IOException e) {
            log.error("Failed to load config: {}", path, e);
        }
    }

    public String get(String key) {
        return System.getProperty(key, props.getProperty(key, ""));
    }

    public String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    public int getInt(String key, int defaultValue) {
        try { return Integer.parseInt(get(key, String.valueOf(defaultValue))); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public boolean getBool(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    // Convenience accessors
    public String getBaseUrl()    { return get("base.url"); }
    public String getApiBaseUrl() { return get("api.base.url"); }
    public String getBrowser()    { return get("browser", "chrome"); }
    public String getPlatform()   { return get("platform", "android"); }
    public String getDriverMode() { return get("driver.mode", "local"); }
    public boolean isHeadless()   { return getBool("headless", false); }
}
