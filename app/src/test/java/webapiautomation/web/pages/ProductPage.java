package webapiautomation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    private By addToCartButton = By.cssSelector("a.btn.btn-success.btn-lg");
    private By cartLink = By.id("cartur");

    public ProductPage(WebDriver driver) {
        super(driver);
    }
    public void addToCart () {
        driver.findElement(addToCartButton) .click();
    }
    public void goToVart () {
        driver.findElement(cartLink) .click();
    }
}
