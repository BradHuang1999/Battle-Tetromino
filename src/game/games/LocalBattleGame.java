package game.games;

import game.TransparentPanel;
import game.gameboards.DoublePlayerGameBoard;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import static java.awt.event.KeyEvent.*;

/**
 * Created by Brad Huang on 1/20/2017.
 */
public class LocalBattleGame extends DoublePlayerGame{
    public LocalBattleGame(String nickName, String iconPath, PrintWriter output){
        super(nickName, iconPath);

        this.output = output;

        myGameBoard = new DoublePlayerGameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        opponentGameBoard = new DoublePlayerGameBoard();
        opponentGameBoard.setBounds(170 + 680, 155, 338, 546);
        getContentPane().add(opponentGameBoard);

        opponentIcon.setText(nickName);
        opponentIcon.setIcon(new ImageIcon(iconPath));

        this.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){

            }

            @Override
            public void keyPressed(KeyEvent e){
                if (!(ready && opponentReady)){
                    switch (e.getKeyCode()){
                        case VK_Q:
                            ready = true;
                            break;
                        case VK_M:
                            opponentReady = true;
                            break;
                    }
                }

                if (playing){
                    switch (e.getKeyCode()){
                        case VK_UP:
                            opponentGameBoard.rotate();
                            break;
                        case VK_W:
                            myGameBoard.rotate();
                            break;
                        case VK_DOWN:
                            opponentGameBoard.moveDown();
                            opponentScore++;
                            break;
                        case VK_S:
                            myGameBoard.moveDown();
                            myScore++;
                            break;
                        case VK_LEFT:
                            opponentGameBoard.move(-1);
                            break;
                        case VK_A:
                            myGameBoard.move(-1);
                            break;
                        case VK_RIGHT:
                            opponentGameBoard.move(1);
                            break;
                        case VK_D:
                            myGameBoard.move(1);
                            break;
                        case VK_N:
                            opponentGameBoard.holdSwitch = true;
                            break;
                        case VK_SHIFT:
                            myGameBoard.holdSwitch = true;
                            break;
                        case VK_SPACE:
                            opponentScore += opponentGameBoard.moveToBottomUp2();
                            break;
                        case VK_CONTROL:
                            myScore += myGameBoard.moveToBottomUp2();
                            break;
                    }
                }

                if (rewardMode){
                    switch (e.getKeyCode()){
                        case VK_K:
                            if (!iWonLevel){
                                ((DoublePlayerGameBoard)opponentGameBoard).gravityDrop();
                                rewardMode = false;
                                playing = true;
                            }
                            break;
                        case VK_Z:
                            if (iWonLevel){
                                ((DoublePlayerGameBoard)myGameBoard).gravityDrop();
                                rewardMode = false;
                                playing = true;
                            }
                            break;
                        case VK_L:
                            if (!iWonLevel){
                                new Thread(() -> {
                                    ((DoublePlayerGameBoard)myGameBoard).deployRewardPiece();
                                    requestFocus();
                                    rewardMode = false;
                                    setupCountDownScreen();
                                    playing = true;
                                }).start();
                            }
                            break;
                        case VK_X:
                            if (iWonLevel){
                                new Thread(() -> {
                                    ((DoublePlayerGameBoard)opponentGameBoard).deployRewardPiece();
                                    requestFocus();
                                    rewardMode = false;
                                    setupCountDownScreen();
                                    playing = true;
                                }).start();
                            }
                            break;
                    }
                } else {
                    if (e.getKeyCode() == VK_P){
                        playing = !playing;
                        if (playing){
                            disposePauseScreen();
                        } else {
                            setupPauseScreen();
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e){

            }
        });
    }

    @Override
    public void setupMyReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Ready?");
        waiting.addSmallMessage("Press Q to continue");

        while (!ready){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        getLayeredPane().remove(waiting);
        repaint();
        revalidate();
    }

    @Override
    public void setupOpponentReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(680, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Ready?");
        waiting.addSmallMessage("Press M to continue");

        while (!opponentReady){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        getLayeredPane().remove(waiting);
        repaint();
        revalidate();
    }

    @Override
    public void setupMyGameOverScreen(){
        String message = opponentGameOver ? "You Won!!!" : "You Lost...";

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        waiting.gameOver(message, myScore, myLines, level, opponentGameOver,
                new String[][]{
                        {"AI", "1370582"},
                        {"AI", "672806"},
                        {"AI", "589043"},
                        {"AI", "351792"},
                        {"AI", "310589"}});
        getLayeredPane().add(waiting, 30);

        if (opponentGameOver){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            waiting.requestFocus();
            waiting.addKeyListener(new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e){
                }

                @Override
                public void keyPressed(KeyEvent e){
                }

                @Override
                public void keyReleased(KeyEvent e){
                    gameOver = true;
                    dispose();
                    output.println("**leaveRoom\n" + gameName);
                    output.flush();
                }
            });
        }

        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setupOpponentGameOverScreen(){
        String message = gameOver ? "You Won!!!" : "You Lost...";

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(680, 0, 680, 768);
        waiting.gameOver(message, opponentScore, opponentLines, level, gameOver,
                new String[][]{
                        {"aaa", "5000"},
                        {"bbb", "4000"},
                        {"ccc", "3000"},
                        {"ddd", "2000"},
                        {"eee", "1000"}});
        getLayeredPane().add(waiting, 30);

        if (gameOver){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            waiting.requestFocus();
            waiting.addKeyListener(new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e){
                }

                @Override
                public void keyPressed(KeyEvent e){
                }

                @Override
                public void keyReleased(KeyEvent e){
                    gameOver = true;
                    dispose();
                    output.println("**leaveRoom\n" + gameName);
                    output.flush();
                }
            });
        }

        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setupRewardModeScreen(int myLevelScore, int opponentLevelScore){
        iWonLevel = (myLevelScore - opponentLevelScore) > 0;

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(iWonLevel ? 0 : 680, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Reward");
        waiting.addSmallMessage("<html><p align=\"center\">Your score in this level: " + myLevelScore +
                "<br>Opponent's score in this level: " + opponentLevelScore +
                "<br>Press " + (iWonLevel ? "Z" : "K") + " for gravity drop, or" +
                "<br>Press " + (iWonLevel ? "X" : "L") + " to put extra pieces on opponent's board.<p></html>");

        repaint();

        while (rewardMode){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        getLayeredPane().remove(waiting);
        repaint();
        revalidate();
    }
}