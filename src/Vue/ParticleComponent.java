package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ParticleComponent extends JComponent {
    private ArrayList<Particle> particles;
    public ParticleComponent(int x, int y, int targetX, int targetY) {
        particles = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            int offsetX = (int) (Math.random() * 40 - 20);
            int offsetY = (int) (Math.random() * 40 - 20);
            double speed =  generateSpeed(5, 0.9); 
            addParticle(x + offsetX, y + offsetY, targetX, targetY, speed);
        }
    }

    private double generateSpeed(double averageSpeed, double speedVariation) {
        double variation = (Math.random() * 2 - 1) * speedVariation;
        return averageSpeed + variation;
    }

    public void addParticle(int x, int y, int targetX, int targetY, double speed) {
        particles.add(new Particle(x, y, targetX, targetY, speed));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        for (Particle particle : particles) {
            particle.draw(g2d);
        }

        g2d.dispose();
    }

    public int updateParticles(int targetX, int targetY) {
        particles.removeIf(particle -> particle.update(targetX, targetY) == 0);
        repaint();
        return particles.size();
    }
}
