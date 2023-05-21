package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By bodyLocator = By.tagName("body");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement getElement(By locator) {
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

    protected String getBody() {
        WebElement body = getElement(bodyLocator);
        return body.getText();
    }
}