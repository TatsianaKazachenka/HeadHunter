package adapters;

import com.google.gson.Gson;
import objects.*;

import java.util.HashMap;
import java.util.Map;

public class EmployerAdapter extends BaseAdapter{

    public EmployersList getCountEmployersListWithOpenVacancies() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("only_with_vacancies", "true");
        String body = getWithParams(EMPLOYERS_API_URL, params);
        EmployersList list = new Gson().fromJson(body, EmployersList.class);
        return list;
    }

    public EmployersList getCountEmployersAdvancedSearch() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("area", "1007");
        String body = getWithParams(EMPLOYERS_API_URL, params);
        EmployersList list = new Gson().fromJson(body, EmployersList.class);
        return list;
    }
}
