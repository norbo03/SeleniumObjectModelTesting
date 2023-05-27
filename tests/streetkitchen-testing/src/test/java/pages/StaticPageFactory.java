package pages;

import org.openqa.selenium.WebDriver;

public class StaticPageFactory {

    private static StaticPageFactory INSTANCE = null;
    private String url;

    private StaticPageFactory() {
    }

    public static StaticPageFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE =  new StaticPageFactory();
        }
        return INSTANCE;
    }

    public StaticPage create(WebDriver driver, String url) {
        return new StaticPage(driver, url);
    }
}
