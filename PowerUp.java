import java.awt.*;
import java.util.Random;

public class PowerUp {
    int x, y, size = 20;
    Random random = new Random();
    boolean active = true;

    public PowerUp() {
        x = random.nextInt(PongGame.WIDTH - size);
        y = random.nextInt(PongGame.HEIGHT - size);
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, size, size);
        }
    }

    public void deactivate() {
        active = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
