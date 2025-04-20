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

import javax.swing.*;

public class gamePanel extends JPanel implements Runnable {
    
    private final int WIN_WIDTH = 1000;
    private final int WIN_HEIGHT = WIN_WIDTH;
    private final Dimension window = new Dimension(WIN_HEIGHT, WIN_WIDTH);

    int distance;
    int m1; 
    int m2;
    int positionX, positionY, Length, Width, velocityX, velocityY, Mass, Radius, id;
    String color;
    Thread thread;

    CelestialObject sun, earth, mars;

    gamePanel() {
        this.setPreferredSize(window);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        createObject();

        thread = new Thread(this);
        thread.start();
    }

    public void createObject() {
        sun = new CelestialObject((WIN_HEIGHT / 2), (WIN_WIDTH / 2), 0, 0, 1000, 20, 1);
        earth = new CelestialObject((WIN_HEIGHT / 2) - (sun.x - 150), sun.y, 0, 2.5, 10, 10, 2);
        mars = new CelestialObject((WIN_HEIGHT / 2) + (sun.x - 200), sun.y, 0, 10.0, 5, 8, 3);
    }

    public void applyGravity(CelestialObject a, CelestialObject b) {

        // newton's law of gravitational rotation

        //this is for the sun and earth
        double G = 1; //6.67430e-11 m^3 kg^-1 s^-2
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

        CelestialObject[] celestialObjects = {sun, earth, mars};

        // this for nested for loop iterates through the celestial object array list twice and applies the method applyGravity to each celestial body
        for (int i = 0; i < celestialObjects.length; i++){
            for (int j = 0; j < celestialObjects.length; j++) {
                if (i == j) continue;
                applyGravity(celestialObjects[i], celestialObjects[j]);
            }
        }


        // this for loop moves the celestial bodies based on the applyGravity method above this
        for (CelestialObject obj: celestialObjects) {
            obj.x += obj.vx;
            obj.y += obj.vy;
            obj.setLocation((int) obj.x, (int) obj.y);
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
        sun.draw(g);
        earth.draw(g);
        mars.draw(g);
    }
}
