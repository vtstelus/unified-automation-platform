# Unified Automation Platform — v1.0

Enterprise-grade multi-module test automation platform supporting UI, API, and Mobile channels.

## Module Overview

| Module | Purpose |
|---|---|
| `automation-core` | Engine: config, drivers, base classes, orchestrator, observability |
| `automation-reporting` | ExtentReports, screenshots, artifact publishing |
| `automation-testdata` | Configs, JSON/CSV payloads, JSON schemas |
| `automation-ui` | UI adapter: BasePage, browser factory, pages, components |
| `automation-api` | API adapter: client, builders, validators, contracts |
| `automation-mobile` | Mobile adapter: screens, gestures, capabilities, providers |
| `automation-tests` | Actual test classes only — no framework code here |

## Quick Start

```bash
# Build all modules
mvn clean install -DskipTests

# Run smoke suite (dev, Chrome)
mvn test -pl automation-tests -Denv=dev -Dbrowser=chrome \
  -Dsurefire.suiteXmlFiles=src/test/resources/testng/testng-smoke.xml

# Run API tests (staging)
mvn test -pl automation-tests -Denv=staging -Dgroups=api

# Run on BrowserStack
mvn test -pl automation-tests -Ddriver.mode=browserstack \
  -DBS_USER=xxx -DBS_KEY=yyy -Dplatform=android
```

## Dependency Graph

```
automation-tests
  ├── automation-ui       → automation-core, automation-reporting
  ├── automation-api      → automation-core, automation-reporting
  ├── automation-mobile   → automation-core, automation-reporting
  └── automation-testdata
        automation-core
        automation-reporting → automation-core
```

## Contributing
See `docs/setup/CONTRIBUTING.md`. All PRs must pass CI smoke suite before merge.
