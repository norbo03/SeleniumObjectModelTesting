package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    private final By mainHeaderLocator = By.xpath("/html/body/div[4]");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://streetkitchen.hu/");
    }

    public WebElement getMainHeader() {
        return getElement(mainHeaderLocator);
    }
}