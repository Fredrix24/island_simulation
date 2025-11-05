package com.simulation.world;

import com.simulation.Simulation;
import com.simulation.config.SimulationParameters;
import com.simulation.Location;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;

public abstract class Animal {
    protected int satiety;
    protected int age;
    public boolean alive;
    public Location currentLocation;
    protected Color color;
    protected Animal nearestTarget = null;

    protected int maxSatiety;
    protected int foodRequiredPerTick;
    protected int movementChance;
    protected int reproductionChance;
    protected int maxAge;

    public Animal() {
        this.alive = true;
        this.age = 0;
        this.satiety = 0;
        this.color = Color.BLACK;
    }
    public abstract int getSatiety();
    public abstract void setSatiety(int satiety);
    protected int foodValue;

    public abstract int getFoodRequiredPerTick();
    public abstract int getMovementChance();
    public abstract int getSpeed();
    protected abstract Animal findNearestPlant(Location currentLocation, Location[] possibleLocations);

    public abstract int getReproductionChance();
    public abstract int getMaxAge();
    protected String deathCause;

    public abstract Class<? extends Animal> getAnimalClass();
    public abstract Color getColor();
    public abstract String getAnimalSimpleName();

    public abstract boolean tryToEatPlant();
    protected abstract Animal createNewOffspring();

    public void initializeState() {
        this.maxSatiety = SimulationParameters.getMaxSatiety(getAnimalClass());
        this.foodRequiredPerTick = SimulationParameters.getFoodRequiredPerTick(getAnimalClass());
        this.movementChance = SimulationParameters.getMovementChance(getAnimalClass());

        this.reproductionChance = getReproductionChance();
        this.maxAge = getMaxAge();

        this.satiety = this.maxSatiety / 2;
        this.color = getColor();
    }

    public void liveTick() {
        if (!alive) return;
        this.satiety -= this.foodRequiredPerTick;
        if (this.satiety <= 0) {
            deathCause = "Starvation";
            die();
            return;
        }
        this.age++;
        if (this.age >= this.maxAge) {
            deathCause = "Old age";
            die();
            return;
        }
        boolean ateSomething = false;
        if (this instanceof Herbivore) {
            if (((Herbivore)this).tryToEatPlant()) {
                ateSomething = true;
            }
        }
        if (this instanceof Predator) {
            if (((Predator)this).tryToHunt()) {
                ateSomething = true;
            }
        }
        if (!ateSomething) {
            tryToFindTarget();
            tryToMove();
        }
        tryToReproduce();
    }

    protected void tryToFindTarget() {
        if (nearestTarget != null && nearestTarget.isAlive() && nearestTarget.getCurrentLocation() != null) {
            this.nearestTarget = findNearestTarget();
        } else {
            this.nearestTarget = findNearestTarget();
        }
    }

    public int getFoodValue() {
        return foodValue;
    }

    public void setFoodValue(int foodValue) {
        this.foodValue = foodValue;
    }

    protected void eat(Animal prey) {
        if (prey != null && prey.isAlive()) {
            prey.deathCause = "Eaten by " + this.getClass().getSimpleName();
            prey.die();
            this.satiety += prey.getFoodValue();
            System.out.println("Tick " + Simulation.currentTick + ": " + this.getClass().getSimpleName() + " (" + this.hashCode() + ") ate " + prey.getClass().getSimpleName() + ".");
        }
    }

    public void die() {
        if (!alive) return;
        alive = false;
        Location currentLocation = this.getCurrentLocation();
        if (currentLocation != null) {
            currentLocation.removeAnimal(this);
        }
        System.out.println("Tick " + Simulation.currentTick + ": " + this.getClass().getSimpleName() + " (" + this.hashCode() + ") died. Cause: " + (deathCause != null ? deathCause : "Unknown"));
    }

    public boolean isAlive() { // ДОЛЖЕН БЫТЬ PUBLIC
        return alive;
    }

    public Location getCurrentLocation() { // ДОЛЖЕН БЫТЬ PUBLIC
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) { // ДОЛЖЕН БЫТЬ PUBLIC
        this.currentLocation = currentLocation;
    }

