package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static utils.JavaScriptUtils.scrollToElement;

@Log4j2
public class AdvancedVacancyPage extends BasePage {

    @FindBy(xpath = "//*[@data-qa='vacancysearch__keywords-input']")
    WebElement inputSearch;
    @FindBy(xpath = "//*[@class='search-form']//*[@class='bloko-form-spacer']")
    WebElement buttonSearchRegion;
    @FindBy(xpath = "//*[contains(@data-qa, 'bloko-tag__cross')]")
    List<WebElement> selectedRegions;
    @FindBy(xpath = "//*[@data-attach='modal-container']")
    WebElement modalRegion;
    @FindBy(xpath = "//*[@data-attach='modal-container']//*[@data-qa='bloko-tree-selector-popup-submit']")
    WebElement buttonSelectRegion;
    @FindBy(xpath = "//*[contains(@data-qa, 'noExperience') and contains(@class, 'bloko-radio')]")
    WebElement radioboxNoExperience;
    @FindBy(xpath = "//*[contains(@data-qa, 'employment-item_full') and contains(@class, 'bloko-checkbox')]")
    WebElement checkboxEmploymentFull;
    @FindBy(xpath = "//*[contains(@data-qa, 'schedule-item_fullDay') and contains(@class, 'bloko-checkbox')]")
    WebElement checkboxDayFull;
    @FindBy(xpath = "//*[contains(@data-qa, 'searchperiod-item_30') and contains(@class, 'bloko-radio')]")
    WebElement radioboxSearchPeriod30;
    @FindBy(xpath = "//*[contains(@data-qa, 'items-on-page_20') and contains(@class, 'bloko-radio')]")
    WebElement radioboxItemPage20;
    @FindBy(xpath = "//*[contains(@data-qa, 'vacancysearch__submit')]")
    WebElement buttonSearchVacancy;
    @FindBy(xpath = "//*[@data-qa = 'signup']")
    WebElement buttonCreateResume;
    @FindBy(xpath = "//*[@data-qa = 'login']")
    WebElement buttonLogin;
    @FindBy(xpath = "//*[@class='supernova-icon-link-switch']")
    WebElement buttonSwitchSearch;
    @FindBy(xpath = "//*[contains(@class, 'supernova-navi-search')]//*[@class='supernova-navi-search-columns']")
    WebElement inputSwitchSearch;

    public static final String BUTTON_SEARCH_COUNTRY = "//*[contains(text(),'%s')]/ancestor::*[contains(@class, 'bloko-tree-selector-content')]//*[contains(@data-qa, 'bloko-tree-selector-toogle-node')]";
    public static final String ELEMENT_SEARCH_REGION = "//*[contains(text(),'%s') and contains(@data-qa,'bloko-tree-selector-item-text')]";

    public AdvancedVacancyPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening Advanced Search Page")
    public void openPage() {
        openPage(String.format("%s%s%s", BASE_HH_URL, SEARCH_VACANCY_URL, ADVANCED_URL));
    }

    @Step("Filling out the advanced search form. Search string - {textSearch}, region to search - {region}")
    public void fullingAdvancedSearch(String textSearch, String country, String region) throws InterruptedException {
        log.info(String.format("Entering the phrase '%s' to search", textSearch));
        try {
            inputSearch.sendKeys(textSearch);
            scrollToElement(driver, buttonSearchRegion);
            for (WebElement item : selectedRegions) {
                item.click();
            }
            buttonSearchRegion.click();
            WebDriverWait wait = new WebDriverWait(driver, 50);
            log.info("Waiting for a window to appear");
            wait.until(ExpectedConditions.visibilityOf(modalRegion));
            log.info(String.format("Country selection '%s'", country));
            driver.findElement(By.xpath(String.format(BUTTON_SEARCH_COUNTRY, country))).click();
            log.info(String.format("Region selection '%s'", region));
            driver.findElement(By.xpath(String.format(ELEMENT_SEARCH_REGION, region))).click();
            buttonSelectRegion.click();
            log.info("Item selection no experience");
            radioboxNoExperience.click();
            scrollToElement(driver, checkboxEmploymentFull);
            log.info("Item selection full time");
            checkboxEmploymentFull.click();
            checkboxDayFull.click();
            scrollToElement(driver, radioboxSearchPeriod30);
            log.info("Item selection per month");
            radioboxSearchPeriod30.click();
            log.info("Item selection 20 jobs per page");
            radioboxItemPage20.click();
            buttonSearchVacancy.click();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
    }

    @Step("Get button name")
    public String getNameButtonCreateResume() {
        log.info("Get button name");
        try {
            return buttonCreateResume.getText();
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return null;
        }
    }

    @Step("check for the presence of the login button")
    public boolean isButtonLoginPresent() {
        log.info("check for the presence of the login button");
        try {
            return isElementPresent(buttonLogin);
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return false;
        }
    }

    @Step("check for the presence of the Create Resume button")
    public boolean isButtonCreateResumePresent() {
        log.info("check for the presence of the Create Resume button");
        try {
            return isElementPresent(buttonCreateResume);
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return false;
        }
    }

    @Step("switch the visibility of the search bar")
    public boolean isSwitchSearch() {
        log.info("switch the visibility of the search bar");
        try {
            boolean isVisible = inputSwitchSearch.isDisplayed();
            buttonSwitchSearch.click();
            return isVisible != inputSwitchSearch.isDisplayed();
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return false;
        }
    }

    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return false;
        }
    }
}
