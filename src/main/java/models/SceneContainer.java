package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneContainer {

    Map<Boolean, List<Scene>> education = new HashMap<>();
    Map<String, List<Scene>> career = new HashMap<>();
    Map<String, List<Scene>> health = new HashMap<>();
    Map<Boolean, List<Scene>> partner = new HashMap<>();
    Map<Boolean, List<Scene>> privilege  = new HashMap<>();
    Map<String, List<Scene>> hobbies = new HashMap<>();
    Map<Boolean, List<Scene>> children = new HashMap<>();
}