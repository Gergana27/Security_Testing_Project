package telerikTests;

import com.Demo.wperevest.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testframework.PropertiesManager;
import testframework.core.BaseWebTest;

import java.io.IOException;


public class TelerikTests extends BaseWebTest {
    private LoginPage loginPage;

    @BeforeEach
    public void beforeTests() {
        loginPage = new LoginPage();
        loginPage.navigate();
    }

    @Test
    public void bruteForceLoginTest() throws IOException, InterruptedException {

        String email = PropertiesManager.getConfigProperties().getProperty("email");
        String filePath = "src/test/resources/top-200-popular-passwords.txt";

        String successfulPassword = loginPage.tryLoginWithPasswords(email, filePath);
        boolean isLoggedIn = loginPage.isLoggedIn();

        // Assert
        Assertions.assertNotNull(successfulPassword, "No successful login after testing common passwords.");
        System.out.println("Successful login with password: " + successfulPassword);


        Assertions.assertTrue(isLoggedIn, "User is not logged in successfully.");
        System.out.println("User is logged in successfully.");
    }
}

