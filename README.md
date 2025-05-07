# 📚 Bookstore E2E API Automation Framework

A robust API automation suite for Bookstore application, built using **Java**, **TestNG**, and **Cucumber BDD**, with **Rest Assured** for API interaction and **Maven** for build management.

---

## 💻 Tech Stack Overview

| Component         | Description                                                                 |
|------------------|-----------------------------------------------------------------------------|
| 🧠 **IDE**        | IntelliJ IDEA                                                               |
| ☕ **Language**    | Java 17+                                                                    |
| 🔄 **Framework**  | Rest Assured + Cucumber BDD – For readable, behavior-driven API automation |
| 🛠 **Build Tool** | Maven – For dependency management & CI/CD integrations                      |
| ✅ **Test Execution** | TestNG – For test execution, parallel runs, retries, and CI/CD integration |
| 📊 **Reporting**  | Maven Surefire Reports – Default test report with HTML/XML outputs          |


## ✅ Features

- **Readable Test Scenarios**: Using Gherkin syntax in `.feature` files.
- **API Testing with Rest Assured**: Fluent API testing DSL.
- **Parallel Test Execution**: Enabled via TestNG.
- **Simple Reporting**: Surefire-generated reports under `target/surefire-reports`.
- **CI/CD Ready**: Easily integrated into Jenkins or other pipeline tools.

---

## 📡 Sample Endpoints Covered

- `POST /signup/`
- `POST /login/`
- `POST /books/`
- `GET /books/`
- `GET /books/{id}`
- `PUT /books/{id}`
- `DELETE /books/{id}`

---

## ▶️ How to Run

### 🧰 Prerequisites

- Java 17+
- Maven

### 🚀 Run Tests

```bash
mvn clean test
```
# View Allure Report
    
    mvn allure:report
    
    mvn allure:serve


# View SureFire Report

    target/surefire-reports/index.html


