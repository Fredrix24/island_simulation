package com.simulation.config;

import com.simulation.world.Animal;
import com.simulation.world.specific.*;
import java.util.HashMap;
import java.util.Map;

public class SimulationParameters {
    public static final int ISLAND_WIDTH = 50;
    public static final int ISLAND_HEIGHT = 50;
    public static final long TICK_DURATION_MS = 1000;
    public static final int MAX_SIMULATION_TICKS = 200;
    public static final int INITIAL_PLANTS = 1000;
    public static final int INITIAL_ANIMALS_PER_SPECIES = 50;
    public static final int STATS_UPDATE_FREQUENCY_TICKS = 5;

    public static final boolean STOP_IF_ALL_ANIMALS_DIE = true;
    public static final boolean STOP_IF_NO_PLANTS_LEFT = false;

    public static final int MAX_ENTITIES_PER_LOCATION = 150;
    public static final Map<Class<? extends Animal>, Integer> MAX_POPULATION_PER_LOCATION_MAP = new HashMap<>();

    public static final Map<Class<? extends Animal>, Double> WEIGHT_MAP = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> MAX_SATIETY_MAP = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> FOOD_REQUIRED_PER_TICK_MAP = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> MAX_OFFSPRING_MAP = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> MOVEMENT_CHANCE_MAP = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> MAX_AGE_MAP = new HashMap<>();

    public static final int PLANT_ENERGY_VALUE = 25;
    public static final int PLANT_GROWTH_RATE = 50;
    public static final int PLANT_MAX_GROWTH = 15;


