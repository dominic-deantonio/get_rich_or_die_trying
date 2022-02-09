package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Careers {

    DANGER, KNOWLEDGE, PASSION;

    /*
     * Creates a field that holds a Map of college careers using Careers ENUM value as key and List<String> as value
     */
    private static Map<Careers, List<String>> collegeCareers = Map.of(
            DANGER, List.of("ANTARCTIC EXPLORER", "ASTRONAUT", "FIGHTER PILOT"),
            KNOWLEDGE, List.of("DOCTOR", "PROFESSOR", "ACCOUNTANT"),
            PASSION, List.of("CHARITY WORKER", "MUSEUM CURATOR", "PHILOSOPHER")
    );

    /*
     * Creates a field that holds a Map of non-college careers using Careers ENUM value as key and List<String> as value
     */
    private static Map<Careers, List<String>> nonCollegeCareers = Map.of(
            DANGER, List.of("UNDERWATER SEA WELDER", "TREE CUTTER", "MARINE"),
            KNOWLEDGE, List.of("DATA ENTRY", "PLUMBER", "SOFTWARE ENGINEER"),
            PASSION, List.of("SCULPTOR", "MUSICIAN", "PERFORMER")
    );

    /*
     * Creates a field that holds a Map of salaries using Careers ENUM value as key and Integer as value
     */
    private static Map<Careers, Integer> salaries = Map.of(
            DANGER, 20000,
            KNOWLEDGE, 15000,
            PASSION, 10000
    );

    //Getter and Setters
    public static Map<Careers, List<String>> getCollegeCareers() {
        return collegeCareers;
    }

    public static Map<Careers, List<String>> getNonCollegeCareers() {
        return nonCollegeCareers;
    }

    public int getSalaryAmount() {
        return salaries.get(this);
    }

}

