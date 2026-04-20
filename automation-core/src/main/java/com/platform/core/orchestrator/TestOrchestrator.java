package com.platform.core.orchestrator;

import com.platform.core.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TestOrchestrator — determines execution scope from CI input
 * and delegates to the appropriate channel.
 */
public class TestOrchestrator {

    private static final Logger log = LogManager.getLogger(TestOrchestrator.class);

    public void orchestrate() {
        ConfigManager cfg   = ConfigManager.getInstance();
        String channel      = cfg.get("execution.channel", "all");
        boolean deltaOnly   = cfg.getBool("delta.only", false);

        log.info("Orchestrating | channel: {} | deltaOnly: {}", channel, deltaOnly);

        ExecutionChannel resolved = switch (channel.toUpperCase()) {
            case "UI"     -> ExecutionChannel.UI;
            case "API"    -> ExecutionChannel.API;
            case "MOBILE" -> ExecutionChannel.MOBILE;
            default       -> null;
        };

        if (resolved != null) log.info("Routing to channel: {}", resolved);
        else log.info("Running all channels");
    }
}
