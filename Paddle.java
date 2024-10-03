import java.awt.*;

public class Paddle {
    int x, y, width = 20, height = 100;
    int speed = 5;
    boolean up = false, down = false;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        if (up && y > 0) {
            y -= speed;
        }
        if (down && y < PongGame.HEIGHT - height) {
            y += speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void increaseSize() {
        height += 30;
    }

    public void decreaseSize() {
        height = Math.max(height - 30, 50);
    }
}

