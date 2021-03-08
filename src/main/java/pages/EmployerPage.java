package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployerPage extends BasePage{

    @FindBy(xpath = "//*[contains(@class, 'b-alfabeta-totals')]//strong")
    WebElement countEmployers;
    @FindBy(xpath = "//*[@class='bloko-form-spacer']//*[contains(@class, 'bloko-link-switch')]")
    WebElement buttonSearchRegion;
    @FindBy(xpath = "//*[@class='bloko-modal']//*[contains(@class, 'bloko-button_primary')]")
    WebElement buttonSelectRegion;
    @FindBy(xpath = "//*[@class='bloko-checkbox']")
    WebElement chackboxWithCloseVacancies;
    @FindBy(xpath = "//*[@class= 'search-form']//*[contains(@class,'bloko-button')]")
    WebElement buttonSearchEmployers;
    @FindBy(xpath = "//*[@data-attach='modal-container']")
    WebElement modalRegion;

    public static final String BUTTON_SEARCH_COUNTRY = "//*[contains(text(),'%s')]/ancestor::*[contains(@class, 'bloko-tree-selector-content')]//*[contains(@data-qa, 'bloko-tree-selector-toogle-node')]";
    public static final String ELEMENT_SEARCH_REGION = "//*[contains(text(),'%s') and contains(@data-qa,'bloko-tree-selector-item-text')]";



    public EmployerPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        openPage(String.format("%s%s",BASE_HH_URL, EMPLOYERS_URL));
    }

    public int getCountEmployers() {
        String count = countEmployers.getText().replaceAll("\\s+", "");
        return Integer.parseInt(count);
    }

    public void advancedSearchEmployers(String country, String region){
        buttonSearchRegion.click();
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOf(modalRegion));
        driver.findElement(By.xpath(String.format(BUTTON_SEARCH_COUNTRY, country))).click();
        driver.findElement(By.xpath(String.format(ELEMENT_SEARCH_REGION, region))).click();
        buttonSelectRegion.click();
        chackboxWithCloseVacancies.click();
        buttonSearchEmployers.click();
    }
}
