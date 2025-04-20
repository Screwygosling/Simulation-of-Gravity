package SolarSystemSimulation.src.com;

import java.awt.EventQueue;

public class Solarsys {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            gameFrame frame = new gameFrame();
            frame.setVisible(true);
        });
    }    
}
