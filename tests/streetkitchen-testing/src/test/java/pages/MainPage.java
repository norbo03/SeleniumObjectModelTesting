package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    private final By mainHeaderLocator = By.xpath("/html/body/div[4]");
    private final By loginPageButtonLocatorLoggedIn = By.xpath("//nav[contains(@class, \"nav-user-logged-in\")]//a[@href='/belepes/']");

    public MainPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/";
        // System.out.println("Cookies at MainPage before: " + this.driver.manage().getCookies());
        load();
        // System.out.println("Cookies at MainPage after: " + this.driver.manage().getCookies());
    }

    public WebElement getMainHeader() {
        return getElement(mainHeaderLocator);
    }

    public LoginPage navigateToLoginPage() {
        WebElement loginLink =  getElement(loginPageButtonLocator);
        System.out.println("loginPageButton not yet clicked");
        clickElement(loginLink);
        System.out.println("loginPageButton clicked");
        return new LoginPage(this.driver);
    }
}