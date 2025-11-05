package com.simulation.world;

import com.simulation.Location;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.simulation.Simulation;

public abstract class Predator extends Animal {

    public Predator() {
        super();
    }

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;

        tryToHunt();
    }

    public abstract int getSpeed();
    protected abstract List<Class<? extends Animal>> getFoodSources();

    @Override
    protected Animal findNearestTarget() {
        return findClosestPrey();
    }


    public Animal findClosestPrey() {

        Location currentLocation = this.getCurrentLocation();
        if (currentLocation == null) {
            return null;
        }

        Animal closestPrey = null;
        int minDistance = Integer.MAX_VALUE;

        Location[] adjacentLocations = currentLocation.getAdjacentLocations(getSpeed());


        for (Location loc : adjacentLocations) {
            if (loc != null) {
                for (Animal potentialPrey : loc.getAnimals()) {
                    if (potentialPrey.isAlive()) {
                        for (Class<? extends Animal> preyClass : getFoodSources()) {
                            if (preyClass.isInstance(potentialPrey)) {
                                int distance = currentLocation.getDistance(loc);
                                if (distance < minDistance) {
                                    minDistance = distance;
                                    closestPrey = potentialPrey;
                                }
                            }
                        }
                    }
                }
            }
        }
        return closestPrey;
    }

    public boolean tryToHunt() {
        Animal prey = this.nearestTarget;

        if (prey != null && prey.isAlive()) {
            Location preyLocation = prey.getCurrentLocation();
            Location predatorLocation = this.getCurrentLocation();

            if (predatorLocation != null && preyLocation != null && predatorLocation.equals(preyLocation)) {
                if (ThreadLocalRandom.current().nextInt(100) < 30) {
                    eat(prey);
                    this.nearestTarget = null;
                    return true;
                } else {
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

    protected void eat(Animal prey) {
        if (prey != null && prey.isAlive()) {
            prey.deathCause = "Eaten by " + this.getClass().getSimpleName();

            this.satiety += prey.getFoodValue();
            if (this.satiety > this.maxSatiety) this.satiety = this.maxSatiety;

            System.out.println("Tick " + Simulation.currentTick + ": " +
                    this.getClass().getSimpleName() + " (" + this.hashCode() + ") ate " +
                    prey.getClass().getSimpleName() + ".");

            prey.die();
        }
    }
}