    protected Location getMoveTargetLocation(Location targetLocation) {
        if (targetLocation == null || this.currentLocation == null) return null;
        int distanceToTarget = this.currentLocation.getDistance(targetLocation);
        if (distanceToTarget == 0) {
            return this.currentLocation;
        }
        Location bestMove = null;
        int minDistanceToTarget = Integer.MAX_VALUE;

        Location[] possibleMoves = this.currentLocation.getAdjacentLocations(getSpeed());

        for (Location loc : possibleMoves) {
            if (loc != null && loc.canAddEntity() && loc.equals(targetLocation)) {
                return loc;
            }
        }
        for (Location loc : possibleMoves) {
            if (loc != null && loc.canAddEntity()) {
                int currentDistanceToTarget = loc.getDistance(targetLocation);
                if (currentDistanceToTarget < minDistanceToTarget) {
                    minDistanceToTarget = currentDistanceToTarget;
                    bestMove = loc;
                }
            }
        }
        return bestMove;
    }

    protected Animal findNearestTarget() {
        if (this instanceof Herbivore) {
            return findNearestPlant(this.getCurrentLocation(), this.getCurrentLocation().getAdjacentLocations(1));
        }
        return null;
    }

    protected void tryToMove() {
        if (!this.alive || this.currentLocation == null) {
            return;
        }
        Location desiredMoveLocation = null;

        boolean targetIsStillValid = (this.nearestTarget != null && this.nearestTarget.isAlive() && this.nearestTarget.getCurrentLocation() != null);

        if (!targetIsStillValid) {
            Animal oldTarget = this.nearestTarget;
            this.nearestTarget = findNearestTarget();

            if (this.nearestTarget != null) {
            } else {
                if (oldTarget != null && oldTarget.isAlive()) {
                }
            }
        } else {
        }

        if (this.nearestTarget != null) {
            Location targetLocation = this.nearestTarget.getCurrentLocation();
            if (targetLocation != null) {
                desiredMoveLocation = getMoveTargetLocation(targetLocation);
                 if (desiredMoveLocation != null) {
                }
            }
        }

        if (desiredMoveLocation == null || desiredMoveLocation.equals(this.currentLocation)) {
            if (ThreadLocalRandom.current().nextInt(100) < this.movementChance) {
                Location randomLocation = getRandomAdjacentLocation(getSpeed());
                if (randomLocation != null) {
                    desiredMoveLocation = randomLocation;
                }
            } else {
                return;
            }
        }

        if (desiredMoveLocation != null && !desiredMoveLocation.equals(this.currentLocation)) {
            move(desiredMoveLocation);
        } else if (desiredMoveLocation != null && desiredMoveLocation.equals(this.currentLocation)) {
        }
    }

    public void tryToReproduce() {
        if (alive && satiety > foodRequiredPerTick * 2 && ThreadLocalRandom.current().nextInt(100) < reproductionChance) {
            Location currentLocation = this.getCurrentLocation();
            if (currentLocation != null) {
                Location[] adjacentLocations = currentLocation.getAdjacentLocations(1);
                for (Location loc : adjacentLocations) {
                    if (loc != null && loc.canAddEntity()) {
                        try {
                            Animal offspring = createNewOffspring();
                            if (offspring != null) {
                                loc.addAnimal(offspring);
                                offspring.initializeState();

                                System.out.println("Tick " + Simulation.currentTick + ": " +
                                        this.getClass().getSimpleName() + " (" + this.hashCode() + ") reproduced " + offspring.getClass().getSimpleName() + ".");
                                return;
                            }
                        } catch (Exception e) {
                            System.err.println("Error creating new offspring for " + this.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    protected Location getRandomAdjacentLocation(int speed) {
        if (currentLocation == null) return null;

        Location[] possibleMoves = currentLocation.getAdjacentLocations(speed);
        if (possibleMoves == null || possibleMoves.length == 0) return currentLocation;

        List<Location> validMoves = new ArrayList<>();
        for (Location loc : possibleMoves) {
            if (loc != null && loc.canAddEntity()) {
                validMoves.add(loc);
            }
        }

        if (validMoves.isEmpty()) {
            return currentLocation;
        }

        return validMoves.get(ThreadLocalRandom.current().nextInt(validMoves.size()));
    }

    public boolean move(Location newLocation) {
        if (newLocation == null || !newLocation.canAddEntity() || !this.alive) {
            return false;
        }

        if (this.currentLocation != null) {
            this.currentLocation.removeAnimal(this);
        }

        newLocation.addAnimal(this);
        this.setCurrentLocation(newLocation);

        return true;
    }

    public String getSymbol() {
        return AnimalSymbolMapper.getSymbolForClass(getAnimalClass());
    }
}