package com.simulation.config;

import com.simulation.world.Plant;
import com.simulation.world.specific.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;

public class EatProbabilityTable {
    private static final Map<Class<?>, Map<Class<?>, Integer>> table = new ConcurrentHashMap<>();

    static {
        //Вероятности успешной охоты
        //Волк
        table.computeIfAbsent(Wolf.class, k -> new ConcurrentHashMap<>()).put(Rabbit.class, 30);
        table.computeIfAbsent(Wolf.class, k -> new ConcurrentHashMap<>()).put(Mouse.class, 45);
        table.computeIfAbsent(Wolf.class, k -> new ConcurrentHashMap<>()).put(Sheep.class, 30);
        table.computeIfAbsent(Wolf.class, k -> new ConcurrentHashMap<>()).put(Goat.class, 25);

        //Удав
        table.computeIfAbsent(Boa.class, k -> new ConcurrentHashMap<>()).put(Mouse.class, 70);
        table.computeIfAbsent(Boa.class, k -> new ConcurrentHashMap<>()).put(Rabbit.class, 20);
        table.computeIfAbsent(Boa.class, k -> new ConcurrentHashMap<>()).put(Caterpillar.class, 90);

        //Лиса
        table.computeIfAbsent(Fox.class, k -> new ConcurrentHashMap<>()).put(Rabbit.class, 50);
        table.computeIfAbsent(Fox.class, k -> new ConcurrentHashMap<>()).put(Mouse.class, 70);
        table.computeIfAbsent(Fox.class, k -> new ConcurrentHashMap<>()).put(Duck.class, 30);

        //Медведь
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Horse.class, 5);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Deer.class, 8);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Goat.class, 10);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Sheep.class, 15);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Boar.class, 10);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Buffalo.class, 2);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Rabbit.class, 50);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Mouse.class, 70);
        table.computeIfAbsent(Bear.class, k -> new ConcurrentHashMap<>()).put(Duck.class, 20);

        //Орел
        table.computeIfAbsent(Eagle.class, k -> new ConcurrentHashMap<>()).put(Mouse.class, 70);
        table.computeIfAbsent(Eagle.class, k -> new ConcurrentHashMap<>()).put(Rabbit.class, 30);
        table.computeIfAbsent(Eagle.class, k -> new ConcurrentHashMap<>()).put(Duck.class, 50);

        //Травоядные и растения
        table.computeIfAbsent(Duck.class, k -> new ConcurrentHashMap<>()).put(Caterpillar.class, 90); table.computeIfAbsent(Duck.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
        table.computeIfAbsent(Horse.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100); table.computeIfAbsent(Deer.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
        table.computeIfAbsent(Rabbit.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100); table.computeIfAbsent(Mouse.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
        table.computeIfAbsent(Goat.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100); table.computeIfAbsent(Sheep.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
        table.computeIfAbsent(Boar.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100); table.computeIfAbsent(Buffalo.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
        table.computeIfAbsent(Caterpillar.class, k -> new ConcurrentHashMap<>()).put(Plant.class, 100);
    }

    public static int getProbability(Class<?> predatorOrHerbivore, Class<?> prey) {
        return table.getOrDefault(predatorOrHerbivore, Collections.emptyMap()).getOrDefault(prey, 0);
    }
}