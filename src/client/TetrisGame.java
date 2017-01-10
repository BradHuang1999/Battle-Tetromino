package client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class TetrisGame extends JFrame {
    private String mode;

    private boolean playing;
    private boolean rewardMode;

    private GameBoard myGameBoard;
    private GameBoard opponentGameBoard;

    public TetrisGame(String mode) {
        this.mode = mode;

        myGameBoard = new GameBoard();
        if (mode.equals("Human vs AI") || mode.equals("Battle")) {
            opponentGameBoard = new GameBoard();
        }

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case VK_UP:
                        myGameBoard.rotate();
                        break;
                    case VK_DOWN:
                        myGameBoard.moveDown();
                        break;
                    case VK_LEFT:
                        myGameBoard.moveLeft();
                        break;
                    case VK_RIGHT:
                        myGameBoard.moveRight();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });


    }
}