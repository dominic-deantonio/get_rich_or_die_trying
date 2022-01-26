import org.json.*;

public class Main {
    public static void main(String[] args) {
        JSONObject json = new JSONObject("{\"hello\":\"this is some text\"}");
        System.out.println(json.get("hello"));
    }
}
