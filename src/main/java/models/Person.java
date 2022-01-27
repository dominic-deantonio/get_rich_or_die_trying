package models;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Person {

    private int netWorth = 0, health = 100, age = 18, children = 0;
    private final NumberFormat money = NumberFormat.getCurrencyInstance();

    Boolean education = false, isMarried=false;
    Careers career = Careers.PASSION;
    Person partner = null;
    String name;

    public Person() {
        money.setMaximumFractionDigits(0);
    }

    public Person(String name, int initialWorth) {
        this.name = name;
        this.netWorth = initialWorth;
    }

    public int getHealthPoints() {
        return this.health;
    }

    public int getNetWorth() {
        return netWorth;
    }

    public String getPrettyNetWorth() {
        return money.format(netWorth);
    }

    public boolean hasEducation() {
        return education;
    }

    public String getCategoryValue(int index) {
        List<String> fieldValues = List.of(career.toString().toLowerCase(), education.toString());
        return fieldValues.get(index);
    }

    public void addMoney(int value) {
        netWorth += value;
        final String msg = String.format("You have %s %s", (value < 0 ? "lost" : "gained"), money.format(value));
        System.out.println(msg);
    }

    public void addHealth(int value) {
        health += value;
        if (health > 100)
            health = 100;
        String msg = String.format("You have gained %d health", value);
        System.out.println(msg);

    }

    public void addPartner(int value) {
        if (partner == null)
            partner = new Person("Janine", value);

        final String msg = String.format("You have a new partner named %s", partner.name);
        System.out.println(msg);

    }

    public void breakUp(int value) {
        System.out.println("Should break up with partner");

    }

    public void marryPartner(int value) {
        System.out.println("Should marry partner and get dowry of " + value);

    }

    public void addChild(int value) {
        System.out.println("Should add children: " + value);

    }

    public void changeCareer(int value) {
        String oldCareer = career.name();
        career = Careers.values()[value];
        final String msg = String.format("Your career has changed from %s to %s", oldCareer, career.toString());
        System.out.println(msg);
    }

    public void setEducation(boolean b) {
        this.education = b;
    }

    public void setCareer(Careers career) {
        this.career = career;
    }

    public Careers getCareer() {
        return this.career;
    }

    public void addAge(int i) {
        age += i;
    }

    public String getName() {
        return name;
    }

    public int getChildren() {
        return this.children;
    }

    public Person getPartner() {
        return partner;
    }

    public boolean isMarried() {
        return isMarried;
    }
}
