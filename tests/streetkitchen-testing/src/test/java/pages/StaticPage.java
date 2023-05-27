package pages;

import org.openqa.selenium.WebDriver;

public class StaticPage extends BasePage {

    private final String url;
    public StaticPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;

        // load the page
        this.driver.get(this.url);
    }
}
