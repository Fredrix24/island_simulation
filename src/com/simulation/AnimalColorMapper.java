package com.simulation;

import com.simulation.world.Animal;
import com.simulation.world.specific.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class AnimalColorMapper {
    private static final Map<Class<? extends Animal>, Color> colorMap = new HashMap<>();

    static {
        colorMap.put(Horse.class, Color.decode("#C0C0C0"));
        colorMap.put(Deer.class, Color.decode("#8B4513"));
        colorMap.put(Rabbit.class, Color.decode("#D3D3D3"));
        colorMap.put(Mouse.class, Color.decode("#A9A9A9"));
        colorMap.put(Goat.class, Color.decode("#FFFFFF"));
        colorMap.put(Sheep.class, Color.decode("#F5F5DC"));
        colorMap.put(Boar.class, Color.decode("#8B4513"));
        colorMap.put(Buffalo.class, Color.decode("#4A4A4A"));
        colorMap.put(Duck.class, Color.decode("#FFD700"));
        colorMap.put(Caterpillar.class, Color.decode("#90EE90"));

        colorMap.put(Wolf.class, Color.decode("#4682B4"));
        colorMap.put(Boa.class, Color.decode("#556B2F"));
        colorMap.put(Fox.class, Color.decode("#FFA500"));
        colorMap.put(Bear.class, Color.decode("#8B0000"));
        colorMap.put(Eagle.class, Color.decode("#87CEEB"));

    }

    public static Color getColor(Class<? extends Animal> animalClass) {
        return colorMap.getOrDefault(animalClass, Color.WHITE);
    }
}