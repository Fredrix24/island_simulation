package com.simulation.world;

import java.util.Objects;

public abstract class WorldEntity {
    private final int id;
    private static int nextId = 0;

    public WorldEntity() {
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldEntity that = (WorldEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract String getSymbol();
    public abstract String getName();
}