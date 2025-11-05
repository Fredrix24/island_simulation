package com.simulation.console;

import com.simulation.Island;
import com.simulation.world.Animal;
import com.simulation.Location;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ConsoleRenderer {
    private Island island;
    private Map<Class<? extends Animal>, String> animalSymbols;

    public ConsoleRenderer(Island island, Map<Class<? extends Animal>, String> animalSymbols) {
        this.island = island;
        this.animalSymbols = animalSymbols;
    }
    private int currentTick = 0;

    public void setTick(int tick) {
        this.currentTick = tick;
    }

    public void render() {
        renderIslandState();
        renderAnimalStatistics();
        renderGrid();
    }

    private void renderIslandState() {
        System.out.println("--- Island State ---");
        System.out.println("Tick: " + currentTick + " | Plants: " + island.getTotalPlantsCount());
        System.out.println("--------------------\n");
    }

    private void renderAnimalStatistics() {
        Map<Class<? extends Animal>, Integer> counts = island.getAnimalCountsByType();
        System.out.println("--- Animal Statistics ---");
        if (counts.isEmpty()) {
            System.out.println("No animals on the island.");
        } else {
            List<Map.Entry<Class<? extends Animal>, Integer>> sortedCounts = new ArrayList<>(counts.entrySet());
            sortedCounts.sort(Map.Entry.comparingByKey( (class1, class2) -> class1.getName().compareTo(class2.getName()) ));

            for (Map.Entry<Class<? extends Animal>, Integer> entry : sortedCounts) {
                Class<? extends Animal> animalClass = entry.getKey();
                Integer count = entry.getValue();
                String symbol = animalSymbols.getOrDefault(animalClass, "?");

                System.out.println(symbol + " " + animalClass.getSimpleName() + ": " + count);
            }
        }
        System.out.println("-----------------------\n");
    }

    private void renderGrid() {
        System.out.println("--- Grid View ---");
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x, y);
                if (loc == null) {
                    System.out.print("?");
                } else if (loc.getPlantCount() > 0) {
                    System.out.print("P");
                } else if (!loc.getAnimals().isEmpty()) {
                    Animal animal = loc.getAnimals().get(0);
                    System.out.print(animalSymbols.getOrDefault(animal.getClass(), "?"));
                } else {
                    System.out.print(".");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----------------\n");
    }
}