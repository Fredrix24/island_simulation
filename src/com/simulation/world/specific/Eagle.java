package com.simulation.world.specific;

import com.simulation.world.Animal;
import com.simulation.Location;
import com.simulation.world.Predator;
import com.simulation.config.SimulationParameters;
import com.simulation.AnimalNameMapper;
import com.simulation.AnimalColorMapper;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class Eagle extends Predator {

    public Eagle() {
        super();
        initializeState();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;
    }

    @Override
    public Class<? extends Animal> getAnimalClass() { return Eagle.class; }
    @Override
    public Color getColor() { return AnimalColorMapper.getColor(Eagle.class); }
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
    protected Animal findNearestPlant(Location currentLocation, Location[] possibleLocations) {
        return null;
    }

    @Override
    public boolean tryToEatPlant() {
        return false;
    }

    @Override
    public boolean tryToHunt() {
        Animal prey = this.nearestTarget;

        if (prey != null && prey.isAlive()) {
            Location preyLocation = prey.getCurrentLocation();
            Location predatorLocation = this.getCurrentLocation();

            if (predatorLocation != null && preyLocation != null && predatorLocation.equals(preyLocation)) {
                if (ThreadLocalRandom.current().nextInt(100) < 70) {
                    eat(prey);
                    this.nearestTarget = null;
                    return true;
                } else {
                    System.out.println(this.getClass().getSimpleName() + " tried to hunt, but failed (random chance).");
                    return false;
                }
            } else {
                return false;
            }
        } else {
            this.nearestTarget = null;
            return false;
        }
    }

    @Override
    protected Animal createNewOffspring() {
        try {
            Eagle eaglet = new Eagle();
            return eaglet;
        } catch (Exception e) {
            System.err.println("Error creating new Eagle instance: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected List<Class<? extends Animal>> getFoodSources() {
        return Arrays.asList(
                Rabbit.class, Mouse.class, Duck.class, Caterpillar.class
        );
    }
}