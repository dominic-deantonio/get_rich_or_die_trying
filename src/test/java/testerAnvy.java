import controller.Game;
import models.Careers;
import models.Person;
import org.junit.Before;
import org.junit.Test;

public class testerAnvy {


    Person player = new Person();

    @Test
    public void intro() {
//        System.out.println(Game.welcome());

    }

    @Test
    public void career() {
//        System.out.println(Game.runSceneOneCareer(player));
    }

    @Test
    public void salary() {
        player.setCareer(Careers.DANGER);
        player.setEducation(true);
        player.addSalary();
    }


}
