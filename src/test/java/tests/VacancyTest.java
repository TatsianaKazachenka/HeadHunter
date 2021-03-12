package tests;

import adapters.VacancyAdapter;
import io.qameta.allure.Description;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VacancyTest extends BaseTest {

    @Test
    @Description("check the number of vacancies found")
    public void countVacanciesFoundTest() {
        VacanciesList list = new VacancyAdapter().getVacanciesListForTextSearch(SEARCH_TEXT);
        int countVacanciesAPI = list.getFound();

        vacancyPage.openPage();
        vacancyPage.search(SEARCH_TEXT);
        int countVacanciesUI = vacancyPage.getCountFoundVacancy(SEARCH_TEXT);
        Assert.assertEquals(countVacanciesAPI, countVacanciesUI);
    }

    @Test
    @Description("check advanced search")
    public void advancedVacancyTest() throws InterruptedException {
        VacanciesList listAPI = new VacancyAdapter().getAdvancedVacanciesListSearch(SEARCH_TEXT, SEARCH_AREA, SEARCH_CURRENCY_CODE_RUR,
                SEARCH_EXPERIENCE, SEARCH_EMPLOYMENT, SEARCH_SCHEDULE, SEARCH_ORDER_BY, SEARCH_PERIOD, SEARCH_PAGE);

        advancedVacancyPage.openPage();
        advancedVacancyPage.fullingAdvancedSearch(SEARCH_TEXT, SEARCH_COUNTRY, SEARCH_REGION);
        VacanciesList listUI = vacancyPage.fullingVacancyList();

        Assert.assertEquals(listAPI.getFound(), listUI.getFound());
        for (int i = 0; i < listAPI.getFound(); i++) {
            String nameAPI = listAPI.getItems().get(i).getName();
            String employerAPI = listAPI.getItems().get(i).getEmployer().getName();
            for (int j = 0; j < listUI.getFound(); j++) {
                String nameUI = listUI.getItems().get(j).getName();
                String employerUI = listAPI.getItems().get(j).getEmployer().getName();
                if (nameAPI.equals(nameUI)) {
                    Assert.assertEquals(employerAPI, employerUI);
                }
            }
        }
    }

    @Test
    @Description("checking one vacancy")
    public void vacancyTest() {
        vacancyPage.openPage();
        vacancyPage.search(SEARCH_TEXT);
        String id = vacancyPage.getIdVacancy();

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
}
