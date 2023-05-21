import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

import org.junit.*;

public class SampleTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String TEST_URL = "http://the-internet.herokuapp.com/login";

    private final By bodyLocator = By.tagName("body");
    private final By mainHeaderLocator = By.tagName("h2");
    private final By usernameInputLocator = By.id("username");
    private final By passwordInputLocator = By.id("password");
    private final By loginButtonLocator = By.xpath("//*[@id=\"login\"]/button");
    private final By statusMessageLocator = By.id("flash");
    private final By logoutButtonLocator = By.xpath("//*[@id=\"content\"]/div/a");

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();

        this.wait = new WebDriverWait(driver, 5);
    }

    private WebElement getElement(By locator) {
        WebElement element = null;

        try {
            element = this.driver.findElement(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator.toString());

            // We try to wait for the element

            this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element = this.driver.findElement(locator);
        }

        return element;
    }

    /**
     * Load the web page and wait for the body to load
     */
    private void loadPage() {
        this.driver.get(TEST_URL);
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(bodyLocator));
    }

    /**
     * Login to the page with given credentials
     *
     * @param username
     * @param password
     */
    private void login(String username, String password) {
        WebElement usernameInput = getElement(usernameInputLocator);
        WebElement passwordInput = getElement(passwordInputLocator);
        WebElement loginButton = getElement(loginButtonLocator);

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        loginButton.click();
    }

    @Test
    public void testWebPageAvailability() {
        loadPage();
        WebElement body = getElement(bodyLocator);

        WebElement mainHeader = getElement(mainHeaderLocator);

        Assert.assertTrue(mainHeader.getText().contains("Login Page"));
    }

    @Test
    public void testWebPageWorkFlow() {
        loadPage();

        login("tomsmith", "SuperSecretPassword!");
        WebElement successMessage = getElement(statusMessageLocator);
        Assert.assertTrue(successMessage.getText().contains("You logged into a secure area!"));

        WebElement logoutButton = getElement(logoutButtonLocator);
        logoutButton.click();

        WebElement successMessageForLogout = getElement(statusMessageLocator);
        Assert.assertTrue(successMessageForLogout.getText().contains("You logged out of the secure area!"));

        login("invalidusername", "invalidpassword");
        WebElement wrongCredentialsMessage = getElement(statusMessageLocator);
        Assert.assertTrue(wrongCredentialsMessage.getText().contains("Your username is invalid!"));

        login("tomsmith", "invalidpassword");
        WebElement wrongPasswordMessage = getElement(statusMessageLocator);
        Assert.assertTrue(wrongPasswordMessage.getText().contains("Your password is invalid!"));

        login("wrongUsername", "SuperSecretPassword!");
        WebElement wrongUsernameMessage = getElement(statusMessageLocator);
        Assert.assertTrue(wrongUsernameMessage.getText().contains("Your username is invalid!"));
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
