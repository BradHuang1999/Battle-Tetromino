package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bradh on 1/18/2017.
 */
public class SoloGame extends SinglePlayerGame {
    public static void main(String[] args) {
        new SoloGame().setVisible(true);
    }

    public SoloGame() {
        super();

        setSize(680, 768);

        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyListener() {
                           @Override
                           public void keyTyped(KeyEvent e) {
                           }

                           @Override
                           public void keyPressed(KeyEvent e) {
                               switch (e.getKeyCode()) {
                                   case VK_UP:
                                       myGameBoard.rotate();
                                       break;
                                   case VK_DOWN:
                                       myGameBoard.moveDown();
                                       break;
                                   case VK_LEFT:
                                       myGameBoard.move(-1);
                                       break;
                                   case VK_RIGHT:
                                       myGameBoard.move(1);
                                       break;
                               }
                           }

                           @Override
                           public void keyReleased(KeyEvent e) {
                           }
                       }
        );

        new Thread(() -> {
            myGameBoard.newTetromino(new Tetromino());
            while (!gameOver) {
                while (playing) {
                    try {
                        myGameBoard.moveDown();
                        Thread.sleep(400);
                        if (myGameBoard.isAtBottom()) {
                            myGameBoard.solidifyTetromino();
                            myGameBoard.checkDisapperance();
                            myGameBoard.newTetromino(new Tetromino());
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        }, "player-thread").start();
    }
}