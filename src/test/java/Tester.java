import models.Person;
import org.junit.Test;

public class Tester {

    @Test
    public void testWhatever() {
        Person p = new Person();
        for(int i = 0 ; i < 20; i++){
            p.addAge(5);
        }

    }


}
