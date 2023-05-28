package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage {

    private final By searchBarLocator = By.xpath("//div[@class=\"nav-containers\"]//form[@role=\"search\"]//input[@class=\"search-field\"]");
    private final By logoutButtonLocator = By.xpath("//a[@href=\"/profilom/kilepes/\"]");
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public SearchPage search(String search) {
        WebElement searchBar = getElement(searchBarLocator);
        searchBar.sendKeys(search);
        searchBar.submit();
        return new SearchPage(this.driver);
    }

    public void logout() {
        WebElement logoutButton = getElement(logoutButtonLocator);
        clickElement(logoutButton);
    }
}
