package com.simulation.gui;

import com.simulation.AnimalColorMapper;
import com.simulation.Island;
import com.simulation.Location;
import com.simulation.config.SimulationParameters;
import com.simulation.world.Animal;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class IslandGUI extends JPanel {
    private Island island;
    private int tick;
    private final Map<Class<? extends Animal>, String> animalSymbols;

    public IslandGUI(Island island, Map<Class<? extends Animal>, String> animalSymbols) {
        this.island = island;
        this.animalSymbols = animalSymbols;
        this.setPreferredSize(new Dimension(
                SimulationParameters.ISLAND_WIDTH * 20,
                SimulationParameters.ISLAND_HEIGHT * 20
        ));
        this.setBackground(Color.BLACK);
    }

    public void updateState(Island island, int tick) {
        this.island = island;
        this.tick = tick;
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int islandWidth = island.getWidth();
        int islandHeight = island.getHeight();

        int cellWidth = getWidth() / islandWidth;
        int cellHeight = getHeight() / islandHeight;

        for (int i = 0; i < islandWidth; i++) {
            for (int j = 0; j < islandHeight; j++) {
                Location loc = island.getLocation(i, j);
                drawCell(g2d, loc, i * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    private void drawCell(Graphics2D g2d, Location loc, int x, int y, int w, int h) {
        if (loc.getPlantCount() > 0) {
            g2d.setColor(new Color(0, 150, 0));
        } else {
            g2d.setColor(new Color(100, 200, 100));
        }
        g2d.fillRect(x, y, w, h);

        List<Animal> animals = loc.getAnimals();
        if (!animals.isEmpty()) {
            Animal primaryAnimal = animals.get(0);
            String symbol = animalSymbols.getOrDefault(primaryAnimal.getClass(), "?");

            g2d.setColor(AnimalColorMapper.getColor(primaryAnimal.getClass()));

            int fontSize = Math.max(10, Math.min(w, h) - 4);
            g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));

            FontMetrics fm = g2d.getFontMetrics();
            int symbolWidth = fm.stringWidth(symbol);
            int symbolHeight = fm.getAscent();

            g2d.drawString(symbol,
                    x + (w - symbolWidth) / 2,
                    y + (h + symbolHeight) / 2 - fm.getDescent());
        }

        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(x, y, w, h);
    }
}