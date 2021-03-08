package pages;

import objects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

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

    public void openPage() {
        openPage(String.format("%s%s",BASE_HH_URL, SEARCH_VACANCY_URL));
    }

    public void search(String search) {
        inputSearch.sendKeys(search);
        buttonSearch.click();
    }

    public int getCountFoundVacancy(String search) {
        String headerText = headerTextVacancy.getText();
        headerText = headerText.replace(String.format(HEADER, search), "")
                .replaceAll("\\s+", "");
        return Integer.parseInt(headerText);
    }

    public VacanciesList fullingVacancyList() {
        VacanciesList list = new VacanciesList();
        ArrayList<Vacancy> vacancies = new ArrayList<Vacancy>();
        List<WebElement> elements = blockVacancy;

        for (WebElement element : elements) {
            Employer employer = new Employer();
            Vacancy vacancy = new Vacancy();
            employer.setName(element.findElement(VACANCY_EMPLOYER).getText()
                    .replace("ООО ", "")
                    .replace("ОАО ", "")
                    .replace("УП ", ""));
            vacancy.setEmployer(employer);
            vacancy.setName(element.findElement(VACANCY_TITLE).getText());
            vacancies.add(vacancy);
        }
        list.setItems(vacancies);
        list.setFound(vacancies.size());
        return list;
    }

    public String getIdVacancy() {
        WebElement selectVacancy = blockVacancy.get(2).findElement(VACANCY_TITLE);
        String urlSelectVacancy = selectVacancy.getAttribute("href");
        String[] params = urlSelectVacancy.substring(0, urlSelectVacancy.indexOf("?")).split("/");
        String id = params[params.length - 1];
        openPage(String.format("%s%s%s",BASE_HH_URL,VACANCY_URL, id));
        return id;
    }

    public Vacancy fullingVacancy(){
        Vacancy vacancy = new Vacancy();
        Employer employer = new Employer();
        Regions regions = new Regions();
        ArrayList<VacancySkill> skills = new ArrayList<VacancySkill>();
        vacancy.setName(titleVacancy.getText());
        regions.setName(regionVacancy.getText());
        vacancy.setArea(regions);
        for (WebElement element : skillsForVacancy) {
            VacancySkill skill = new VacancySkill();
            skill.setName(element.getText());
            skills.add(skill);
        }
        vacancy.setSkills(skills);
        employer.setName(employerName.getText());
        vacancy.setEmployer(employer);
        return vacancy;
    }
}
