package tests;

import adapters.*;
import io.qameta.allure.Description;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.Utils.getFormattedUrl;

public class EmployerTest extends BaseTest {

    @Test
    @Description("Search by text number of companies that have no open vacancies")
    public void countEmployersWithoutOpenVacanciesTest() {
        EmployersList list = new EmployerAdapter().getCountEmployersListWithoutOpenVacancies(SEARCH_TEXT_EMPLOYER);
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.searchEmployersWithoutOpenVacancies(SEARCH_TEXT_EMPLOYER);
        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }

    @Test
    @Description("The number of companies after search")
    public void countEmployersAdvancedSearchTest() {
        EmployersList list = new EmployerAdapter().getCountEmployersAdvancedSearch(SEARCH_AREA);
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION, "", true);

        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }

    @Test
    @Description("The number open vacancies with Employer")
    public void openVacanciesTest() {
        EmployersList listAPI = new EmployerAdapter().getCountOpenVacancies(SEARCH_TEXT_EMPLOYER, SEARCH_AREA, SEARCH_WITH_VACANCIES);
        int countEmployersAPI = listAPI.getFound();

        employerPage.openPage();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION, SEARCH_TEXT_EMPLOYER, false);
        EmployersList listUI = employerPage.getListEmployers();
        int countEmployersUI = listUI.getFound();

        Assert.assertEquals(countEmployersAPI, countEmployersUI);
        Assert.assertTrue(employerPage.comparisonOfNameAndQuantity(listAPI, listUI));
    }

    @Test
    @Description("Checking Employer")
    public void employerTest() {
        employerPage.openPage();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION, SEARCH_TEXT_EMPLOYER, false);
        String id = employerPage.getIdEmployer(0);

        Employer listAPI = new EmployerAdapter().getEmployer(id);
        Employer listUI = employerPage.fullingEmployer();

        Assert.assertEquals(listAPI.getName(), listUI.getName());
        Assert.assertEquals(listAPI.getArea().getName(), listUI.getArea().getName());
        Assert.assertEquals(listAPI.getOpenVacancies(), listUI.getOpenVacancies());
        Assert.assertEquals(getFormattedUrl(listAPI.getSiteUrl()), getFormattedUrl(listUI.getSiteUrl()));
    }

    @Test
    @Description("checking language switching on the site")
    public void switchingLanguageTest() {
        employerPage.openPage();
        String language = employerPage.isSwitchLanguage();
        String languageButtonLogin = LANGUAGE_RU == language ? BUTTON_LOGIN_RU : BUTTON_LOGIN_EN;
        Assert.assertEquals(languageButtonLogin, employerPage.getNameButtonLogin());
    }
}
