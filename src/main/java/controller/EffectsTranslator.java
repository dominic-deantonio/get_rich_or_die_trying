package controller;

import models.Person;
import models.Scene;

import java.util.Map;

public class EffectsTranslator {

    public static void doEffects(Person player, Map<String, Object> effect) {

        for (String action : effect.keySet()) {
            action = action.trim();
            int value = (int) effect.get(action);

            switch (action) {
                case "money":
                    player.addMoney(value);
                    break;
                case "health":
                    player.addHealth(value);
                    break;
                case "dating":
                    player.addPartner(value);
                    break;
                case "breakup":
                    player.breakUp(value);
                    break;
                case "marry":
                    player.marryPartner(value);
                    break;
                case "addchild":
                    player.addChild(value);
                    break;
                case "career":
                    player.changeCareer(value);
                    break;
                default:
                    System.out.println("There is no valid action for the following effect: " + action + " with value " + value);

            }
        }

    }
}


