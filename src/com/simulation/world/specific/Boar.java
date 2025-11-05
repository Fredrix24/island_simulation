package com.simulation.world.specific;

import com.simulation.world.Animal;
import com.simulation.Location;
import com.simulation.world.Herbivore;
import com.simulation.config.SimulationParameters;
import com.simulation.AnimalNameMapper;
import com.simulation.AnimalColorMapper;
import java.awt.Color;

public class Boar extends Herbivore {

    public Boar() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Boar.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Boar.class); }
    @Override
    public String getAnimalSimpleName() { return AnimalNameMapper.getName(getAnimalClass()); }

    @Override
    public int getSpeed() { return 2; }
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
    protected Animal createNewOffspring() {
        try {
            Boar piglet = new Boar();
            return piglet;
        } catch (Exception e) {
            System.err.println("Error creating new Boar instance: " + e.getMessage());
            return null;
        }
    }
    @Override
    protected Animal findNearestPlant(Location currentLocation, Location[] possibleLocations) {
        if (currentLocation != null && currentLocation.getPlantCount() > 0) {
            return this;
        }

        if (possibleLocations != null) {
            for (Location loc : possibleLocations) {
                if (loc != null && loc.getPlantCount() > 0) {
                    return this;
                }
            }
        }
        return null;
    }
}