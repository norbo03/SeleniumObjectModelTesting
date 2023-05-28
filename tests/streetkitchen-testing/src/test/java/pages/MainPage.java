package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage implements SearchablePage {

    private final By mainHeaderLocator = By.xpath("/html/body/div[4]");

    public MainPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/";

        tryCloseAd();
        load();
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

    @Override
    public SearchResultPage search(String search) {
        WebElement searchBar = getElement(searchBarLocator);
        searchBar.sendKeys(search);
        searchBar.submit();
        return new SearchResultPage(this.driver);
    }
}