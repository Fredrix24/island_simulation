package com.simulation;

import com.simulation.world.Animal;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.Map;

public class Island {
    private static Island instance;
    private final int width;
    private final int height;
    private final Location[][] grid;
    private final List<Animal> allAnimals;

    private Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Location[width][height];
        this.allAnimals = new CopyOnWriteArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.grid[x][y] = new Location(x, y, this);
            }
        }
    }

    public static Island getInstance(int width, int height) {
        if (instance == null) {
            instance = new Island(width, height);
        }
        return instance;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return null;
    }

    public void addAnimalToIsland(Animal animal, int x, int y) {
        Location loc = getLocation(x, y);
        if (loc != null) {
            loc.addAnimal(animal);
            allAnimals.add(animal);
        }
    }

    public List<Animal> getAllAnimalsUnfiltered() {
        return new ArrayList<>(allAnimals);
    }

    public List<Animal> getAllAnimals() {
        return allAnimals.stream()
                .filter(Animal::isAlive)
                .collect(Collectors.toList());
    }

    public int getTotalAnimalsCount() {
        return (int) allAnimals.stream()
                .filter(Animal::isAlive)
                .count();
    }

    public int getTotalPlantsCount() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                count += grid[x][y].getPlantCount();
            }
        }
        return count;
    }

    public void cleanUpDeadAnimals() {
        allAnimals.removeIf(animal -> !animal.isAlive());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] != null) {
                    grid[x][y].getAnimals().removeIf(animal -> !animal.isAlive());
                }
            }
        }
    }

    public List<Animal> getAllEntities() {
        return getAllAnimals();
    }

    public Map<Class<? extends Animal>, Integer> getAnimalCountsByType() {
        return allAnimals.stream()
                .filter(Animal::isAlive)
                .collect(Collectors.groupingBy(
                        Animal::getAnimalClass,
                        Collectors.summingInt(a -> 1)
                ));
    }
}