package pages;

import org.openqa.selenium.By;

public interface SearchablePage {
    By searchBarLocator = By.xpath("//div[@class=\"nav-containers\"]//form[@role=\"search\"]//input[@class=\"search-field\"]");

    default SearchResultPage search(String search) {
        return null;
    };
    default boolean isThereResult() {
        return true;
    }
}