    static {
        //Возраст животного
        MAX_AGE_MAP.put(Wolf.class, 50); MAX_AGE_MAP.put(Boa.class, 80); MAX_AGE_MAP.put(Fox.class, 40);
        MAX_AGE_MAP.put(Bear.class, 100); MAX_AGE_MAP.put(Eagle.class, 60); MAX_AGE_MAP.put(Horse.class, 70);
        MAX_AGE_MAP.put(Deer.class, 60); MAX_AGE_MAP.put(Rabbit.class, 25); MAX_AGE_MAP.put(Mouse.class, 15);
        MAX_AGE_MAP.put(Goat.class, 30); MAX_AGE_MAP.put(Sheep.class, 35); MAX_AGE_MAP.put(Boar.class, 50);
        MAX_AGE_MAP.put(Buffalo.class, 80); MAX_AGE_MAP.put(Duck.class, 20); MAX_AGE_MAP.put(Caterpillar.class, 35);

        //Вес животного
        WEIGHT_MAP.put(Wolf.class, 50.0); WEIGHT_MAP.put(Boa.class, 15.0); WEIGHT_MAP.put(Fox.class, 8.0);
        WEIGHT_MAP.put(Bear.class, 500.0); WEIGHT_MAP.put(Eagle.class, 6.0); WEIGHT_MAP.put(Horse.class, 400.0);
        WEIGHT_MAP.put(Deer.class, 300.0); WEIGHT_MAP.put(Rabbit.class, 2.0); WEIGHT_MAP.put(Mouse.class, 0.05);
        WEIGHT_MAP.put(Goat.class, 60.0); WEIGHT_MAP.put(Sheep.class, 70.0); WEIGHT_MAP.put(Boar.class, 400.0);
        WEIGHT_MAP.put(Buffalo.class, 700.0); WEIGHT_MAP.put(Duck.class, 1.0); WEIGHT_MAP.put(Caterpillar.class, 0.01);

        //Максимальное количество животного
        MAX_POPULATION_PER_LOCATION_MAP.put(Wolf.class, 50); MAX_POPULATION_PER_LOCATION_MAP.put(Boa.class, 15); MAX_POPULATION_PER_LOCATION_MAP.put(Fox.class, 8);
        MAX_POPULATION_PER_LOCATION_MAP.put(Bear.class, 500); MAX_POPULATION_PER_LOCATION_MAP.put(Eagle.class, 6); MAX_POPULATION_PER_LOCATION_MAP.put(Horse.class, 400);
        MAX_POPULATION_PER_LOCATION_MAP.put(Deer.class, 300); MAX_POPULATION_PER_LOCATION_MAP.put(Rabbit.class, 2); MAX_POPULATION_PER_LOCATION_MAP.put(Mouse.class, 2);
        MAX_POPULATION_PER_LOCATION_MAP.put(Goat.class, 60); MAX_POPULATION_PER_LOCATION_MAP.put(Sheep.class, 70); MAX_POPULATION_PER_LOCATION_MAP.put(Boar.class, 400);
        MAX_POPULATION_PER_LOCATION_MAP.put(Buffalo.class, 700); MAX_POPULATION_PER_LOCATION_MAP.put(Duck.class, 1); MAX_POPULATION_PER_LOCATION_MAP.put(Caterpillar.class, 1);

        //Максимальная сытость животного
        MAX_SATIETY_MAP.put(Wolf.class, 60); MAX_SATIETY_MAP.put(Boa.class, 40); MAX_SATIETY_MAP.put(Fox.class, 70);
        MAX_SATIETY_MAP.put(Bear.class, 100); MAX_SATIETY_MAP.put(Eagle.class, 45); MAX_SATIETY_MAP.put(Horse.class, 80);
        MAX_SATIETY_MAP.put(Deer.class, 70); MAX_SATIETY_MAP.put(Rabbit.class, 60); MAX_SATIETY_MAP.put(Mouse.class, 30);
        MAX_SATIETY_MAP.put(Goat.class, 30); MAX_SATIETY_MAP.put(Sheep.class, 30); MAX_SATIETY_MAP.put(Boar.class, 70);
        MAX_SATIETY_MAP.put(Buffalo.class, 120); MAX_SATIETY_MAP.put(Duck.class, 40); MAX_SATIETY_MAP.put(Caterpillar.class, 30);

        //Требуемое количество еды в тик животного
        FOOD_REQUIRED_PER_TICK_MAP.put(Wolf.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Boa.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Fox.class, 2);
        FOOD_REQUIRED_PER_TICK_MAP.put(Bear.class, 4); FOOD_REQUIRED_PER_TICK_MAP.put(Eagle.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Horse.class, 4);
        FOOD_REQUIRED_PER_TICK_MAP.put(Deer.class, 3); FOOD_REQUIRED_PER_TICK_MAP.put(Rabbit.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Mouse.class, 1);
        FOOD_REQUIRED_PER_TICK_MAP.put(Goat.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Sheep.class, 3); FOOD_REQUIRED_PER_TICK_MAP.put(Boar.class, 3);
        FOOD_REQUIRED_PER_TICK_MAP.put(Buffalo.class, 3); FOOD_REQUIRED_PER_TICK_MAP.put(Duck.class, 2); FOOD_REQUIRED_PER_TICK_MAP.put(Caterpillar.class, 1);

        //Максимальное количество детёнышей
        MAX_OFFSPRING_MAP.put(Horse.class, 3); MAX_OFFSPRING_MAP.put(Deer.class, 3); MAX_OFFSPRING_MAP.put(Rabbit.class, 7);
        MAX_OFFSPRING_MAP.put(Mouse.class, 10); MAX_OFFSPRING_MAP.put(Goat.class, 3); MAX_OFFSPRING_MAP.put(Sheep.class, 5);
        MAX_OFFSPRING_MAP.put(Boar.class, 4); MAX_OFFSPRING_MAP.put(Buffalo.class, 2); MAX_OFFSPRING_MAP.put(Duck.class, 5);
        MAX_OFFSPRING_MAP.put(Caterpillar.class, 15);
        MAX_OFFSPRING_MAP.put(Wolf.class, 3); MAX_OFFSPRING_MAP.put(Boa.class, 3); MAX_OFFSPRING_MAP.put(Fox.class, 4);
        MAX_OFFSPRING_MAP.put(Bear.class, 2); MAX_OFFSPRING_MAP.put(Eagle.class, 3);

        //Вероятность движения животного
        MOVEMENT_CHANCE_MAP.put(Wolf.class, 110); MOVEMENT_CHANCE_MAP.put(Boa.class, 70); MOVEMENT_CHANCE_MAP.put(Fox.class, 95);
        MOVEMENT_CHANCE_MAP.put(Bear.class, 70); MOVEMENT_CHANCE_MAP.put(Eagle.class, 100); MOVEMENT_CHANCE_MAP.put(Horse.class, 90);
        MOVEMENT_CHANCE_MAP.put(Deer.class, 95); MOVEMENT_CHANCE_MAP.put(Rabbit.class, 100); MOVEMENT_CHANCE_MAP.put(Mouse.class, 100);
        MOVEMENT_CHANCE_MAP.put(Goat.class, 90); MOVEMENT_CHANCE_MAP.put(Sheep.class, 85); MOVEMENT_CHANCE_MAP.put(Boar.class, 80);
        MOVEMENT_CHANCE_MAP.put(Buffalo.class, 50); MOVEMENT_CHANCE_MAP.put(Duck.class, 95); MOVEMENT_CHANCE_MAP.put(Caterpillar.class, 70);
    }

