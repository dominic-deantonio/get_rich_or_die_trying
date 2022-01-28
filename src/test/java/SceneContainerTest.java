import models.Person;
import models.Scene;
import models.SceneContainer;
import org.junit.Test;

public class SceneContainerTest {

    @Test
    public void testGetRandomScene(){
        Person player = new Person();

        Scene scene = new SceneContainer().getRandomScene(player);
        System.out.println(scene.prompt);
    }

}
