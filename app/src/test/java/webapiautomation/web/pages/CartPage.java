package webapiautomation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By cartTableRows = By.cssSelector("#tbodyid tr");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasAnyProduct() {
        return driver.findElements(cartTableRows).size() > 0;
    }
}
