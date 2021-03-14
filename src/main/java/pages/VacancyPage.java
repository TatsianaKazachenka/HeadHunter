package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import objects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.AllureUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.JavaScriptUtils.scrollToElement;

@Log4j2
public class VacancyPage extends BasePage {

    @FindBy(xpath = "//*[@data-qa='search-input']")
    WebElement inputSearch;
    @FindBy(xpath = "//*[@data-qa='search-button']")
    WebElement buttonSearch;
    @FindBy(xpath = "//*[@data-qa='bloko-header-1']")
    WebElement headerTextVacancy;
    @FindBy(xpath = "//*[@data-qa='vacancy-serp__vacancy']")
    List<WebElement> blockVacancy;
    @FindBy(xpath = "//*[@data-qa='vacancy-title']")
    WebElement titleVacancy;
    @FindBy(xpath = "//*[@data-qa='vacancy-view-location']")
    WebElement regionVacancy;
    @FindBy(xpath = "//*[contains(@data-qa, 'skills-element')]")
    List<WebElement> skillsForVacancy;
    @FindBy(xpath = "//*[@data-qa = 'vacancy-company-name']")
    WebElement employerName;

    public static final By VACANCY_TITLE = By.xpath("//*[contains(@data-qa,'vacancy-title')]");
    public static final By VACANCY_EMPLOYER = By.xpath("//*[contains(@data-qa,'vacancy-employer')]");

    public VacancyPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening Vacancies list")
    public void openPage() {
        openPage(String.format("%s%s", BASE_HH_URL, SEARCH_VACANCY_URL));
    }

    @Step("String search")
    public void search(String textSearch) {
        log.info(String.format("Entering the phrase '%s' to search", textSearch));
        try {
            inputSearch.sendKeys(textSearch);
            buttonSearch.click();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
    }

    @Step("Getting the number of vacancies after searching")
    public int getCountFoundVacancy(String search) {
        log.info("Getting the number of vacancies");
        String headerText = headerTextVacancy.getText();
        headerText = headerText.replaceAll(HEADER, "")
                .replaceAll("\\s+", "");
        return Integer.parseInt(headerText);
    }

    @Step("Filling in the list of vacancies after searching")
    public VacanciesList fullingVacancyList() {
        log.info("Filling out the list of vacancies");
        VacanciesList list = new VacanciesList();
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        try {
            List<WebElement> elements = blockVacancy;
            for (WebElement element : elements) {
                Employer employer = new Employer();
                Vacancy vacancy = new Vacancy();
                employer.setName(element.findElement(VACANCY_EMPLOYER).getText()
                        .replace(COMPANY_OOO, "")
                        .replace(COMPANY_OAO, "")
                        .replace(COMPANY_YP, ""));
                vacancy.setEmployer(employer);
                vacancy.setName(element.findElement(VACANCY_TITLE).getText());
                vacancies.add(vacancy);
            }
            list.setItems(vacancies);
            list.setFound(vacancies.size());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
        return list;
    }

    @Step("Receiving vacancy id and transition")
    public String getIdVacancy(int index) throws InterruptedException {
        try {
            WebElement selectVacancyByIndex = blockVacancy.get(index);
            scrollToElement(driver, selectVacancyByIndex);
            WebElement selectVacancy = selectVacancyByIndex.findElement(VACANCY_TITLE);
            String urlSelectVacancy = selectVacancy.getAttribute("href");
            String[] params = urlSelectVacancy.substring(0, urlSelectVacancy.indexOf("?")).split("/");
            String id = params[params.length - 1];
            log.info(String.format("Vacancy selection by id = '%s'", id));
            openPage(String.format("%s%s%s", BASE_HH_URL, VACANCY_URL, id));
            return id;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
            return null;
        }
    }

    @Step("Getting a description of the vacancy by id")
    public Vacancy fullingVacancy() {
        log.info("Filling job parameters");
        Vacancy vacancy = new Vacancy();
        Employer employer = new Employer();
        Regions regions = new Regions();
        try {
            ArrayList<VacancySkill> skills = new ArrayList<>();
            String title = titleVacancy.getText();
            log.info(String.format("Vacancy Name '%s'", title));
            vacancy.setName(title);
            String regionVacancyName = titleVacancy.getText();
            log.info(String.format("Region Vacancy Name '%s'", regionVacancyName));
            regions.setName(regionVacancyName);
            vacancy.setArea(regions);
            for (WebElement element : skillsForVacancy) {
                VacancySkill skill = new VacancySkill();
                String skillName = element.getText();
                log.info(String.format("Skill Name '%s'", skillName));
                skill.setName(skillName);
                skills.add(skill);
            }
            vacancy.setSkills(skills);
            String nameEmployer = employerName.getText()
                    .replace(COMPANY_OOO, "")
                    .replace(COMPANY_OAO, "")
                    .replace(COMPANY_YP, "");
            log.info(String.format("Employer Name '%s'", nameEmployer));
            employer.setName(nameEmployer);
            vacancy.setEmployer(employer);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            AllureUtils.takeScreenshot(driver);
        }
        return vacancy;
    }

    @Step("Comparison value of a pair of values name vacancy and name employer")
    public boolean comparisonOfNameVacancyAndNameEmployer(VacanciesList listAPI, VacanciesList listUI) {
        boolean isEquals = false;
        log.info("Comparison value of a pair of values name vacancy and name employer");
        for (int i = 0; i < listAPI.getFound(); i++) {
            String nameAPI = listAPI.getItems().get(i).getName();
            log.info(String.format("Name API '%s'", nameAPI));
            String employerAPI = listAPI.getItems().get(i).getEmployer().getName();
            log.info(String.format("Name employer API '%s'", employerAPI));
            for (int j = 0; j < listUI.getFound(); j++) {
                String nameUI = listUI.getItems().get(j).getName();
                log.info(String.format("Name UI '%s'", nameUI));
                String employerUI = listAPI.getItems().get(j).getEmployer().getName();
                log.info(String.format("Name employer UI '%s'", employerUI));
                if (nameAPI.equals(nameUI)) {
                    isEquals = employerAPI.equals(employerUI);
                    if (!isEquals) {
                        log.info(String.format("mismatch between the values of a pair of parameters and the name UI - '%s', API - '%s' and the employer name UI - '%s', API - '%s'", nameUI, nameAPI, employerUI, employerAPI));
                        break;
                    }
                }
            }
        }
        return isEquals;
    }
}
