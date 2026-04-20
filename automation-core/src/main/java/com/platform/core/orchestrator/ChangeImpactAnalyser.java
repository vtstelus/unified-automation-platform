package com.platform.core.orchestrator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * ChangeImpactAnalyser — maps Git diff output to impacted TestNG groups.
 * Used by CI pipeline for delta-only test execution.
 */
public class ChangeImpactAnalyser {

    private static final Logger log = LogManager.getLogger(ChangeImpactAnalyser.class);

    private static final Map<String, List<String>> MODULE_GROUP_MAP = Map.of(
        "automation-ui",     List.of("ui", "smoke"),
        "automation-api",    List.of("api", "smoke"),
        "automation-mobile", List.of("mobile", "smoke"),
        "automation-core",   List.of("smoke", "regression")
    );

    public List<String> resolveImpactedGroups(List<String> changedFiles) {
        if (changedFiles == null || changedFiles.isEmpty()) {
            log.info("No changed files — returning smoke only");
            return List.of("smoke");
        }
        Set<String> groups = new LinkedHashSet<>();
        for (String file : changedFiles) {
            MODULE_GROUP_MAP.forEach((module, moduleGroups) -> {
                if (file.contains(module)) groups.addAll(moduleGroups);
            });
        }
        if (groups.isEmpty()) groups.add("smoke");
        log.info("Impacted groups for {} changed files: {}", changedFiles.size(), groups);
        return new ArrayList<>(groups);
    }
}
