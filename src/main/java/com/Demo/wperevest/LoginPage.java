package com.Demo.wperevest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testframework.PropertiesManager;
import testframework.core.BaseWebPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoginPage extends BaseWebPage {

    //Locators
    private final By emailField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButtonField = By.xpath("//input[@name='login']");
    private final By errorMessageField = By.xpath("//ul[@class='user-registration-error']");
    private final By successfulLoginField = By.xpath("//h2[normalize-space()='Login Protection']");
    private final By clickHereButtonField = By.xpath("//a[@class='btn btn-default']");
    private final By clickingOnCheckmarkField = By.xpath("//div[@class='recaptcha-checkbox-border']");
    private final By verifyButtonField = By.xpath("//input[@value=' Verify ']");
    private final By loggedInField = By.xpath("//div[@id='user-registration']");

    public LoginPage() {
        super("");
    }

    @Override
    public String getBasePageUrl() {
        return PropertiesManager.getConfigProperties().getProperty("loginPageUrl");
    }


    //Actions
    public boolean isLoginBlocked() {
        return !driver().findElements(successfulLoginField).isEmpty();
    }

    public void enterEmail(String email) {
        driverWait().until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver().findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driverWait().until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver().findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driverWait().until(ExpectedConditions.visibilityOfElementLocated(loginButtonField));
        driver().findElement(loginButtonField).click();
    }

    public boolean isErrorMessagePresent() {
        return !driver().findElements(errorMessageField).isEmpty();
    }

    public List<String> getPasswordsFromFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public void handleLoginProtection(String email) throws InterruptedException {
        try {
            driverWait().until(ExpectedConditions.visibilityOfElementLocated(clickHereButtonField));
            driver().findElement(clickHereButtonField).click();
            System.out.println("Clicked on 'Click here' button to unlock.");

            driverWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.
                    xpath("//iframe[contains(@title, 'reCAPTCHA')]")));
            driverWait().until(ExpectedConditions.elementToBeClickable(clickingOnCheckmarkField)).click();
            System.out.println("Clicked on reCAPTCHA checkbox.");

            driver().switchTo().defaultContent();

            driverWait().until(ExpectedConditions.visibilityOfElementLocated(verifyButtonField));
            driver().findElement(verifyButtonField).click();
            System.out.println("Clicked on 'Verify' button to confirm.");

        } catch (TimeoutException e) {
            System.out.println("Login Protection button not found. Pausing for 30 minutes...");
            Thread.sleep(30 * 60 * 1000);
            System.out.println("30 minutes have passed. Reloading login page...");

            navigate();
            enterEmail(email);
        }
    }

    public String tryLoginWithPasswords(String email, String filePath) throws IOException, InterruptedException {
        List<String> passwords = getPasswordsFromFile(filePath);
        int attempt = 0;
        enterEmail(email);
        for (String password : passwords) {
            attempt++;
            enterPassword(password);
            clickLoginButton();

            System.out.println("Login attempt " + attempt + " with password: " + password);

            if (!isErrorMessagePresent() && isLoggedIn()) {
                System.out.println("Successful login with password: " + password);
                return password;
            }

            System.out.println("Failed login attempt with password: " + password);

            if (isLoginBlocked()) {
                handleLoginProtection(email);
            }
        }

        return null;
    }

    public boolean isLoggedIn() {
        return !driver().findElements(loggedInField).isEmpty();
    }
}
