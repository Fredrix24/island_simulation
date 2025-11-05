package com.simulation.world.specific;

import com.simulation.world.Animal;
import com.simulation.Location;
import com.simulation.world.Predator;
import com.simulation.config.SimulationParameters;
import com.simulation.AnimalNameMapper;
import com.simulation.AnimalColorMapper;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class Wolf extends Predator {

    public Wolf() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Wolf.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Wolf.class); }
    @Override
    public String getAnimalSimpleName() { return AnimalNameMapper.getName(getAnimalClass()); }

    @Override
    public int getSpeed() { return 3; }
    @Override
    public int getSatiety() { return this.satiety; }
    @Override
    public void setSatiety(int satiety) { this.satiety = satiety; }

    @Override
    public int getFoodRequiredPerTick() { return SimulationParameters.getFoodRequiredPerTick(getAnimalClass()); }
    @Override
    public int getMovementChance() { return SimulationParameters.getMovementChance(getAnimalClass()); }
    @Override
    public int getReproductionChance() { return SimulationParameters.getReproductionChance(getAnimalClass()); }
    @Override
    public int getMaxAge() { return SimulationParameters.getMaxAge(getAnimalClass()); }

    @Override
    public boolean tryToEatPlant() {
        return false;
    }
    @Override
    protected Animal createNewOffspring() {
        try {
            Wolf cub = new Wolf();
            return cub;
        } catch (Exception e) {
            System.err.println("Error creating new Wolf instance: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected Animal findNearestPlant(Location currentLocation, Location[] possibleLocations) {
        return null;
    }


    @Override
    protected List<Class<? extends Animal>> getFoodSources() {
        return Arrays.asList(
                Rabbit.class,
                Deer.class,
                Goat.class,
                Sheep.class,
                Boar.class,
                Buffalo.class
        );
    }
}