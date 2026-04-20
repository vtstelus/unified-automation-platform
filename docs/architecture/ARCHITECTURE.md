# Platform Architecture Overview

## Layers (top to bottom)

1. **CI/CD Trigger** — GitHub Actions with real delta detection and matrix runners
2. **Test Orchestration** — Smart test selection, parallel distribution, retry control
3. **Scalability Model** — Thread pools per channel, CI matrix concurrency
4. **Channel Adapters** — UI (Selenium 4), API (REST Assured), Mobile (Appium 2 / BrowserStack)
5. **Observability** — ObservabilityBus → ExtentReports + Log4j2
6. **Distribution** — Published as Maven JAR to GitHub Packages

## Module Dependency Rules
- `automation-core` has NO dependency on any other platform module
- `automation-reporting` depends only on `automation-core`
- `automation-ui/api/mobile` depend on `core` + `reporting` only
- `automation-tests` depends on all channel modules — no framework code here
- `automation-testdata` has no Java sources — resources only
