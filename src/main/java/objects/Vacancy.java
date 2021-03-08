package objects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Vacancy {
    String id;
    boolean premium;
    String name;
    @SerializedName("has_test")
    boolean hasTest;
    @SerializedName("response_letter_required")
    boolean responseLetterRequired;
    Regions area;
    VacancySalary salary;
    @SerializedName("response_url")
    String responseUrl;
    @SerializedName("sort_point_distance")
    String sortPointDistance;
    @SerializedName("published_at")
    Date published;
    @SerializedName("created_at")
    Date created;
    boolean archived;
    @SerializedName("apply_alternate_url")
    String applyAlternateUrl;
    @SerializedName("insider_interview")
    String insiderInterview;
    String url;
    @SerializedName("alternate_url")
    String alternateUrl;
    Employer employer;
    String contacts;
    @SerializedName("key_skills")
    ArrayList<VacancySkill> skills;
}
