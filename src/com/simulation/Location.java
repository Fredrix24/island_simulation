package com.simulation;

import com.simulation.config.SimulationParameters;
import com.simulation.world.Animal;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final int x;
    private final int y;
    private int plantCount;
    private final List<Animal> animals;
    private final Island island;

    private static final int MAX_PLANTS_PER_LOCATION = 5;

    public Location(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.plantCount = 0;
        this.animals = new CopyOnWriteArrayList<>();
        this.island = island;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getPlantCount() { return plantCount; }
    public void addPlant() {
        if (plantCount < MAX_PLANTS_PER_LOCATION) {
            plantCount++;
        }
    }
    public void removePlant() {
        if (plantCount > 0) {
            plantCount--;
        }
    }

    public List<Animal> getAnimals() { return animals; }
    public void addAnimal(Animal animal) {
        if (canAddEntity()) {
            this.animals.add(animal);
            animal.setCurrentLocation(this);
        }
    }
    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
    }

    public List<Animal> getEntities() {
        return new ArrayList<>(animals);
    }

    public boolean canAddEntity() {
        return this.animals.size() < SimulationParameters.MAX_ENTITIES_PER_LOCATION;
    }

    public Location[] getAdjacentLocations(int radius) {
        List<Location> adj = new ArrayList<>();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i == 0 && j == 0) continue;

                int targetX = this.x + i;
                int targetY = this.y + j;

                Location adjacentLoc = island.getLocation(targetX, targetY);
                if (adjacentLoc != null) {
                    adj.add(adjacentLoc);
                }
            }
        }
        return adj.toArray(new Location[0]);
    }

    public int getDistance(Location other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}