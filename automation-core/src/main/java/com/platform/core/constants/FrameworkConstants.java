package com.platform.core.constants;

/**
 * Platform-wide constants — single source of truth for paths, timeouts, and tags.
 */
public final class FrameworkConstants {

    // Wait timeouts
    public static final int EXPLICIT_WAIT_SECONDS  = 20;
    public static final int IMPLICIT_WAIT_SECONDS  = 0;  // keep 0 — use explicit waits only
    public static final int PAGE_LOAD_TIMEOUT      = 30;

    // Output paths
    public static final String REPORT_PATH         = "target/reports/ExtentReport.html";
    public static final String SCREENSHOT_PATH     = "target/screenshots/";
    public static final String LOG_PATH            = "target/logs/automation.log";
    public static final String ARTIFACT_PATH       = "target/artifacts/";

    // TestNG group tags
    public static final String GROUP_SMOKE         = "smoke";
    public static final String GROUP_REGRESSION    = "regression";
    public static final String GROUP_UI            = "ui";
    public static final String GROUP_API           = "api";
    public static final String GROUP_MOBILE        = "mobile";
    public static final String GROUP_E2E           = "e2e";

    private FrameworkConstants() {}
}
