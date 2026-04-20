package com.platform.core.retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryController — retries ONLY infrastructure failures, never functional ones.
 * Functional bugs must not be silently retried — they must show as FAILED.
 */
public class RetryController implements IRetryAnalyzer {

    private static final Logger log  = LogManager.getLogger(RetryController.class);
    private static final int MAX_RETRY = 2;
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (isInfrastructureFailure(result) && retryCount < MAX_RETRY) {
            retryCount++;
            log.warn("Infrastructure failure — retrying [{}/{}]: {}",
                    retryCount, MAX_RETRY, result.getMethod().getMethodName());
            return true;
        }
        return false;
    }

    private boolean isInfrastructureFailure(ITestResult result) {
        Throwable t = result.getThrowable();
        if (t == null) return false;
        String msg   = t.getMessage() != null ? t.getMessage().toLowerCase() : "";
        String klass = t.getClass().getSimpleName().toLowerCase();
        return msg.contains("connection refused")
            || msg.contains("timeout")
            || msg.contains("session not created")
            || klass.contains("webdriverexception")
            || klass.contains("sessionnotcreatedexception")
            || klass.contains("noalertpresentexception");
    }
}
