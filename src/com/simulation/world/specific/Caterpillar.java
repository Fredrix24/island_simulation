package com.simulation.world.specific;

import com.simulation.world.Animal;
import com.simulation.Location;
import com.simulation.world.Herbivore;
import com.simulation.config.SimulationParameters;
import com.simulation.AnimalNameMapper;
import com.simulation.AnimalColorMapper;
import java.awt.Color;

public class Caterpillar extends Herbivore {

    public Caterpillar() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Caterpillar.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Caterpillar.class); }
    @Override
    public String getAnimalSimpleName() { return AnimalNameMapper.getName(getAnimalClass()); }

    @Override
    public int getSpeed() { return 0; }
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
            Caterpillar larva = new Caterpillar();
            return larva;
        } catch (Exception e) {
            System.err.println("Error creating new Caterpillar instance: " + e.getMessage());
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