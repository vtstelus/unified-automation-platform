package com.platform.core.data.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * TestDataLifecycleManager — seed before test, cleanup after.
 * Ensures zero cross-test data pollution per test method.
 */
public class TestDataLifecycleManager {

    private static final Logger log = LogManager.getLogger(TestDataLifecycleManager.class);
    private final List<Runnable> cleanupTasks = new ArrayList<>();

    public <T> T seed(Supplier<T> seedAction) {
        log.info("Seeding test data...");
        T result = seedAction.get();
        log.info("Seeded: {}", result);
        return result;
    }

    public void registerCleanup(Runnable task) {
        cleanupTasks.add(task);
        log.debug("Registered cleanup task #{}", cleanupTasks.size());
    }

    public void cleanup() {
        log.info("Running {} cleanup task(s)", cleanupTasks.size());
        cleanupTasks.forEach(task -> {
            try { task.run(); }
            catch (Exception e) { log.warn("Cleanup task failed: {}", e.getMessage()); }
        });
        cleanupTasks.clear();
    }
}
