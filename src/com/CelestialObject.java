package SolarSystemSimulation.src.com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

public class CelestialObject extends Rectangle{
    
    double vx, vy, Mass, Radius;
    int id;

    private LinkedList<Point> trail = new LinkedList<>();
    private final int MAX_TRAIL_SIZE = 300;
    
    CelestialObject(int x,int y, double vx, double vy, double Mass, double Radius, int id) {
        super((int) x,(int) y,(int) Radius *2,(int) Radius * 2);
        this.vx = vx;
        this.vy = vy;
        this.Mass = Mass;
        this.Radius = Radius; 
        this.id = id;
    }

    public void updateTrail() {
        trail.add(new Point((int)x, (int)y));
        if (trail.size() > MAX_TRAIL_SIZE) {
            trail.removeFirst();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        for (Point p : trail) {
            g.fillRect(p.x, p.y, 1, 1);
        }
        
        switch(id){
            case 1:
                g.setColor(Color.YELLOW);
                break;
            case 2:
                g.setColor(Color.BLUE);
                break;
            case 3:
                g.setColor(Color.RED);
                break;
            case 4:
                g.setColor(Color.GREEN);
                break;
        }
        g.fillOval(x, y, width, height);
    }
}
