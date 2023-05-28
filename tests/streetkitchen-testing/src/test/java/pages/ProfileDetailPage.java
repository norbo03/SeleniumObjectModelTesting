package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URISyntaxException;
import java.util.Objects;

public class ProfileDetailPage extends ProfilePage {

    private final By usernameInputLocator = By.xpath("//div[@role=\"document\"]//div[contains(@class,\"ur-field-item field-nickname\")]//input");
    private final By lastNameInputLocator = By.xpath("//div[@role=\"document\"]//input[@data-id=\"user_registration_last_name\"]");
    private final By firstNameInputLocator = By.xpath("//div[@role=\"document\"]//input[@data-id=\"user_registration_first_name\"]");
    private final By updateDetailsInputLocator = By.xpath("//div[@role=\"document\"]//input[@name=\"save_account_details\"]");
    private final By updateDetailsMessageLocator = By.xpath("//div[@class=\"user-registration-message\"]");
    private final By removeProfilePictureLocator = By.xpath("//div[@role=\"document\"]//button[contains(@class,\"profile-pic-remove\")]");
    private final By imageUploadLocator = By.xpath("//div[@role=\"document\"]//input[@type=\"file\"]");
    public ProfileDetailPage(WebDriver driver) {
        super(driver);
        this.url = "https://streetkitchen.hu/profilom/profilom-szerkesztese/";
    }

    public String getUsername() {
        WebElement usernameInput = getElement(usernameInputLocator);
        return usernameInput.getAttribute("value");
    }
    public String getFirstName() {
        WebElement firstNameInput = getElement(firstNameInputLocator);
        return firstNameInput.getAttribute("value");
    }

    public String getLastName() {
        WebElement lastNameInput = getElement(lastNameInputLocator);
        return lastNameInput.getAttribute("value");
    }
    public void updateProfile(String newUsername, String newLastName, String newFirstName) {
        WebElement usernameInput = getElement(usernameInputLocator);
        WebElement lastNameInput = getElement(lastNameInputLocator);
        WebElement firstNameInput = getElement(firstNameInputLocator);
        WebElement updateDetails = getElement(updateDetailsInputLocator);

        // clear everything
        usernameInput.clear();
        lastNameInput.clear();
        firstNameInput.clear();

        // update with new value
        usernameInput.sendKeys(newUsername);
        lastNameInput.sendKeys(newLastName);
        firstNameInput.sendKeys(newFirstName);

        // click update button
        clickElement(updateDetails);
    }

    public String getUpdateDetailsMessage() {
        WebElement updateDetailsMessage = getElement(updateDetailsMessageLocator);
        return updateDetailsMessage.getText();
    }

    public void removeProfilePicture() {
        WebElement removeProfilePicture = getElement(removeProfilePictureLocator);
        if (removeProfilePicture.isDisplayed()) {
            clickElement(removeProfilePicture);
        }
    }

    public void uploadProfilePicture(String imgPath) {

        WebElement imageUpload = getElement(imageUploadLocator);
        imageUpload.sendKeys(imgPath);

        WebElement updateDetails = getElement(updateDetailsInputLocator);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)", "");

        clickElement(updateDetails);
    }
}
