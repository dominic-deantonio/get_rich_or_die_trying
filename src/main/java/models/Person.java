package models;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

public class Person {

    private int netWorth = 0, health = 100, age = 18, children = 0;
    private final NumberFormat money = NumberFormat.getCurrencyInstance();
    private int strength = 0, intellect = 0, creativity = 0;
    Boolean education = false, isMarried = false, hasPrivilege = false;
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

    public void setNetWorth(int netWorth) {this.netWorth = netWorth;}

    public String getPrettyNetWorth() {
        return money.format(netWorth);
    }

    public boolean hasEducation() {
        return education;
    }

    public String getCategoryValue(int index) {
        List<String> fieldValues = List.of(
                career.toString().toLowerCase(),
                education.toString(),
                Boolean.toString(partner != null),
                hasPrivilege.toString(),
                Boolean.toString(health > 50),
                Boolean.toString(children > 0)
        );
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
        String gained = value < 0 ? "lost" : "gained";
        String msg = String.format("You have %s %d health", gained, value);
        System.out.println(msg);

    }

    public void addPartner(int value) {
        if (partner == null)
            partner = new Person("Sam", value);

        final String msg = String.format("You have a new partner named %s", partner.name);
        System.out.println(msg);

    }

    public void breakUp(int value) {
        System.out.println("Should break up with partner");

    }

    public void marryPartner(int value) {
        if (partner != null)
            partner.setMarried(true);
    }

    private void setMarried(boolean b) {
        this.isMarried = true;
    }

    public void addChild(int value) {
        children += value;
        String some = value > 1 ? "children" : "child";
        String msg = String.format("You have gained %d %s", value, some);
        System.out.println(msg);
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

        if(age > 50){
            Random rand = new Random();
            int amountHealthToDecrease = -(rand.nextInt(15) + 1);
            System.out.println("You are getting older and losing health.");
            addHealth(amountHealthToDecrease);
        }

        String msg = String.format("You are now %d years old.", age);
        System.out.println(msg);
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

    public void addSalary() {
        int amountToAdd = career.getSalaryAmount() * 5;
        double educationMultiplier = hasEducation() ? 1.5 : 1;
        double incomeMultiplier = getIncomeMultiplier();
        int sum = (int) (amountToAdd * educationMultiplier * incomeMultiplier);
        int oldNetWorth = netWorth;
        netWorth = sum + netWorth;
        final String msg = "You have earned " + money.format(sum) + " in the last 5 years from your job.\n\n" +
                "\nNet worth breakdown: " +
                "\nBase yearly salary: " + career.getSalaryAmount() +
                "\nYearly salary * 5 years: " + amountToAdd +
                "\nEducation Multiplier: " + educationMultiplier +
                "\nIncome Multiplier from " + getAttributeFromCareer() + ": " + incomeMultiplier +
                "\nTotal: (Yearly Salary * 5 years * education multiplier * income multiplier): " + money.format(sum) + " + Previous net worth: " + money.format(oldNetWorth) + "=" + money.format(netWorth);
        System.out.println(msg);



    }

    private String getAttributeFromCareer() {
        switch (career) {
            case DANGER:
                return "strength";
            case KNOWLEDGE:
                return "intellect";
            case PASSION:
                return "creativity";
            default:
                return "none";
        }
    }

    private double getIncomeMultiplier() {
        //1 - 1.5

        switch (career) {
            case DANGER:
                return (10.0 + strength)/10;
            case KNOWLEDGE:
                return (10.0 + intellect)/10;
            case PASSION:
                return (10.0 + creativity)/10;
            default:
                return 1;
        }
    }

    public void setName(String playerName) {
        this.name = playerName;
    }

    public void addCreativity(int i) {
        this.creativity += i;
    }

    public void addIntellect(int i) {
        this.intellect += i;
    }

    public void addStrength(int i) {
        this.strength += i;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getIntellect() {
        return this.intellect;
    }

    public int getCreativity() {
        return this.creativity;
    }

    public int getAge() {
        return this.age;
    }
}
