package pages;

import org.openqa.selenium.WebDriver;

public class StaticPage extends BasePage {

    public StaticPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;

        // load the page
        load();
    }
}
