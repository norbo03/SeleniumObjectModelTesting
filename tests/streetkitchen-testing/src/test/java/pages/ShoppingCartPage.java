package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class ShoppingCartPage extends BasePage {
    private final By savedCartsLocator = By.xpath("//main//div[@class=\"row\"]//div[contains(@class, \"shopping-list-item\")]");
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/bevasarlolistak/";
    }

    public int getNumberOfSavedCarts() {
        ArrayList<WebElement> savedCarts = (ArrayList<WebElement>) getElements(savedCartsLocator);
        System.out.println(savedCarts.toString());
        return savedCarts.size();
    }
}
