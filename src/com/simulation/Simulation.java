package com.simulation;

import com.simulation.config.SimulationParameters;
import com.simulation.gui.IslandGUI;
import com.simulation.world.Animal;
import com.simulation.world.AnimalSymbolMapper;
import com.simulation.Simulation;
import com.simulation.world.specific.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.*;

public class Simulation {
    private final Island island;
    private ScheduledExecutorService scheduledPool;
    private ExecutorService animalActionPool;
    public static int currentTick = 0;
    private boolean simulationRunning = true;
    private boolean isPaused = false;
    private IslandGUI islandGUI;
    private JFrame mainFrame;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton stopButton;
    private JLabel tickStatusLabel;
    private JLabel speedLabel;
    private JLabel totalAnimalsLabel;
    private JLabel totalPlantsLabel;

    private JPanel animalStatsPanel;
    private Map<Class<? extends Animal>, JLabel> individualAnimalLabels;

    private final int MAX_FRAME_WIDTH = 1000;
    private final int MAX_FRAME_HEIGHT = 800;

    public Simulation() {
        this.island = Island.getInstance(SimulationParameters.ISLAND_WIDTH, SimulationParameters.ISLAND_HEIGHT);
        initializeIsland();
        initializeGUI();
    }

    private void initializeIsland() {
        for (int i = 0; i < SimulationParameters.INITIAL_PLANTS; i++) {
            int x = ThreadLocalRandom.current().nextInt(island.getWidth());
            int y = ThreadLocalRandom.current().nextInt(island.getHeight());
            island.getLocation(x, y).addPlant();
        }
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Horse.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Deer.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Rabbit.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Mouse.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Goat.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Sheep.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Boar.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Buffalo.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Duck.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Caterpillar.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Wolf.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Boa.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Fox.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Bear.class);
        addAnimalsToIsland(SimulationParameters.INITIAL_ANIMALS_PER_SPECIES, Eagle.class);
    }

    private void addAnimalsToIsland(int count, Class<? extends Animal> animalClass) {
        for (int i = 0; i < count; i++) {
            int x = ThreadLocalRandom.current().nextInt(island.getWidth());
            int y = ThreadLocalRandom.current().nextInt(island.getHeight());
            try {
                Animal animal = animalClass.getConstructor().newInstance();

                animal.initializeState();
                island.addAnimalToIsland(animal, x, y);
            } catch (Exception e) {
                System.err.println("Error creating instance of " + animalClass.getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void initializeGUI() {
        Map<Class<? extends Animal>, String> symbols = AnimalSymbolMapper.getSymbols();

        SwingUtilities.invokeLater(() -> {
            mainFrame = new JFrame("Island Simulation");
            islandGUI = new IslandGUI(island, symbols);

            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pauseButton = new JButton("Pause");
            resumeButton = new JButton("Resume");
            stopButton = new JButton("Stop");
            tickStatusLabel = new JLabel("Tick: 0 | Status: Idle");
            speedLabel = new JLabel("Speed: " + SimulationParameters.TICK_DURATION_MS + "ms");
            totalAnimalsLabel = new JLabel("Animals: 0");
            totalPlantsLabel = new JLabel("Plants: 0");

            controlPanel.add(pauseButton);
            controlPanel.add(resumeButton);
            controlPanel.add(stopButton);
            controlPanel.add(Box.createHorizontalStrut(20));
            controlPanel.add(tickStatusLabel);
            controlPanel.add(Box.createHorizontalStrut(20));
            controlPanel.add(speedLabel);
            controlPanel.add(Box.createHorizontalStrut(20));
            controlPanel.add(totalAnimalsLabel);
            controlPanel.add(Box.createHorizontalStrut(10));
            controlPanel.add(totalPlantsLabel);

            pauseButton.addActionListener(e -> pauseSimulation());
            resumeButton.addActionListener(e -> resumeSimulation());
            stopButton.addActionListener(e -> stopSimulation());

            animalStatsPanel = new JPanel();
            animalStatsPanel.setLayout(new BoxLayout(animalStatsPanel, BoxLayout.Y_AXIS));
            animalStatsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            individualAnimalLabels = new HashMap<>();

            List<Class<? extends Animal>> allPossibleAnimalTypes = new ArrayList<>();
            allPossibleAnimalTypes.add(Bear.class); allPossibleAnimalTypes.add(Boa.class);
            allPossibleAnimalTypes.add(Boar.class); allPossibleAnimalTypes.add(Buffalo.class);
            allPossibleAnimalTypes.add(Caterpillar.class); allPossibleAnimalTypes.add(Deer.class);
            allPossibleAnimalTypes.add(Duck.class); allPossibleAnimalTypes.add(Eagle.class);
            allPossibleAnimalTypes.add(Fox.class); allPossibleAnimalTypes.add(Goat.class);
            allPossibleAnimalTypes.add(Horse.class); allPossibleAnimalTypes.add(Mouse.class);
            allPossibleAnimalTypes.add(Rabbit.class); allPossibleAnimalTypes.add(Sheep.class);
            allPossibleAnimalTypes.add(Wolf.class);

            allPossibleAnimalTypes.sort( (c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName()) );

            for (Class<? extends Animal> animalType : allPossibleAnimalTypes) {
                JLabel label = new JLabel(formatAnimalStat(animalType, 0));
                label.setFont(new Font("Monospaced", Font.PLAIN, 12));
                animalStatsPanel.add(label);
                individualAnimalLabels.put(animalType, label);
            }

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(controlPanel, BorderLayout.NORTH);
            mainPanel.add(islandGUI, BorderLayout.CENTER);
            mainPanel.add(animalStatsPanel, BorderLayout.EAST);

            mainFrame.add(mainPanel);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

            updateUIState();
            updateStatusLabel();

            startSimulation();
        });
    }

    private void updateUIState() {
        pauseButton.setEnabled(!isPaused && simulationRunning);
        resumeButton.setEnabled(isPaused && simulationRunning);
        stopButton.setEnabled(simulationRunning);
    }

    private void startSimulation() {
        createPools();

        scheduledPool.scheduleWithFixedDelay(this::growPlants, 0, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        scheduledPool.scheduleWithFixedDelay(this::animalLifeCycle, 0, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        scheduledPool.scheduleWithFixedDelay(this::checkSimulationEndConditions, SimulationParameters.TICK_DURATION_MS, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
        scheduledPool.scheduleWithFixedDelay(this::updateGui, SimulationParameters.TICK_DURATION_MS, SimulationParameters.TICK_DURATION_MS, TimeUnit.MILLISECONDS);

        simulationRunning = true;
        isPaused = false;
        updateUIState();
        updateStatusLabel();
    }

    private void createPools() {
        scheduledPool = Executors.newScheduledThreadPool(4);
        animalActionPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    private void destroyPools() {
        if (scheduledPool != null) scheduledPool.shutdown();
        if (animalActionPool != null) animalActionPool.shutdown();
        try {
            if (scheduledPool != null && !scheduledPool.awaitTermination(1, TimeUnit.SECONDS)) scheduledPool.shutdownNow();
            if (animalActionPool != null && !animalActionPool.awaitTermination(1, TimeUnit.SECONDS)) animalActionPool.shutdownNow();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (scheduledPool != null) scheduledPool.shutdownNow();
            if (animalActionPool != null) animalActionPool.shutdownNow();
        }
    }

    private void pauseSimulation() {
        if (simulationRunning && !isPaused) {
            isPaused = true;
            destroyPools();
            updateUIState();
            updateStatusLabel();
        }
    }

    private void resumeSimulation() {
        if (simulationRunning && isPaused) {
            isPaused = false;
            startSimulation();
            updateUIState();
            updateStatusLabel();
        }
    }

    public void stopSimulation() {
        if (simulationRunning) {
            simulationRunning = false;
            isPaused = false;
            destroyPools();

            SwingUtilities.invokeLater(() -> {
                if (islandGUI != null) {
                    islandGUI.updateState(island, currentTick);
                }
                if (mainFrame != null) {
                    mainFrame.dispose();
                }
            });
            System.out.println("Simulation stopped after " + currentTick + " ticks.");
        }
    }

    private void growPlants() {
        if (!simulationRunning || isPaused) return;
        for (int i = 0; i < island.getWidth(); i++) {
            for (int j = 0; j < island.getHeight(); j++) {
                Location location = island.getLocation(i, j);
                if (ThreadLocalRandom.current().nextInt(100) < SimulationParameters.getPlantGrowthRate()) {
                    location.addPlant();
                }
            }
        }
    }

    private void animalLifeCycle() {
        if (!simulationRunning || isPaused) return;

        List<Animal> allAnimals = new CopyOnWriteArrayList<>(island.getAllAnimals());

        if (allAnimals.isEmpty()) {
            checkSimulationEndConditions();
            return;
        }

        List<Future<?>> futures = new ArrayList<>();

        for (Animal animal : allAnimals) {
            if (animal.isAlive()) {
                futures.add(animalActionPool.submit(() -> {
                    try {
                        animal.liveTick();
                    } catch (Exception e) {
                        System.err.println("Error in liveTick for " + animal.getClass().getSimpleName() + ": " + e.getMessage());
                        e.printStackTrace();
                        animal.die();
                    }
                }));
            }
        }

        island.cleanUpDeadAnimals();

        currentTick++;
        checkSimulationEndConditions();
        updateStatusLabel();
    }

    private void updateGui() {
        if (!simulationRunning || isPaused) return;

        SwingUtilities.invokeLater(() -> {
            islandGUI.updateState(island, currentTick);
            totalAnimalsLabel.setText("Animals: " + island.getTotalAnimalsCount());
            totalPlantsLabel.setText("Plants: " + island.getTotalPlantsCount());

            updateAnimalStatsPanel();
        });
    }

    private void updateAnimalStatsPanel() {
        Map<Class<? extends Animal>, Integer> counts = island.getAnimalCountsByType();

        for (Map.Entry<Class<? extends Animal>, JLabel> entry : individualAnimalLabels.entrySet()) {
            Class<? extends Animal> animalType = entry.getKey();
            JLabel label = entry.getValue();
            int count = counts.getOrDefault(animalType, 0);
            label.setText(formatAnimalStat(animalType, count));
        }
    }

    private String formatAnimalStat(Class<? extends Animal> animalClass, int count) {
        String symbol = AnimalSymbolMapper.getSymbolForClass(animalClass);
        String russianName = AnimalNameMapper.getName(animalClass);

        int rgb = AnimalColorMapper.getColor(animalClass).getRGB();
        String hexColor = String.format("#%06x", rgb & 0xFFFFFF);

        return String.format("<html><font color='%s'>%s</font> %-15s: %5d</html>",
                hexColor, symbol, russianName, count);
    }

    private void checkSimulationEndConditions() {
        if (!simulationRunning || isPaused) return;

        boolean allAnimalsDead = island.getAllAnimals().isEmpty();
        boolean noPlantsLeft = island.getTotalPlantsCount() == 0;

        if (SimulationParameters.STOP_IF_ALL_ANIMALS_DIE && allAnimalsDead) {
            System.out.println("\nSimulation stopped: All animals have died.");
            stopSimulation();
        } else if (SimulationParameters.STOP_IF_NO_PLANTS_LEFT && noPlantsLeft) {
            System.out.println("\nSimulation stopped: No plants left on the island.");
            stopSimulation();
        }
    }

    private void updateStatusLabel() {
        String status = isPaused ? "Paused" : (simulationRunning ? "Running" : "Stopped");
        SwingUtilities.invokeLater(() -> {
            tickStatusLabel.setText("Tick: " + currentTick + " | Status: " + status);
            speedLabel.setText("Speed: " + SimulationParameters.TICK_DURATION_MS + "ms");
        });
    }
}