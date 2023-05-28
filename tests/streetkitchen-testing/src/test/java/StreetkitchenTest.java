import com.github.javafaker.Faker;
import configuration.Configuration;
import configuration.entity.ECredential;
import configuration.entity.EStaticPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class StreetkitchenTest {

    private static Configuration CONFIG;
    private static StaticPageFactory STATIC_PAGE_FACTORY;
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {

        System.out.println("Starting tests...");
        System.out.println("Loading configuration...");

        CONFIG = Configuration.getInstance();
        STATIC_PAGE_FACTORY = StaticPageFactory.getInstance();

        System.out.println("Configuration loaded: " + CONFIG.toString());

        ChromeOptions options = new ChromeOptions();
        // options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        // driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);

        // set chromedriver path
        System.setProperty("webdriver.chrome.driver", "chromedriver_linux64/chromedriver");
        driver = new ChromeDriver(options);

        setConsent();

        driver.manage().window().maximize();
    }

    private static void setConsent() {
        doCookieHack();
        //        driver.get("https://streetkitchen.hu/");
        //        Date tomorrow = new Date(Calendar.getInstance().getTimeInMillis() + 86400000);
        //        Cookie cookie = new Cookie.Builder("euconsent-v2", CONFIG.getCredentials().getEuconsent_v2())
        //                .domain(".streetkitchen.hu")
        //                .path("/")
        //                .expiresOn(tomorrow)
        //                .isSecure(true)
        //                .build();
        //        driver.manage().addCookie(cookie);

    }

    private static void doCookieHack() {
        driver.get("https://streetkitchen.hu/");
        By acceptBtnLocator = By.xpath("//div[contains(@class, 'qc-cmp2-summary-buttons')]//button[@mode=\"primary\"]");
        driver.findElement(acceptBtnLocator).click();
        Set<Cookie> cookies = driver.manage().getCookies();

        cookies.forEach(cookie -> driver.manage().addCookie(cookie));

        //        Date tomorrow = new Date(Calendar.getInstance().getTimeInMillis() + 86400000);
        //        Cookie cookie = new Cookie.Builder("euconsent-v2", CONFIG.getCredentials().getEuconsent_v2())
        //                .domain(".streetkitchen.hu")
        //                .path("/")
        //                .expiresOn(tomorrow)
        //                .isSecure(true)
        //                .build();
        //        driver.manage().addCookie(cookie);
    }

    private ProfilePage login() {
        ECredential credentials = CONFIG.getCredentials();

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.navigateToLoginPage();

        return loginPage.login(credentials.getEmail(), credentials.getPassword());
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("configuration.Configuration#staticPages")
    public void testUsedPagesLoad(EStaticPage page) {
        StaticPage staticPage = STATIC_PAGE_FACTORY.create(driver, page.getUrl());

        assertEquals(page.getTitle(), staticPage.getTitle());

        String bodyText = staticPage.getBody();
        page.getKeywords().forEach(keyword -> assertTrue(bodyText.contains(keyword)));
    }

    @Disabled
    @Test
    public void testLoginAndLogoutFlow() {
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = login();
        profilePage.logout();

        assertTrue(loginPage.isLoggedOut());
    }

    @Disabled
    @Test
    public void testLoginAndSearchFlow() {
        ProfilePage profilePage = login();

        String firstPizzaName = profilePage.search("pizza").getPizzaTitle();

        assertEquals("Ultimate négysajtos pizza", firstPizzaName);
    }

    @Test
    public void testChangeProfileDetailsWithRandomData() {
        Faker faker = new Faker();

        String newUsername = faker.funnyName().name();
        String newFirstName = faker.name().firstName();
        String newLastName = faker.name().lastName();

        login();
        ProfileDetailPage profileDetailPage = login().navigateToProfileDetailPage();

        profileDetailPage.updateProfile(newUsername, newLastName, newFirstName);

        assertAll(
                () -> assertEquals("Profiladatok sikeresen frissítve.", profileDetailPage.getUpdateDetailsMessage()),
                () -> assertEquals(newUsername, profileDetailPage.getUsername()),
                () -> assertEquals(newFirstName, profileDetailPage.getFirstName()),
                () -> assertEquals(newLastName, profileDetailPage.getLastName())
        );
    }

    @Test
    public void testAddToShoppingList() {
        Faker faker = new Faker();

        MainPage mainPage = new MainPage(driver);
        login();

        mainPage.load();
        System.out.println("Main page loaded");
        ShoppingCartPage shoppingCartPage = mainPage.openShoppingCartPage();
        System.out.println("Shopping cart page loaded");

        int numberOfSavedCartsBefore = shoppingCartPage.getNumberOfSavedCarts();
        System.out.println("Number of saved carts: " + numberOfSavedCartsBefore);

        driver.navigate().back();

        SearchResultPage soupResult = mainPage.search("minestrone");
        System.out.println("Search result page loaded");

        assertTrue(soupResult.isThereResult());

        System.out.println("Adding random result to shopping list");
        boolean isNewListAdded = soupResult.addRandomResultToShoppingList(faker.book().title());
        // boolean isNewListAdded = soupResult.addRandomResultToShoppingList("Now Sleeps the Crimson Petal");

        shoppingCartPage = soupResult.openShoppingCartPage();
        int newNumberOfSavedCarts = shoppingCartPage.getNumberOfSavedCarts();

        if (isNewListAdded) {
            assertEquals(numberOfSavedCartsBefore + 1, newNumberOfSavedCarts);
        } else {
            assertEquals(numberOfSavedCartsBefore, newNumberOfSavedCarts);
        }
    }

    @Test
    public void testProfilePictureUpload() throws URISyntaxException {
        ProfilePage profilePage = login();

        ProfileDetailPage profileDetailPage = profilePage.navigateToProfileDetailPage();

        profileDetailPage.removeProfilePicture();

        String imgPath = Objects.requireNonNull(getClass().getResource("images/avatar.png")).toURI().getPath();

        profileDetailPage.uploadProfilePicture(imgPath);

        assertEquals("Profiladatok sikeresen frissítve.", profileDetailPage.getUpdateDetailsMessage());
    }

    @AfterAll
    public static void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
