package tests;

import adapters.*;
import io.qameta.allure.Description;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployerTest extends BaseTest{

    @Test
    @Description("The number of companies with open vacancies")
    public void countEmployersWithOpenVacanciesTest() {
        EmployersList list = new EmployerAdapter().getCountEmployersListWithOpenVacancies();
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.getCountEmployers();
        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }

    @Test
    public void countEmployersAdvancedSearchTest() {
        EmployersList list = new EmployerAdapter().getCountEmployersAdvancedSearch();
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.getCountEmployers();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION);
        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }
}
