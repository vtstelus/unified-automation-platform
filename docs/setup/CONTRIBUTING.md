# Contributing to the Unified Automation Platform

## PR Process
1. Branch from `develop` — never commit directly to `main`
2. Write or update tests in `automation-tests` only
3. Framework changes go into the relevant module (`automation-core`, `automation-ui`, etc.)
4. All PRs must pass the CI smoke suite before review
5. Version bump follows Semver: PATCH for fixes, MINOR for features, MAJOR for breaking changes

## Branch Naming
- `feature/your-feature`
- `fix/your-fix`
- `chore/your-chore`

## Adding a New Page Object
1. Create your class in `automation-ui/src/main/java/com/platform/ui/pages/`
2. Extend `BasePage`
3. Use `@FindBy` annotations + `wait` helpers — no raw `driver.findElement` calls

## Adding a New API Test
1. Create your test in `automation-tests/src/test/java/com/platform/tests/api/`
2. Extend `BaseApiTest`
3. Use `ApiClient` for requests and `ContractValidator` for assertions

## Release Process
1. Platform team merges `develop` → `main`
2. CI builds and publishes JAR to GitHub Packages automatically
3. Teams update `pom.xml` version and run smoke suite to verify