    public static int getMaxPopulationPerLocation(Class<? extends Animal> animalClass) { return MAX_POPULATION_PER_LOCATION_MAP.getOrDefault(animalClass, MAX_ENTITIES_PER_LOCATION); }
    public static double getWeight(Class<? extends Animal> animalClass) { return WEIGHT_MAP.getOrDefault(animalClass, 1.0); }
    public static int getMaxSatiety(Class<? extends Animal> animalClass) { return MAX_SATIETY_MAP.getOrDefault(animalClass, 50); }
    public static int getFoodRequiredPerTick(Class<? extends Animal> animalClass) {
        if (FOOD_REQUIRED_PER_TICK_MAP.containsKey(animalClass) && FOOD_REQUIRED_PER_TICK_MAP.get(animalClass) >= 0) {
            return Math.max(0, FOOD_REQUIRED_PER_TICK_MAP.get(animalClass));
        }
        int maxSatiety = getMaxSatiety(animalClass);
        if (maxSatiety > 0) {
            return Math.max(1, maxSatiety / 10);
        }
        return 0;
    }
    public static int getMaxOffspring(Class<? extends Animal> animalClass) { return MAX_OFFSPRING_MAP.getOrDefault(animalClass, 2); }
    public static int getMovementChance(Class<? extends Animal> animalClass) { return MOVEMENT_CHANCE_MAP.getOrDefault(animalClass, 100); }
    public static int getPlantEnergyValue() { return PLANT_ENERGY_VALUE; }
    public static int getPlantGrowthRate() { return PLANT_GROWTH_RATE; }
    public static int getPlantMaxGrowth() { return PLANT_MAX_GROWTH; }
    public static int getReproductionChance(Class<? extends Animal> animalClass) {
        if (animalClass == Wolf.class) return 30;
        if (animalClass == Bear.class) return 20;
        if (animalClass == Rabbit.class) return 40;
        if (animalClass == Sheep.class) return 35;
        if (animalClass == Horse.class) return 20;
        if (animalClass == Mouse.class) return 45;
        if (animalClass == Buffalo.class) return 20;
        if (animalClass == Deer.class) return 30;
        if (animalClass == Caterpillar.class) return 30;
        if (animalClass == Duck.class) return 45;
        if (animalClass == Eagle.class) return 4;
        if (animalClass == Fox.class) return 20;
        if (animalClass == Boa.class) return 30;
        if (animalClass == Boar.class) return 25;
        if (animalClass == Goat.class) return 30;
        return 5;
    }

    public static int getMaxAge(Class<? extends Animal> animalClass) {
        if (animalClass == Wolf.class) return 150;
        if (animalClass == Bear.class) return 200;
        if (animalClass == Rabbit.class) return 40;
        if (animalClass == Sheep.class) return 50;
        if (animalClass == Horse.class) return 70;
        if (animalClass == Mouse.class) return 40;
        if (animalClass == Buffalo.class) return 60;
        if (animalClass == Deer.class) return 80;
        if (animalClass == Caterpillar.class) return 30;
        if (animalClass == Duck.class) return 40;
        if (animalClass == Eagle.class) return 100;
        if (animalClass == Fox.class) return 90;
        if (animalClass == Boa.class) return 120;
        if (animalClass == Boar.class) return 70;
        if (animalClass == Goat.class) return 60;
        return 50;
    }
}