package pages;

import constants.ICommonConstants;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureUtils;

@Log4j2
public class BasePage implements ICommonConstants {
    WebDriver driver;
    WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = 20;
        wait = new WebDriverWait(driver, timeout);
        log.debug("Implicit timeout = " + timeout);
        PageFactory.initElements(driver, this);
    }

    public void openPage(String url) {
        log.info("Open URL = " + url);
        driver.get(url);
    }
}
