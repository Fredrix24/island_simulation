package com.simulation.world.specific;

import com.simulation.world.Animal;
import com.simulation.Location;
import com.simulation.world.Herbivore;
import com.simulation.config.SimulationParameters;
import com.simulation.AnimalNameMapper;
import com.simulation.AnimalColorMapper;
import java.awt.Color;

public class Mouse extends Herbivore {

    public Mouse() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Mouse.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Mouse.class); }
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
    protected Animal createNewOffspring() {
        try {
            Mouse pup = new Mouse();
            return pup;
        } catch (Exception e) {
            System.err.println("Error creating new Mouse instance: " + e.getMessage());
            return null;
        }
    }
}