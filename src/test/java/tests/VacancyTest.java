package tests;

import adapters.VacancyAdapter;
import io.qameta.allure.Description;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VacancyTest extends BaseTest {

    @Test(groups = { "search" })
    @Description("check the number of vacancies found")
    public void countVacanciesFoundTest() {
        VacanciesList list = new VacancyAdapter().getVacanciesListForTextSearch(SEARCH_TEXT);
        int countVacanciesAPI = list.getFound();

        vacancyPage.openPage();
        vacancyPage.search(SEARCH_TEXT);
        int countVacanciesUI = vacancyPage.getCountFoundVacancy(SEARCH_TEXT);
        Assert.assertEquals(countVacanciesAPI, countVacanciesUI);
    }

    @Test(groups = { "search" })
    @Description("check advanced search")
    public void advancedVacancyTest() throws InterruptedException {
        VacanciesList listAPI = new VacancyAdapter().getAdvancedVacanciesListSearch(SEARCH_TEXT, SEARCH_AREA, SEARCH_CURRENCY_CODE_RUR,
                SEARCH_EXPERIENCE, SEARCH_EMPLOYMENT, SEARCH_SCHEDULE, SEARCH_ORDER_BY, SEARCH_PERIOD, SEARCH_PAGE);

        advancedVacancyPage.openPage();
        advancedVacancyPage.fullingAdvancedSearch(SEARCH_TEXT, SEARCH_COUNTRY, SEARCH_REGION);
        VacanciesList listUI = vacancyPage.fullingVacancyList();

        Assert.assertEquals(listAPI.getFound(), listUI.getFound());
        Assert.assertTrue(vacancyPage.comparisonOfNameVacancyAndNameEmployer(listAPI, listUI));
    }

    @Test
    @Description("checking one vacancy")
    public void vacancyTest() throws InterruptedException{
        vacancyPage.openPage();
        vacancyPage.search(SEARCH_TEXT);
      
        String id = vacancyPage.getVacancyIdByIndex(2);

        Vacancy listAPI = new VacancyAdapter().getVacancy(id);
        Vacancy listUI = vacancyPage.fullingVacancy();

        Assert.assertEquals(listAPI.getName(), listUI.getName());
        Assert.assertEquals(listAPI.getEmployer().getName(), listUI.getEmployer().getName());
        Assert.assertEquals(listAPI.getArea().getName(), listUI.getArea().getName());
        Assert.assertEquals(listAPI.getSkills().size(), listUI.getSkills().size());

        boolean isSkills = false;

        for (int i = 0; i < listAPI.getSkills().size(); i++) {
            String nameAPI = listAPI.getSkills().get(i).getName();
            for (int j = 0; j < listUI.getSkills().size(); j++) {
                String nameUI = listUI.getSkills().get(j).getName();
                if (nameAPI.equals(nameUI)) {
                    isSkills = true;
                }
            }
        }
        Assert.assertTrue(isSkills);
    }

    @Test(groups = { "UI Test" })
    @Description("check button title create resume")
    public void buttonTitleCreateResumeTest() {
        advancedVacancyPage.openPage();
        Assert.assertEquals(advancedVacancyPage.getCreateResumeButtonName(), BUTTON_CREATE_RESUME);
    }

    @Test(groups = { "UI Test" })
    @Description("check for the presence of login buttons and create a resume")
    public void buttonsLoginAndCreateResumePresentTest() {
        advancedVacancyPage.openPage();
        Assert.assertTrue(advancedVacancyPage.isButtonLoginPresent());
        Assert.assertTrue(advancedVacancyPage.isButtonCreateResumePresent());
    }

    @Test(groups = { "UI Test" })
    @Description("checking toggle the visibility of the search bar")
    public void isSwitchSearchTest() {
        advancedVacancyPage.openPage();
        Assert.assertTrue(advancedVacancyPage.isSwitchSearchDisplayed());
    }
}
