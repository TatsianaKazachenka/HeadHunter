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
        EmployersList list = new EmployerAdapter().getCountEmployersListWithOpenVacancies(SEARCH_WITH_VACANCIES);
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.getCountEmployers();
        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }

    @Test
    public void countEmployersAdvancedSearchTest() {
        EmployersList list = new EmployerAdapter().getCountEmployersAdvancedSearch(SEARCH_AREA);
        int countEmployersAPI = list.getFound();

        employerPage.openPage();
        employerPage.getCountEmployers();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION, "", true);
        int countEmployersUI = employerPage.getCountEmployers();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);
    }

    @Test
    public void openVacanciesTest() {
        EmployersList listAPI = new EmployerAdapter().getCountOpenVacancies(SEARCH_TEXT_EMPLOYER, SEARCH_AREA,SEARCH_WITH_VACANCIES);
        int countEmployersAPI = listAPI.getFound();

        employerPage.openPage();
        employerPage.advancedSearchEmployers(SEARCH_COUNTRY, SEARCH_REGION, SEARCH_TEXT_EMPLOYER, false);
        EmployersList listUI = employerPage.getListEmployers();
        int countEmployersUI = listUI.getFound();
        Assert.assertEquals(countEmployersAPI, countEmployersUI);

        for(int i = 0; i < countEmployersAPI; i++){
            String nameAPI = listAPI.getItems().get(i).getName();
            String countVacanciesAPI = listAPI.getItems().get(i).getOpenVacancies();
            for (int j = 0 ; j < countEmployersUI; j++){
                String nameUI = listUI.getItems().get(j).getName();
                if(nameAPI.equals(nameUI)){
                    String countVacanciesUI = listUI.getItems().get(j).getOpenVacancies();
                    Assert.assertEquals(countVacanciesAPI, countVacanciesUI);
                }
            }
        }
    }
}
