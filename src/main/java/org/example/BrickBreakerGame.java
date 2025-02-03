package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener {


            private static final int WIDTH = 600;
            private static final int HEIGHT = 400;

            private int topX = 300, topY = 350;
            private int topDX = 2, ballDY = -2;
            private int padX = 250;
            private final int PAD_WIDTH = 100, PAD_HEIGHT = 15;
            private boolean left = false, right = false;

            private boolean[][] bricks;
            private final int BRICK_WIDTH = 60, BRICK_HEIGHT = 20;
            private final int ROWS = 5, COLS = 10;

            private Timer timer;

            public BrickBreakerGame() {
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                this.setBackground(Color.BLACK);
                this.addKeyListener(this);
                this.setFocusable(true);


                bricks = new boolean[ROWS][COLS];
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        bricks[i][j] = true;
                    }
                }


                timer = new Timer(10, this);
                timer.start();
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);


                g.setColor(Color.RED);
                g.fillOval(topX, topY, 15, 15); // To'pni chizish


                g.setColor(Color.BLUE);
                g.fillRect(padX, HEIGHT - PAD_HEIGHT - 10, PAD_WIDTH, PAD_HEIGHT); // Padni chizish


                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (bricks[i][j]) {
                            g.setColor(Color.GREEN);
                            g.fillRect(j * BRICK_WIDTH + 50, i * BRICK_HEIGHT + 50, BRICK_WIDTH, BRICK_HEIGHT); // Briklarni chizish
                        }
                    }
                }


                if (isGameOver()) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
                }


                if (isWon()) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString("You Win!", WIDTH / 2 - 100, HEIGHT / 2);
                }
            }


            @Override
            public void actionPerformed(ActionEvent e) {

                if (!isGameOver() && !isWon()) {
                    moveBall();
                    checkCollisions();
                    movePad();
                }
                repaint();
            }


            private void moveBall() {
                topX += topDX;
                topY += ballDY;


                if (topX < 0 || topX > WIDTH - 15) {
                    topDX = -topDX;
                }
                if (topY < 0) {
                    ballDY = -ballDY;
                }


                if (topY + 15 >= HEIGHT - PAD_HEIGHT - 10 && topX >= padX && topX <= padX + PAD_WIDTH) {
                    ballDY = -ballDY;
                }


                if (topY > HEIGHT) {
                    ballDY = 0;
                    topDX = 0;
                }
            }


            private void movePad() {
                if (left && padX > 0) {
                    padX -= 5;
                }
                if (right && padX < WIDTH - PAD_WIDTH) {
                    padX += 5;
                }
            }


            private void checkCollisions() {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (bricks[i][j]) {
                            int brickX = j * BRICK_WIDTH + 50;
                            int brickY = i * BRICK_HEIGHT + 50;

                            if (topX + 15 > brickX && topX < brickX + BRICK_WIDTH && topY + 15 > brickY && topY < brickY + BRICK_HEIGHT) {
                                bricks[i][j] = false;
                                ballDY = -ballDY;
                            }
                        }
                    }
                }
            }


            private boolean isGameOver() {
                return topY > HEIGHT;
            }


            private boolean isWon() {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (bricks[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            }


            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    left = true;
                } else if (key == KeyEvent.VK_RIGHT) {
                    right = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    left = false;
                } else if (key == KeyEvent.VK_RIGHT) {
                    right = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            public static void main(String[] args) {
                JFrame frame = new JFrame("Brick Breaker");
                BrickBreakerGame gamePanel = new BrickBreakerGame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(gamePanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);


    }
}