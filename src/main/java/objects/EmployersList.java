package objects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EmployersList {
    ArrayList<Employer> items;
    int found;
    int pages;
    @SerializedName("per_page")
    int perPage;
    int page;
}
