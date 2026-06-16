# OrangeHRM Test Automation Framework

## Overview
This repository contains a **test automation framework for OrangeHRM**, designed to validate core functionalities of the application through automated UI tests.
The project follows industry best practices, focusing on scalability, maintainability, and readability.

## Overview
E2E Testing and API Testing using REST Assure.

## Tech Stack
- **Language:** Java
- **Build Tool:** Maven  
- **Testing Framework:** TestNG  
- **Automation Tool:** Selenium WebDriver  
- **Design Pattern:** Page Object Model (POM)  

## Project Structure

OrangeHRM/
drivers/                # Browser drivers (e.g., ChromeDriver)
resources/              # Configuration files and test data
src/
    src/main/java/          # Core framework (base classes, utilities)
    src/test/java/          # Test cases and suites (E2E Testing and API Testing)

testng.xml              # Test suite configuration
pom.xml                 # Maven dependencies
test-output.log         # Execution logs
README.md               # Documentation
