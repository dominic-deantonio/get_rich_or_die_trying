import models.Person;
import org.junit.Test;

public class Tester {

    @Test
    public void testWhatever() {
        Person p = new Person();
        for(int i = 0 ; i < 20; i++){
            p.addMoney(i * -1000);
        }

    }
    @Test
    public void testWhatever2() {
//        int scale = (int) Math.pow(10, precision);

        System.out.println( (double) Math.round(1.2345687 * 2) / 2);

    }

}
