package pages;

import org.openqa.selenium.WebDriver;

public class StaticPage extends BasePage {

    private final String url;
    public StaticPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    @Override
    public String getBody() {
        // first load the given url
        this.driver.get(this.url);
        // then get the body
        return super.getBody();
    }
}
