package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage implements SearchablePage {

    private final By searchBarLocator = By.xpath("//div[@class=\"nav-containers\"]//form[@role=\"search\"]//input[@class=\"search-field\"]");
    private final By logoutButtonLocator = By.xpath("//a[@href=\"/profilom/kilepes/\"]");
    private final By profileDetailLinkLocator = By.xpath("//div[@role=\"document\"]//li[contains(@class, \"user-registration-MyAccount-navigation-link user-registration-MyAccount-navigation-link--edit-profile\")]//a");

    public ProfilePage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/profilom/";
    }

    public void logout() {
        WebElement logoutButton = getElement(logoutButtonLocator);
        clickElement(logoutButton);
    }

    public ProfileDetailPage navigateToProfileDetailPage() {
        WebElement profileDetailLink = getElement(profileDetailLinkLocator);
        clickElement(profileDetailLink);
        return new ProfileDetailPage(this.driver);
    }

    @Override
    public SearchPage search(String search) {
        WebElement searchBar = getElement(searchBarLocator);
        searchBar.sendKeys(search);
        searchBar.submit();
        return new SearchPage(this.driver);
    }
}
