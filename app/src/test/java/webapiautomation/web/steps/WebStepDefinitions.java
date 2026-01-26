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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("#tbodyid .card-title a"))
        );
        firstProduct.click();
    }

    @And("user adds the product to cart")
    public void user_adds_the_product_to_cart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-success"))
        );
        addButton.click();
        // Optional: handle alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @And("user navigates to cart page")
    public void user_navigates_to_cart_page() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("cartur"))
        );
        cartLink.click();
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

    // Tambahkan steps lain sesuai feature
}