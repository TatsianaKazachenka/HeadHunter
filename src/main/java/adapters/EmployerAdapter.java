package adapters;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import objects.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class EmployerAdapter extends BaseAdapter{

    public EmployersList getWithParams(Map<String, String> params){
        log.info("Getting a list of employers with parameters");
        String body = getWithParams(EMPLOYERS_API_URL, params);
        return new Gson().fromJson(body, EmployersList.class);
    }

    public EmployersList getCountEmployersListWithOpenVacancies(String withVacancies) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("only_with_vacancies", withVacancies);
        return getWithParams(params);
    }

    public EmployersList getCountEmployersAdvancedSearch(String area) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("area", area);
        return getWithParams(params);
    }

    public EmployersList getCountOpenVacancies(String search, String area,String withVacancies) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("area", area);
        params.put("text", search);
        params.put("only_with_vacancies", withVacancies);
        return getWithParams(params);
    }

    public Employer getEmployer(String id) {
        String body = get(String.format("%s/%s", EMPLOYERS_API_URL, id));
        Employer list = new Gson().fromJson(body, Employer.class);
        return list;
    }
}
