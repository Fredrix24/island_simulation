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

public class Boa extends Predator {

    public Boa() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Boa.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Boa.class); }
    @Override
    public String getAnimalSimpleName() { return AnimalNameMapper.getName(getAnimalClass()); }

    @Override
    public int getSpeed() { return 1; }
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
    protected Animal findNearestPlant(Location currentLocation, Location[] possibleLocations) {
        return null;
    }

    @Override
    public boolean tryToEatPlant() {
        return false;
    }

    @Override
    protected Animal createNewOffspring() {
        try {
            Boa hatchling = new Boa();
            return hatchling;
        } catch (Exception e) {
            System.err.println("Error creating new Boa instance: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected List<Class<? extends Animal>> getFoodSources() {
        return Arrays.asList(
                Mouse.class, Rabbit.class, Duck.class, Caterpillar.class
        );
    }
}