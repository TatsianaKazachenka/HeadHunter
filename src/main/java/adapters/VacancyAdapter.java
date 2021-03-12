package adapters;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import objects.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class VacancyAdapter extends BaseAdapter {

    public VacanciesList getWithParams(Map<String, String> params) {
        log.info("Getting a list of vacancies with parameters");
        String body = getWithParams(VACANCIES_API_URL, params);
        return new Gson().fromJson(body, VacanciesList.class);
    }

    public VacanciesList getVacanciesListForTextSearch(String search) {
        Map<String, String> params = new HashMap<>();
        params.put("text", search);
        return getWithParams(params);
    }

    public VacanciesList getAdvancedVacanciesListSearch(String search, String area, String currencyCode,
                                                        String experience, String employment, String schedule,
                                                        String orderBy, String period, String page) {
        Map<String, String> params = new HashMap<>();
        params.put("text", search);
        params.put("area", area);
        params.put("currency_code", currencyCode);
        params.put("experience", experience);
        params.put("employment", employment);
        params.put("schedule", schedule);
        params.put("order_by", orderBy);
        params.put("search_period", period);
        params.put("items_on_page", page);
        return getWithParams(params);
    }

    public Vacancy getVacancy(String id) {
        String body = get(String.format("%s/%s", VACANCIES_API_URL, id));
        return new Gson().fromJson(body, Vacancy.class);
    }
}
