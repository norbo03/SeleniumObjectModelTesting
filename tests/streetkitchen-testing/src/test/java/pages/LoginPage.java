package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private final By emailInputLocator = By.xpath("//div[@class='wrap']//form//input[@id='username']");
    private final By passwordInputLocator = By.xpath("//div[@class='wrap']//form//input[@id='password']");
    private final By loginButtonLocator = By.xpath("//div[@role=\"document\"]//form//input[@type=\"submit\" and @name=\"login\" and @value=\"Belépés\"]");
    public LoginPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/belepes/";
    }

    @Override
    public void load() {
        tryCloseAd();
        super.load();
    }

    public ProfilePage login(String email, String password) {
        WebElement emailInput = getElement(emailInputLocator);
        WebElement passwordInput = getElement(passwordInputLocator);
        WebElement loginButton = getElement(loginButtonLocator);

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        clickElement(loginButton);

        return new ProfilePage(this.driver);
    }
}
