package webapiautomation.web.steps;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebStepDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("user is on Demoblaze home page")
    public void user_is_on_demoblaze_home_page() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless=new",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu",
            "--window-size=1920,1080"
    );

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.demoblaze.com");
    }

    // === Scenario: Home page is accessible ===
    @Then("home page title should be {string}")
    public void home_page_title_should_be(String title) {
        assertThat(driver.getTitle()).contains(title);
        driver.quit();
        driver = null;
    }

    // === Scenario: Add one product to cart ===
    @When("user opens first product detail")
    public void user_opens_first_product_detail() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones")));
        driver.findElement(By.linkText("Phones")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid .card-title a")));
        driver.findElement(By.cssSelector("#tbodyid .card-title a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".name")));
    }

    @When("user adds the product to cart")
    public void user_adds_the_product_to_cart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart")));
        driver.findElement(By.linkText("Add to cart")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @When("user navigates to cart page")
    public void user_navigates_to_cart_page() {
        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
    }

    @Then("cart should contain at least one product")
    public void cart_should_contain_at_least_one_product() {
        wait.until(d -> driver.findElements(By.cssSelector("#tbodyid tr")).size() >= 1);
        int rows = driver.findElements(By.cssSelector("#tbodyid tr")).size();
        assertThat(rows).as("Cart should contain products").isGreaterThan(0);

        driver.quit();
        driver = null;
    }

    // === Negative Scenario: Cart empty when no product added ===
    @When("user navigates directly to cart page")
    public void user_navigates_directly_to_cart_page() {
        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
    }

    @Then("cart should be empty")
    public void cart_should_be_empty() {
        int rows = driver.findElements(By.cssSelector("#tbodyid tr")).size();
        assertThat(rows).as("Cart should be empty").isEqualTo(0);

        driver.quit();
        driver = null;
    }

    // === E2E: Place order (checkout) ===
    @When("user places an order with valid data")
    public void user_places_an_order_with_valid_data() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-target='#orderModal']")));
        driver.findElement(By.cssSelector("button[data-target='#orderModal']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).sendKeys("Auto User");
        driver.findElement(By.id("country")).sendKeys("Indonesia");
        driver.findElement(By.id("city")).sendKeys("Bekasi");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2026");

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
    }

    @Then("order success popup should be displayed")
    public void order_success_popup_should_be_displayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".sweet-alert.showSweetAlert")
        ));
        String text = driver.findElement(
                By.cssSelector(".sweet-alert.showSweetAlert h2")
        ).getText();
        assertThat(text).contains("Thank you");

        driver.findElement(By.xpath("//button[text()='OK']")).click();
        driver.quit();
        driver = null;
    }
}
