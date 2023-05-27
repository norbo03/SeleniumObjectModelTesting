import configuration.Configuration;
import configuration.entity.EStaticPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.MainPage;
import pages.StaticPage;
import pages.StaticPageFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StreetkitchenTest {

    private static Configuration CONFIG;
    private static StaticPageFactory STATIC_PAGE_FACTORY;
    private static WebDriver driver;

    @BeforeAll
    public static void setup() throws MalformedURLException {
        System.out.println("Starting tests...");
        System.out.println("Loading configuration...");

        CONFIG = Configuration.getInstance();
        STATIC_PAGE_FACTORY = StaticPageFactory.getInstance();

        System.out.println("Configuration loaded: " + CONFIG.toString());

        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        // set chromedriver path
        // System.setProperty("webdriver.chrome.driver", "chromedriver_linux64/chromedriver");
        // driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Disabled
    @Test
    public void testMainPageAvailability() {
        MainPage mainPage = new MainPage(driver);

        assertTrue(mainPage.getTitle().contains("Street Kitchen"));

        WebElement mainHeader = mainPage.getMainHeader();

        assertTrue(mainHeader.getText().contains("A nap receptje:"));
    }

    @Disabled
    @Test
    public void testConfigLoad() {
        assertEquals("myeamil@email.com", CONFIG.getCredentials().getEmail());
        assertEquals("mypassword", CONFIG.getCredentials().getPassword());
        assertEquals("myusername", CONFIG.getCredentials().getUsername());
    }

    @ParameterizedTest
    @MethodSource("configuration.Configuration#staticPages")
    public void testUsedPageLoad(EStaticPage page) {
        StaticPage staticPage = STATIC_PAGE_FACTORY.create(driver, page.getUrl());

        assertEquals(page.getTitle(), staticPage.getTitle());

        String bodyText = staticPage.getBody();
        page.getKeywords().forEach(keyword -> assertTrue(bodyText.contains(keyword)));
    }

    @AfterAll
    public static void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
