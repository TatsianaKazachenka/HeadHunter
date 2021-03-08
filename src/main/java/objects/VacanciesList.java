package objects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

@Data
public class VacanciesList {
    ArrayList<Vacancy> items;
    int found;
    int pages;
    @SerializedName("per_page")
    int perPage;
    int page;
    String clusters;
    String arguments;
    @SerializedName("alternate_url")
    String alternateUrl;
}
