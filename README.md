# Bookstore API Test Automation

## Framework Overview
A comprehensive test automation framework for validating Bookstore API endpoints using modern testing practices.

## Features
- Complete CRUD operations testing
- Environment-specific configurations
- TestNG test management
- CI/CD integration with GitHub Actions
- Detailed test reporting

## Prerequisites
- Java 17+
- Maven 3.8+

## Installation
1. Clone the repository
2. Install dependencies:

-pip install -r requirements.txt

## Running Tests
- Run all tests
- mvn test

# Run with custom base URL
- mvn test -DBASE_URL=http://localhost:8000

# Run specific test class
- mvn test -Dtest=CreateBookTest

# Test Structure
    src/test/java/
    ├── tests/
    │   ├── CreateBookTest.java
    │   ├── GetBookTest.java
    │   ├── UpdateBookTest.java
    │   └── DeleteBookTest.java
    └── config/
    └── TestBase.java

# CI/CD Pipeline
    Automated workflow includes:

    Server startup

    Test execution

    Results archiving

    Artifact upload
# Reporting

    Test results available in:
    
    Console output
    
    Surefire reports in target/surefire-reports/
    
    GitHub Actions artifacts