package com.simulation.world;

import com.simulation.Simulation;
import com.simulation.Location;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    public Herbivore() {
        super();
    }
    public abstract int getSpeed();

    @Override
    public void liveTick() {
        super.liveTick();
        if (!alive) return;

        tryToEatPlant();
    }

    @Override
    public boolean tryToEatPlant() {
        Location currentLocation = this.getCurrentLocation();
        if (currentLocation != null && currentLocation.getPlantCount() > 0) {
            if (ThreadLocalRandom.current().nextInt(100) < 85) {
                currentLocation.removePlant();
                this.satiety += 15;
                if (this.satiety > this.maxSatiety) this.satiety = this.maxSatiety;
                System.out.println("Tick " + Simulation.currentTick + ": " +
                        this.getClass().getSimpleName() + " ate plant(s).");
                return true;
            }
        }
        return false;
    }
    @Override
    protected Animal findNearestTarget() {
        return findNearestPlant(this.getCurrentLocation(), this.getCurrentLocation().getAdjacentLocations(1));
    }
}