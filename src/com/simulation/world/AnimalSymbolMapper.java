package com.simulation.world;

import com.simulation.world.specific.*;

import java.util.HashMap;
import java.util.Map;

public class AnimalSymbolMapper {

    private static final Map<Class<? extends Animal>, String> SYMBOLS = new HashMap<>();

    static {
        SYMBOLS.put(Horse.class, "🐎");
        SYMBOLS.put(Deer.class, "🦌");
        SYMBOLS.put(Rabbit.class, "🐇");
        SYMBOLS.put(Mouse.class, "🐁");
        SYMBOLS.put(Goat.class, "🐐");
        SYMBOLS.put(Sheep.class, "🐑");
        SYMBOLS.put(Boar.class, "🐗");
        SYMBOLS.put(Buffalo.class, "🐃");
        SYMBOLS.put(Duck.class, "🦆");
        SYMBOLS.put(Caterpillar.class, "🐛");

        SYMBOLS.put(Wolf.class, "🐺");
        SYMBOLS.put(Boa.class, "🐍");
        SYMBOLS.put(Fox.class, "🦊");
        SYMBOLS.put(Bear.class, "🐻");
        SYMBOLS.put(Eagle.class, "🦅");
    }

    public static Map<Class<? extends Animal>, String> getSymbols() {
        return SYMBOLS;
    }

    public static String getSymbolForClass(Class<? extends Animal> animalClass) {
        return SYMBOLS.getOrDefault(animalClass, "?");
    }
}