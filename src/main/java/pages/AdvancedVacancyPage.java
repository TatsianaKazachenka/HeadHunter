package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdvancedVacancyPage extends BasePage{

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
    WebElement radioboxSearchperiod30;
    @FindBy(xpath = "//*[contains(@data-qa, 'items-on-page_20') and contains(@class, 'bloko-radio')]")
    WebElement radioboxItemPage20;
    @FindBy(xpath = "//*[contains(@data-qa, 'vacancysearch__submit')]")
    WebElement buttonSearchVacancy;

    public static final String BUTTON_SEARCH_COUNTRY = "//*[contains(text(),'%s')]/ancestor::*[contains(@class, 'bloko-tree-selector-content')]//*[contains(@data-qa, 'bloko-tree-selector-toogle-node')]";
    public static final String ELEMENT_SEARCH_REGION = "//*[contains(text(),'%s') and contains(@data-qa,'bloko-tree-selector-item-text')]";

    public AdvancedVacancyPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        openPage(String.format("%s%s", BASE_HH_URL, SEARCH_VACANCY_URL+ ADVANCED_URL));
    }

    public void fullingAdvancedSearch(String textSearch,String country, String region) throws InterruptedException {
        inputSearch.sendKeys(textSearch);
        scrolling(buttonSearchRegion);
        for(WebElement item : selectedRegions){
            item.click();
        }
        buttonSearchRegion.click();
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOf(modalRegion));
        driver.findElement(By.xpath(String.format(BUTTON_SEARCH_COUNTRY, country))).click();
        driver.findElement(By.xpath(String.format(ELEMENT_SEARCH_REGION, region))).click();
        buttonSelectRegion.click();
        radioboxNoExperience.click();
        scrolling(checkboxEmploymentFull);
        checkboxEmploymentFull.click();
        checkboxDayFull.click();
        scrolling(radioboxSearchperiod30);
        radioboxSearchperiod30.click();
        radioboxItemPage20.click();
        buttonSearchVacancy.click();
    }

    public void scrolling(WebElement element) throws InterruptedException{
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }
}
