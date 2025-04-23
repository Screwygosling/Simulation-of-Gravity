/*This is really barebones and so not accurate but this was fun to do
 * 
 * 
 * 
 */


package SolarSystemSimulation.src.com;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class gamePanel extends JPanel implements Runnable {
    
    private final int WIN_WIDTH = 1000;
    private final int WIN_HEIGHT = WIN_WIDTH;
    private final Dimension window = new Dimension(WIN_HEIGHT, WIN_WIDTH);

    private java.util.List<CelestialObject> celestialObjects = new java.util.ArrayList<>();

    int distance;
    int m1; 
    int m2;
    int positionX, positionY, Length, Width, velocityX, velocityY, Mass, Radius, id;
    String color;
    Thread thread;

    CelestialObject sun, earth, mars, Planet;

    gamePanel() {
        this.setPreferredSize(window);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new ActionListener());
        createObject();

        thread = new Thread(this);
        thread.start();
    }

    public void createObject() {
        sun = new CelestialObject((WIN_HEIGHT / 2), (WIN_WIDTH / 2), 0, 0, 1000, 30, 1);
        earth = new CelestialObject(sun.x - 150, sun.y, 0, -2.5, 60, 10, 2);
        mars = new CelestialObject(sun.x - 228, sun.y, 0, -2.0, 30, 8, 3);

        celestialObjects.add(sun);
        celestialObjects.add(earth);
        celestialObjects.add(mars);
    }

    public void createNewObject() {
        Planet = new CelestialObject((WIN_HEIGHT / 2), (WIN_WIDTH / 2), 0, 0, 80, 10, 4);
        celestialObjects.add(Planet);
    }

    public void applyGravity(CelestialObject a, CelestialObject b) {

        // newton's law of gravitational rotation
        double G = 2; //6.67430e-11 m^3 kg^-1 s^-2
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance == 0) {
            return;
        }
        double force = G * (a.Mass * b.Mass) / (distance * distance); 

        // this is acceleration
        double ax = (force * dx / distance) / a.Mass;
        double ay = (force * dy / distance) / a.Mass;

        // this updates the x and y positions of the celestial bodies
        a.vx += ax;
        a.vy += ay;
    }

    public void update() {

        // this for nested for loop iterates through the celestial object array list twice and applies the method applyGravity to each celestial body
        for (int i = 0; i < celestialObjects.size(); i++){
            for (int j = 0; j < celestialObjects.size(); j++) {
                if (i == j) continue;
                applyGravity(celestialObjects.get(i), celestialObjects.get(j));
            }
        }


        // this for loop moves the celestial bodies based on the applyGravity method above this 
        for (CelestialObject obj: celestialObjects) {
            obj.x += obj.vx;
            obj.y += obj.vy;
            obj.setLocation((int) obj.x, (int) obj.y);
            obj.updateTrail();
        }

    }

    public void run() {
        long lastTime = System.nanoTime();
        double targetFPS = 60.0;
        double ns = 1_000_000_000 / targetFPS;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (CelestialObject obj : celestialObjects) {
            obj.draw(g);
        }
    }

    public class ActionListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_W) {
                createNewObject();
            }
        }
    }
}
