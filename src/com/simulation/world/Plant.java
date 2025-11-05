package com.simulation.world;

import com.simulation.config.SimulationParameters;

public class Plant extends WorldEntity {
    private final int energyValue;
    private final int growthRate;
    private final int maxGrowth;

    public Plant() {
        this.energyValue = SimulationParameters.getPlantEnergyValue();
        this.growthRate = SimulationParameters.getPlantGrowthRate();
        this.maxGrowth = SimulationParameters.getPlantMaxGrowth();
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public int getGrowthRate() {
        return growthRate;
    }

    public int getMaxGrowth() {
        return maxGrowth;
    }

    @Override
    public String getSymbol() {
        return "🌿";
    }

    @Override
    public String getName() {
        return "Plant";
    }
}