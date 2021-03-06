package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

import constants.ITestConstants;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.*;

import java.util.concurrent.TimeUnit;

public class BaseTest implements ITestConstants {
    WebDriver driver;

    VacancyPage vacancyPage;
    AdvancedVacancyPage advancedVacancyPage;
    EmployerPage employerPage;

    @BeforeMethod(groups = { "init" } )
    public void initTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-incognito");
        //options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        initPages();
    }
/*
    @AfterMethod(alwaysRun = true)
    public void endTest() {
        driver.quit();
    }
*/
    private void initPages() {
        vacancyPage = new VacancyPage(driver);
        advancedVacancyPage = new AdvancedVacancyPage(driver);
        employerPage = new EmployerPage(driver);
    }
}
