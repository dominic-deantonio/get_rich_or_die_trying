package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Careers {

    DANGER, KNOWLEDGE, PASSION;

    Map<Careers, List<String>> collegeCareers = new HashMap<>();
    Map<Careers, List<String>> noncollegeCareers = new HashMap<>();
}

