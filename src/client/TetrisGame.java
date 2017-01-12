package client;

import client.queue.Queue;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class TetrisGame extends JFrame {
    private String mode;

    private boolean playing;
    private boolean rewardMode;
    private boolean gameOver = false;

    private boolean doublePlayer;

    private int level = 0;

    private GameBoard myGameBoard;
    private GameBoard opponentGameBoard;

    private Queue<Tetromino> myTetrominoQueue;
    private Queue<Tetromino> opponentTetrominoQueue;

    public TetrisGame(String mode) {
        this.mode = mode;

        myGameBoard = new GameBoard();
        doublePlayer = mode.equals("Human vs AI") || mode.equals("Battle");
        if (doublePlayer) {
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
        });

        new Thread(()->{
            playing = true;
            rewardMode = false;

            while (!gameOver) {
                while (playing) {
                    // TODO play game algorithm
                }
                while (rewardMode){
                    // TODO reward mode
                }
            }
        }, "player-thread").start();

        if (doublePlayer) {
            new Thread(() -> {
                playing = true;
                rewardMode = false;

                while (!gameOver) {
                    // TODO receive stuff from server
                }
            }, "opponent-thread").start();
        }
    }
}