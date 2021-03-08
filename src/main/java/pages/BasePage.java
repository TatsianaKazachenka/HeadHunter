package pages;

import constants.ICommonConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements ICommonConstants {
    WebDriver driver;
    WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = 20;
        wait = new WebDriverWait(driver, timeout);
        PageFactory.initElements(driver, this);
    }

    public void openPage(String url) {
        driver.get(url);
    }
}
