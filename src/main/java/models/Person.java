package models;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class Person {

    Boolean education = false;
    Career career = Career.DANGER;

    public int getHealthPoints() {
        return 0;
    }

    public int getNetWorth() {
        return 0;
    }

    public String getPrettyNetWorth() {
        return "0";
    }

    public boolean hasEducation() {
        return false;
    }

    public String getCategoryValue(int index){
        List<String> fieldValues = List.of(career.toString().toLowerCase(), education.toString());
        return fieldValues.get(index);
    }
}
