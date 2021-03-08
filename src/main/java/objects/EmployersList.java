package objects;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EmployersList {
    ArrayList<Employer> items;
    int found;
    int pages;
    int per_page;
    int page;
}
