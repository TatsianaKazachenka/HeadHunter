package adapters;

import com.google.gson.Gson;
import objects.*;

import java.util.HashMap;
import java.util.Map;

public class EmployerAdapter extends BaseAdapter{

    public EmployersList getWithParams(Map<String, String> params){
        String body = getWithParams(EMPLOYERS_API_URL, params);
        EmployersList list = new Gson().fromJson(body, EmployersList.class);
        return list;
    }

    public EmployersList getCountEmployersListWithOpenVacancies(String withVacancies) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("only_with_vacancies", withVacancies);
        EmployersList list = getWithParams(params);
        return list;
    }

    public EmployersList getCountEmployersAdvancedSearch(String area) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("area", area);
        EmployersList list = getWithParams(params);;
        return list;
    }

    public EmployersList getCountOpenVacancies(String search, String area,String withVacancies) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("area", area);
        params.put("text", search);
        params.put("only_with_vacancies", withVacancies);
        EmployersList list = getWithParams(params);;
        return list;
    }
}
