## Description
This project focuses on security testing, specifically on performing a brute-force attack to test the vulnerability of a login page against commonly used passwords. The goal is to attempt login using a list of the top 200 most popular passwords to check if the system is vulnerable to easily guessed passwords.
The `TelerikTests` class implements a test for logging into a website. The test uses a list of the 200 most popular passwords stored in a text file (top-200-popular-passwords.txt) and attempts to log in using each password sequentially.

### Workflow
#### Login Attempts: The system tries each password from the list of 200 most common passwords in sequence.
#### Cooldown Handling: If the system locks the account after a certain number of failed attempts, the test waits for 30 minutes before continuing with the next password.
#### Successful Login Detection: Once the correct password is found, the test stops and prints the successful password.
#### Test Assertion: The test asserts that the correct password is found and the user is logged in successfully.


### Core Package
- `BaseApiService.java`: Contains base methods for API services.
- `BaseApiTest.java`: Includes common setup and teardown methods for API tests.
- `BaseWebPage.java`: Contains base methods for web page interactions.
- `BaseWebTest.java`: Includes common setup and teardown methods for web tests.

### Enums Package
- `BrowserMode.java`: Enum for different browser modes (e.g., headless, normal).
- `BrowserType.java`: Enum for supported browser types.
- `FrameworkSettings.java`: Enum for framework configuration settings.
- `Driver.java`: Class for managing the WebDriver instance.
- `DriverManager.java`: Manages WebDriver creation and lifecycle.
- `PropertiesManager.java`: Manages framework properties and configurations.

### Resources
The `resources` folder configuration files, test data, or other non-Java resources.

### Test
The `test` folder is where test classes would be placed, following a similar package structure as the `main` folder.

## Usage

This framework is designed to support both API and Web testing, with a particular focus on automating security testing scenarios such as brute-force login attempts. The brute-force test makes use of a list of commonly used passwords to test the strength of the authentication system against weak or easily guessable passwords.