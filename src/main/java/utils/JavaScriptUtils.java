package utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Log4j2
public class JavaScriptUtils {

    private JavaScriptUtils() {
    }

    public static void scrollToElement(WebDriver driver, WebElement element) throws InterruptedException {
        log.info("Scrolling by element");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        //waits 5 milliseconds after scrolling
        Thread.sleep(500);
    }
}
