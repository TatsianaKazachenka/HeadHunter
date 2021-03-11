package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import objects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class EmployerPage extends BasePage {

    @FindBy(xpath = "//*[contains(@class, 'b-alfabeta-totals')]//strong")
    WebElement countEmployers;
    @FindBy(xpath = "//*[@class='bloko-form-spacer']//*[contains(@class, 'bloko-link-switch')]")
    WebElement buttonSearchRegion;
    @FindBy(xpath = "//*[@class='bloko-modal']//*[contains(@class, 'bloko-button_primary')]")
    WebElement buttonSelectRegion;
    @FindBy(xpath = "//*[@class='bloko-checkbox']")
    WebElement checkboxWithCloseVacancies;
    @FindBy(xpath = "//*[@class= 'search-form']//*[contains(@class,'bloko-button')]")
    WebElement buttonSearchEmployers;
    @FindBy(xpath = "//*[@data-attach='modal-container']")
    WebElement modalRegion;
    @FindBy(xpath = "//*[@class='search-form']//*[@class='bloko-input']")
    WebElement inputSearch;
    @FindBy(xpath = "//*[contains(@class,'b-companylist')]//div")
    List<WebElement> blockEmployers;
    @FindBy(xpath = "//*[@class = 'company-header']//*[@data-qa='company-header-title-name']")
    WebElement titleEmployer;
    @FindBy(xpath = "//*[@data-qa='sidebar-text-color']//*[@class='employer-sidebar-block']")
    WebElement regionEmployer;
    @FindBy(xpath = "//*[@data-qa='sidebar-text-color']//*[contains(@data-qa, 'employer-vacancies-link')]")
    WebElement countOpenVacancies;
    @FindBy(xpath = "//*[@data-qa='sidebar-text-color']//*[@data-qa='sidebar-company-site']")
    WebElement siteEmployer;
    @FindBy(xpath = "//*[@data-qa = 'change-locale-EN']")
    WebElement hrefSwitchLanguageToEn;
    @FindBy(xpath = "//*[@data-qa = 'change-locale-RU']")
    WebElement hrefSwitchLanguageToRu;
    @FindBy(xpath = "//*[@data-qa = 'login']")
    WebElement buttonLogin;

    public static final String BUTTON_SEARCH_COUNTRY = "//*[contains(text(),'%s')]/ancestor::*[contains(@class, 'bloko-tree-selector-content')]//*[contains(@data-qa, 'bloko-tree-selector-toogle-node')]";
    public static final String ELEMENT_SEARCH_REGION = "//*[contains(text(),'%s') and contains(@data-qa,'bloko-tree-selector-item-text')]";
    public static final String EMPLOYER_NAME = "a";
    public static final String COUNT_VACANCIES = "em";

    public EmployerPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening Employer list")
    public void openPage() {
        openPage(String.format("%s%s", BASE_HH_URL, EMPLOYERS_URL));
    }

    @Step("Getting the number of employers after searching")
    public int getCountEmployers() {
        log.info("Getting the number of employers");
        String count = countEmployers.getText().replaceAll("\\s+", "");
        return Integer.parseInt(count);
    }

    @Step("Search for companies with vacancies")
    public void searchEmployersWithoutOpenVacancies(String text) {
        try {
            inputSearch.sendKeys(text);
            checkboxWithCloseVacancies.click();
            buttonSearchEmployers.click();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
    }

    @Step("Filling out the advanced search form. Search string - {text}, region to search - {region}")
    public void advancedSearchEmployers(String country, String region, String text, boolean isClickToCheckbox) {
        log.info(String.format("Entering the phrase '%s' to search", text));
        try {
            inputSearch.sendKeys(text);
            buttonSearchRegion.click();
            WebDriverWait wait = new WebDriverWait(driver, 50);
            log.info("Waiting for a window to appear");
            wait.until(ExpectedConditions.visibilityOf(modalRegion));
            log.info(String.format("Country selection '%s'", country));
            driver.findElement(By.xpath(String.format(BUTTON_SEARCH_COUNTRY, country))).click();
            log.info(String.format("Region selection '%s'", region));
            driver.findElement(By.xpath(String.format(ELEMENT_SEARCH_REGION, region))).click();
            buttonSelectRegion.click();
            if (isClickToCheckbox) {
                log.info("Choose show companies that have no open vacancies");
                checkboxWithCloseVacancies.click();
            }
            buttonSearchEmployers.click();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
    }

    @Step("Filling in the list of companies after searching")
    public EmployersList getListEmployers() {
        EmployersList employersList = new EmployersList();
        ArrayList<Employer> employers = new ArrayList<Employer>();
        try {
            for (WebElement element : blockEmployers) {
                Employer employer = new Employer();
                String name = element.findElement(By.tagName(EMPLOYER_NAME)).getText();
                String countVacancies = element.findElement(By.tagName(COUNT_VACANCIES)).getText();
                log.info(String.format("Adding a company '%s'", name));
                employer.setName(name);
                log.info(String.format("Adding '%s' active vacancies", countVacancies));
                employer.setOpenVacancies(countVacancies);
                employers.add(employer);
            }
            employersList.setItems(employers);
            employersList.setFound(getCountEmployers());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
        return employersList;
    }

    @Step("Receiving company id and transition")
    public String getIdEmployer(int index) {
        try {
            WebElement selectEmployer = blockEmployers.get(index).findElement(By.tagName(EMPLOYER_NAME));
            String urlSelectEmployer = selectEmployer.getAttribute("href");
            String[] params = urlSelectEmployer.split("/");
            String id = params[params.length - 1];
            log.info(String.format("Company selection by id = '%s'", id));
            selectEmployer.click();
            return id;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
        return null;
    }

    @Step("Getting a description of the company by id")
    public Employer fullingEmployer() {
        Employer employer = new Employer();
        Regions regions = new Regions();
        try {
            String employerName = titleEmployer.getText()
                    .replace(COMPANY_OOO, "")
                    .replace(COMPANY_OAO, "")
                    .replace(COMPANY_YP, "");
            log.info(String.format("Adding '%s' to array", employerName));
            employer.setName(employerName);
            String regionName = regionEmployer.getText();
            log.info(String.format("Adding '%s' to array", regionName));
            regions.setName(regionEmployer.getText());
            employer.setArea(regions);
            String countVacancies = countOpenVacancies.getText();
            log.info(String.format("Adding '%s' active vacancies", countVacancies));
            countVacancies = countVacancies
                    .replace(EMPLOYERS_ACTIVE_VACANCIES, "")
                    .replace(EMPLOYERS_ACTIVE_VACANCY, "")
                    .replaceAll("\\s+", "");
            employer.setOpenVacancies(countVacancies);
            employer.setSiteUrl(siteEmployer.getText());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
        return employer;
    }

    @Step("switching language")
    public String isSwitchLanguage() {
        log.info("switching language");
        try {
            if (hrefSwitchLanguageToEn.isDisplayed()) {
                hrefSwitchLanguageToEn.click();
                return LANGUAGE_EN;
            } else {
                hrefSwitchLanguageToRu.click();
                return LANGUAGE_RU;
            }
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return null;
        }
    }

    @Step("Get button name")
    public String getNameButtonLogin() {
        log.info("Get button name");
        try {
            return buttonLogin.getText();
        } catch (NoSuchElementException ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return null;
        }
    }
}
