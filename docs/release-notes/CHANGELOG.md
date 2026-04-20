# Changelog

## v1.0.0 — Initial Release
- Multi-module Maven architecture (7 modules)
- UI channel: Selenium 4, WebDriverManager, BasePage with explicit waits
- API channel: REST Assured, RequestBuilder, ContractValidator, JSON Schema validation
- Mobile channel: Appium 2, BrowserStack provider, GestureHelper, thread-safe drivers
- Reporting: ExtentReports 5 wired via ReportBridge (decoupled from core)
- Observability: ObservabilityBus with pluggable ReportBridge interface
- Orchestration: ChangeImpactAnalyser, smart delta test selection
- CI: GitHub Actions with real module-aware delta detection and matrix runners
- Java 17, TestNG 7.10, all dependencies centrally managed in parent POM
