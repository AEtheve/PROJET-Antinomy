package Vue;

import java.awt.*;
import java.util.Random;

public class Particle {
    private int x;
    private int y;
    private int targetX;
    private int targetY;
    private double speed;
    private Color color;
    private Random random;

    public Particle(int x, int y, int targetX, int targetY, double speed) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
        color = new Color(0x7F00FF);
        random = new Random();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(x, y, 4, 4);
    }

    public int update(int targetX, int targetY) {
        setTarget(targetX, targetY);
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            double ratio = speed / distance;
            double vx = dx * ratio;
            double vy = dy * ratio;

            double variation = random.nextDouble() * Math.PI / 4 - Math.PI / 8;
            double newVx = vx * Math.cos(variation) - vy * Math.sin(variation);
            double newVy = vx * Math.sin(variation) + vy * Math.cos(variation);

            x += newVx;
            y += newVy;
            return 1;
        } else {
            return 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTarget(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
