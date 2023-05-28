package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class SearchResultPage extends BasePage implements SearchablePage {
    private final By pizzaTitleLocator = By.xpath("//article[1]//div[@data-postid=\"12674181\"]/parent::article/div[@class=\"entry-content\"]//header/h2/a");
    private final By firstRowLocator = By.xpath("//main//div[@class=\"content--search-page\"]//div[@class=\"row\"][1]");
    private final By firstRowChildrenLocator = By.xpath("//main//div[@class=\"content--search-page\"]//div[@class=\"row\"][1]/div[contains(@class,\"col-content-item-small\")]");
    private final By addToShoppingCartLocator = By.xpath(".//div[@data-postid and contains(@class, \"sk-circle sk-shopping-list\")]");
    private final By cartNameInputLocator = By.xpath("//div[contains(@class, \"popup-list-input-container\")]//input[@name=\"shopping_list_input\"]");
    private final By saveCartButtonLocator = By.xpath("//div[contains(@class, \"popup-list-input-container\")]/following-sibling::button[contains(@class, \"save-shopping-list-popup\")]");
    private final By shoppingListsLocator = By.xpath("//div[contains(@class, \"popup-list-input-container\")]/select[@name=\"shopping_lists\"]//option");
    private final By shoppingListsSelectLocator = By.xpath("//select[@id=\"shopping_lists\"]");
    private final By shoppingListsOptionsLocator = By.xpath("//select[@id=\"shopping_lists\"]/option");
    private final By shoppingCartButtonLocator = By.xpath("//nav[contains(@class, \"nav-user-logged-in\")]//a[@href=\"/bevasarlolistak\"]");

    public SearchResultPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/kereses/";
    }

    public String getPizzaTitle() {
        return getElement(pizzaTitleLocator).getText();
    }

    public boolean addRandomResultToShoppingList(String cartName) {
        List<WebElement> firstRowContent = getElements(firstRowChildrenLocator);
        System.out.println("found " + firstRowContent.size() + " elements");
        WebElement randomResult = firstRowContent.get(new Random().nextInt(firstRowContent.size()));
        System.out.println("random result: " + randomResult.getText());
        System.out.println(randomResult);

        WebElement addToShoppingListButton = randomResult.findElement(addToShoppingCartLocator);
        System.out.println("found button: " + addToShoppingListButton);
        clickElement(addToShoppingListButton);

        WebElement saveCartButton = getElement(saveCartButtonLocator);
        WebElement shoppingListsSelect = getElement(shoppingListsSelectLocator);
        clickElement(shoppingListsSelect);
        List<WebElement> shoppingLists = getElements(shoppingListsOptionsLocator);

        boolean isNewList = false;
        boolean isInList = shoppingLists.stream().map(WebElement::getText).anyMatch(cartName::equals);
        if (shoppingLists.size() > 0 && isInList) {
            int shoppingListIndex = shoppingLists.stream().map(WebElement::getText).toList().indexOf(cartName);
            WebElement shoppingList = shoppingLists.get(shoppingListIndex);
            shoppingList.click();
            clickElement(saveCartButton);
        } else {
            isNewList = true;
            clickElement(shoppingListsSelect);
            WebElement cartNameInput = getElement(cartNameInputLocator);
            // wait for element to be clickable
            this.wait.until(ExpectedConditions.elementToBeClickable(cartNameInput));
            cartNameInput.sendKeys(cartName);
            clickElement(saveCartButton);
        }

        return isNewList;
    }

    public ShoppingCartPage openShoppingCartPage() {
        WebElement shoppingCartButton = getElement(shoppingCartButtonLocator);
        clickElement(shoppingCartButton);
        return new ShoppingCartPage(this.driver);
    }
    @Override
    public boolean isThereResult() {
        List<WebElement> firstRowContent = getElements(firstRowChildrenLocator);
        return firstRowContent.size() > 0;
    }
}
