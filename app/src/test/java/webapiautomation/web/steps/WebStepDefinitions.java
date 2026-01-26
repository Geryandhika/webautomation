package webapiautomation.web.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebStepDefinitions {

    private static WebDriver driver;

    // =======================
    // STEP DEFINITIONS
    // =======================

    @Given("user is on Demoblaze home page")
    public void user_is_on_demoblaze_home_page() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new"); // lebih stabil di CI
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }
        driver.get("https://www.demoblaze.com/");
    }

    @When("user opens first product detail")
    public void user_opens_first_product_detail() {
        safeClick(By.cssSelector("#tbodyid .card-title a"));
    }

    @And("user adds the product to cart")
    public void user_adds_the_product_to_cart() {
        safeClick(By.cssSelector(".btn-success"));
        // Optional: handle alert
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @And("user navigates to cart page")
    public void user_navigates_to_cart_page() {
        safeClick(By.id("cartur"));
    }

    @Then("cart should contain at least one product")
    public void cart_should_contain_at_least_one_product() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid"))
        );
        if (table.findElements(By.cssSelector("tr")).isEmpty()) {
            throw new AssertionError("Cart is empty!");
        }
    }

    @And("user places an order with valid data")
    public void user_places_an_order_with_valid_data() {
        safeClick(By.cssSelector(".btn-success")); // tombol Place Order

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        WebElement countryInput = driver.findElement(By.id("country"));
        WebElement cityInput = driver.findElement(By.id("city"));
        WebElement cardInput = driver.findElement(By.id("card"));
        WebElement monthInput = driver.findElement(By.id("month"));
        WebElement yearInput = driver.findElement(By.id("year"));

        nameInput.sendKeys("Test User");
        countryInput.sendKeys("Test Country");
        cityInput.sendKeys("Test City");
        cardInput.sendKeys("1234567890123456");
        monthInput.sendKeys("01");
        yearInput.sendKeys("2030");

        safeClick(By.cssSelector("#orderModal .btn-primary")); // tombol Purchase
    }

    @Then("order success popup should be displayed")
    public void order_success_popup_should_be_displayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert"))
        );
        if (!confirmation.isDisplayed()) {
            throw new AssertionError("Order success popup not displayed!");
        }
    }

    // =======================
    // HELPER METHOD
    // =======================
    public void safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int attempts = 0;
        while(attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }
}
