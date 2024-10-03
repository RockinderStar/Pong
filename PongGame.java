import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    static final int WIDTH = 800, HEIGHT = 600;
    Paddle player1, player2;
    ArrayList<Ball> balls = new ArrayList<>();
    Timer timer;
    PowerUp powerUp;  // Handles the power-up mechanic
    Random random = new Random();
    int score1 = 0, score2 = 0;  // Player scores
    boolean gameStarted = false;  // Track if the game has started

    public PongGame() {
        player1 = new Paddle(50, HEIGHT / 2 - 50);
        player2 = new Paddle(WIDTH - 70, HEIGHT / 2 - 50);
        balls.add(new Ball(WIDTH / 2, HEIGHT / 2));
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (!gameStarted) {
            drawRules(g);  // Show rules before game starts
        } else {
            setBackground(Color.BLACK);

            // Draw paddles
            player1.draw(g);
            player2.draw(g);

            // Draw balls
            for (Ball ball : balls) {
                ball.draw(g);
            }

            // Draw power-up
            if (powerUp != null && powerUp.active) {
                powerUp.draw(g);
            }

            // Draw scores
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            g.drawString("Player 1: " + score1, 50, 30);
            g.drawString("Player 2: " + score2, WIDTH - 200, 30);
        }
    }

    // Display the rules before the game starts
    private void drawRules(Graphics g) {
        setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));

        g.drawString("Welcome to Pong with Power-Ups!", 180, 200);
        g.drawString("Rules:", 350, 260);
        g.drawString("1. Control Player 1 with W/S keys", 200, 300);
        g.drawString("2. Control Player 2 with UP/DOWN keys", 200, 340);
        g.drawString("3. Power-ups randomly appear during the game", 150, 380);
        g.drawString("Press any key to start!", 280, 500);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {  // Only update game logic if the game has started
            player1.move();
            player2.move();

            for (Ball ball : balls) {
                ball.move();

                // Check collision with paddles
                if (ball.getBounds().intersects(player1.getBounds()) || ball.getBounds().intersects(player2.getBounds())) {
                    ball.reverseX();
                }

                // Check if ball goes off-screen
                if (ball.isOutLeft()) {
                    score2++;
                    ball.resetPosition();
                    repositionPowerUp();  // Reposition power-up on score
                } else if (ball.isOutRight()) {
                    score1++;
                    ball.resetPosition();
                    repositionPowerUp();  // Reposition power-up on score
                }

                // Check if the ball intersects with the power-up
                if (powerUp != null && powerUp.active) {
                    if (ball.getBounds().intersects(powerUp.getBounds())) {
                        applyPowerUp(ball);  // Apply power-up based on ball position
                        powerUp.deactivate();
                    }
                }
            }

            // Spawn power-up randomly if no active power-up is on the field
            if (powerUp == null || !powerUp.active) {
                if (random.nextInt(1000) < 5) {  // 0.5% chance of spawning each frame
                    powerUp = new PowerUp();
                }
            }

            repaint();
        }
    }

    private void repositionPowerUp() {
        // Reposition the power-up to a new random location after a point is scored
        powerUp = new PowerUp();
    }

    private void applyPowerUp(Ball ball) {
        int effect = random.nextInt(3);  // Random power-up effect

        // Check which paddle the ball is closest to when the power-up is activated
        if (ball.x < WIDTH / 2) {
            // Ball is on the left side, so apply power-up to Player 1
            switch (effect) {
                case 0:
                    player1.increaseSize();  // Increase paddle size for Player 1
                    break;
                case 1:
                    player1.decreaseSize();  // Decrease paddle size for Player 1
                    break;
                case 2:
                    balls.add(new Ball(WIDTH / 2, HEIGHT / 2));  // Add an extra ball
                    break;
            }
        } else {
            // Ball is on the right side, so apply power-up to Player 2
            switch (effect) {
                case 0:
                    player2.increaseSize();  // Increase paddle size for Player 2
                    break;
                case 1:
                    player2.decreaseSize();  // Decrease paddle size for Player 2
                    break;
                case 2:
                    balls.add(new Ball(WIDTH / 2, HEIGHT / 2));  // Add an extra ball
                    break;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!gameStarted) {
            gameStarted = true;  // Start the game when a key is pressed
        }

        if (e.getKeyCode() == KeyEvent.VK_W) player1.up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) player1.down = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) player2.up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player2.down = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) player1.up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) player1.down = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) player2.up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player2.down = false;
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game with Power-Ups and Scoring!");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}




