package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {

    private final By pizzaTitleLocator = By.xpath("//article[1]//div[@data-postid=\"12674181\"]/parent::article/div[@class=\"entry-content\"]//header/h2/a");
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getPizzaTitle() {
        return getElement(pizzaTitleLocator).getText();
    }
}
