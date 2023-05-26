import configuration.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.MainPage;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StreetkitchenTest {

    private static Configuration CONFIG;
    private static WebDriver driver;

    @Disabled
    @BeforeAll
    public static void setup() throws MalformedURLException {
        CONFIG = Configuration.getInstance();
        ChromeOptions options = new ChromeOptions();
         driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        // set chromedriver path
        // System.setProperty("webdriver.chrome.driver", "chromedriver_linux64/chromedriver");
        // driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }


    @ParameterizedTest
    @NullSource
    void isBlank_ShouldReturnTrueForNullInputs(String input) {
        System.out.println("\n\n\n\n\n\n\n---------------------input: " + input + "\n\n\n\n\n\n\n");
        assertTrue(isBlank(input));
    }

    @Test
    public void testMainPageAvailability() {
        MainPage mainPage = new MainPage(driver);

        assertTrue(mainPage.getTitle().contains("Street Kitchen"));

        WebElement mainHeader = mainPage.getMainHeader();

        assertTrue(mainHeader.getText().contains("A nap receptje:"));
    }

    @Test
    public void testConfigLoad() {
        assertEquals("myeamil@email.com", CONFIG.getCredentials().getEmail());
        assertEquals("mypassword", CONFIG.getCredentials().getPassword());
        assertEquals("myusername", CONFIG.getCredentials().getUsername());
    }

//    @Disabled
//    @ParameterizedTest
//    @ValueSource(strings = {"myusername", "myusername2"})
//    public void testUsedPageLoad(String username) {
//        assertEquals(username, username);
//    }

    @AfterAll
    public static void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
