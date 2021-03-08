package objects;

import lombok.Data;

@Data
public class VacancySalary {
    int from;
    int to;
    String currency;
    boolean gross;
}
