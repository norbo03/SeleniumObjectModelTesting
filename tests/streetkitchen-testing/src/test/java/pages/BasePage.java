package pages;

import configuration.Configuration;
import configuration.entity.ECredential;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    private static final ECredential CONFIG = Configuration.getInstance().getCredentials();
    private final By bodyLocator = By.tagName("body");
    private final By popupAdLocator = By.xpath("//div[@class='ad-popup']//div[@class='ad-popup-close']");
    protected final By loginPageButtonLocator = By.xpath("//nav[contains(@class, \"nav-user-logged-out\")]//a[@href='/belepes/']");

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait popupWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 5);
        this.popupWait = new WebDriverWait(this.driver, 1);
    }

    public void load(String url) {
        // setConsent();
        this.driver.get(url);
    }

    private void TryCloseAd() {
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

    protected void clickElement(WebElement clickElement) {
        try {
            TryCloseAd();
            clickElement.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element not yet clickable(ElementClickInterceptedException): " + clickElement.toString());

            // we try to close the popup
            TryCloseAd();

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
}