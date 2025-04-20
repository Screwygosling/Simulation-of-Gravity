package SolarSystemSimulation.src.com;
import java.awt.Color;

import javax.swing.*;

public class gameFrame extends JFrame {
    gamePanel panel = new gamePanel();
    gameFrame() {
        this.add(panel);
        this.setTitle("Solar System Simulation");
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
