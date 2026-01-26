# Web & API Automation Testing – Demoblaze & DummyAPI
Repository ini berisi automation test framework untuk Web UI dan API dalam satu project, menggunakan Java + Gradle + Cucumber (BDD). Framework ini mengetes:

Web UI: situs Demoblaze untuk skenario belanja (add to cart, cart, checkout).
​

API: layanan DummyAPI (user & tag endpoint) dengan autentikasi app-id.
​

Teknologi & Tools
Bahasa: Java 17

Build Tool: Gradle 9

BDD: Cucumber (Gherkin feature + step definitions)
​

Web UI: Selenium WebDriver + Chrome (headless di CI)
​

API: RestAssured untuk HTTP request/response validation
​

Assertion: AssertJ

WebDriver Management: WebDriverManager

CI/CD: GitHub Actions (manual trigger & Pull Request)

