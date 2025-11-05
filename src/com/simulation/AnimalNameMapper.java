package com.simulation;

import com.simulation.world.Animal;
import com.simulation.world.specific.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnimalNameMapper {

    private static final Map<Class<? extends Animal>, String> NAMES;

    static {
        Map<Class<? extends Animal>, String> map = new HashMap<>();

        map.put(Bear.class, "Медведь");
        map.put(Boa.class, "Удав");
        map.put(Eagle.class, "Орел");
        map.put(Fox.class, "Лиса");
        map.put(Wolf.class, "Волк");

        map.put(Boar.class, "Кабан");
        map.put(Buffalo.class, "Буйвол");
        map.put(Caterpillar.class, "Гусеница");
        map.put(Deer.class, "Олень");
        map.put(Duck.class, "Утка");
        map.put(Goat.class, "Коза");
        map.put(Horse.class, "Лошадь");
        map.put(Mouse.class, "Мышь");
        map.put(Rabbit.class, "Кролик");
        map.put(Sheep.class, "Овца");

        NAMES = Collections.unmodifiableMap(map);
    }

    public static String getName(Class<? extends Animal> animalClass) {
        return NAMES.getOrDefault(animalClass, animalClass.getSimpleName());
    }
}