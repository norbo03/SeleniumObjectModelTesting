import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import pages.MainPage;

import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

import org.junit.*;

public class StreetkitchenTest {

    private WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        // this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        // set chromedriver path
        System.setProperty("webdriver.chrome.driver", "chromedriver_linux64/chromedriver");
        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
    }

    @Test
    public void testMainPageAvailability() {
        MainPage mainPage = new MainPage(this.driver);

        Assert.assertTrue(mainPage.getTitle().contains("Street Kitchen"));

        WebElement mainHeader = mainPage.getMainHeader();

        Assert.assertTrue(mainHeader.getText().contains("A nap receptje:"));
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
