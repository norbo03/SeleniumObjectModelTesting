package pages;

import configuration.Configuration;
import configuration.entity.ECredential;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BasePage {

    private static final ECredential CONFIG = Configuration.getInstance().getCredentials();
    protected String url;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait popupWait;
    private final By bodyLocator = By.tagName("body");
    private final By popupAdLocator = By.xpath("//div[@class='ad-popup']//div[@class='ad-popup-close']");
    protected final By loginPageButtonLocator = By.xpath("//nav[contains(@class, \"nav-user-logged-out\")]//a[@href='/belepes/']");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 5);
        this.popupWait = new WebDriverWait(this.driver, 2);
        tryCloseAd();
    }

    //    public void load(String url) {
    //        // setConsent();
    //        this.driver.get(url);
    //    }

    public void tryCloseAd() {
        try {
            System.out.println("Try to close popup");
            this.popupWait.until(ExpectedConditions.elementToBeClickable(popupAdLocator));
            WebElement popupAdClose = getElement(popupAdLocator);
            System.out.println("Popup found");
            popupAdClose.click();
            System.out.println("Popup closed");
        } catch (TimeoutException ex) {
            System.out.println("Popup timeout failed");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Popup not found");
        }
    }

    protected WebElement getElement(By locator) {
        WebElement element = null;

        try {
            element = this.driver.findElement(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator.toString());

            // We try to wait for the element

            this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            // this.wait.until(ExpectedConditions.elementToBeClickable(locator));
            element = this.driver.findElement(locator);
        }

        System.out.println("Element found: " + locator.toString());
        return element;
    }

    protected List<WebElement> getElements(By locator) {
        List<WebElement> elements = null;

        try {
            elements = this.driver.findElements(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator.toString());

            // We try to wait for the element

            this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            // this.wait.until(ExpectedConditions.elementToBeClickable(locator));
            elements = this.driver.findElements(locator);
        }

        System.out.println("Elements found: " + locator.toString());
        return elements;
    }

    protected void clickElement(WebElement clickElement) {
        try {
             tryCloseAd();
            clickElement.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element not yet clickable(ElementClickInterceptedException): " + clickElement.toString());

            // we try to close the popup
            tryCloseAd();

            // then we try to click again
            clickElement.click();
        } catch (Exception e) {
            System.out.println("Element not yet clickable: " + clickElement.toString());
            e.printStackTrace();
        }
    }

    public String getBody() {
        WebElement body = getElement(bodyLocator);
        return body.getText();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public boolean isLoggedOut() {
        try {
            WebElement loginLink = getElement(loginPageButtonLocator);
            return loginLink.isDisplayed();
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void load() {
        tryCloseAd();
        this.driver.get(this.url);
    }
}