package pages;

import constants.IPageConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements IPageConstants {
    WebDriver driver;
    WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = 20;
        wait = new WebDriverWait(driver, timeout);
    }

    public void openPage(String url) {
        driver.get(url);
    }
}
