package objects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Employer {
    String id;
    String name;
    String url;
    @SerializedName("alternate_url")
    String alternateUrl;
    @SerializedName("vacancies_url")
    String vacanciesUrl;
    String type;
    String description;
    @SerializedName("site_url")
    String siteUrl;
    Regions area;
    @SerializedName("open_vacancies")
    String openVacancies;
}
