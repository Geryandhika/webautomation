package webapiautomation.web.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage {
    private WebDriver driver;
    private By firstProductLink = By.cssSelector("#tbodyid .card-title a");
    private By cartLink = By.id("cartur");
    private By loginLink = By.id("login2");

    public void open() {
        driver.get("https://www.demoblaze.com/");
    }

    public void openFirstProduct() {
        driver.findElement(firstProductLink).click();
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }

    public void openLoginModal() {
        driver.findElement(loginLink).click();
    }
}
