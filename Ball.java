import java.awt.*;

public class Ball {
    int x, y, diameter = 20;
    int xSpeed = 3, ySpeed = 3;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;

        // Bounce off top and bottom walls
        if (y <= 0 || y >= PongGame.HEIGHT - diameter) {
            ySpeed = -ySpeed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }

    public void reverseX() {
        xSpeed = -xSpeed;
    }

    public void reverseY() {
        ySpeed = -ySpeed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public boolean isOutLeft() {
        return x <= 0;
    }

    public boolean isOutRight() {
        return x >= PongGame.WIDTH - diameter;
    }

    public void resetPosition() {
        x = PongGame.WIDTH / 2;
        y = PongGame.HEIGHT / 2;
        xSpeed = -xSpeed;
    }
}
