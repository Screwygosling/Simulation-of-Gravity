package SolarSystemSimulation.src.com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CelestialObject extends Rectangle{
    
    double vx, vy, Mass, Radius;
    int id;
    
    CelestialObject(int x,int y, double vx, double vy, double Mass, double Radius, int id) {
        super((int) x,(int) y,(int) Radius *2,(int) Radius * 2);
        this.vx = vx;
        this.vy = vy;
        this.Mass = Mass;
        this.Radius = Radius; 
        this.id = id;
    }

    public void draw(Graphics g) {
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
        }
        g.fillOval(x, y, width, height);
    }
}
