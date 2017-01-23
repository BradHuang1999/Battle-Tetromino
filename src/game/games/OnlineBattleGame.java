package game.games;

import game.Tetromino;
import game.TransparentPanel;
import game.gameboards.DoublePlayerGameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bradh on 1/18/2017.
 */
public class OnlineBattleGame extends DoublePlayerGame{
    public OnlineBattleGame(String nickName, String iconPath, PrintWriter output){
        super(nickName, iconPath);

        this.output = output;

        myGameBoard = new DoublePlayerGameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        opponentGameBoard = new DoublePlayerGameBoard();
        opponentGameBoard.setBounds(170 + 680, 155, 338, 546);
        getContentPane().add(opponentGameBoard);

        addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){

            }

            @Override
            public void keyPressed(KeyEvent e){
                if (!(ready)){
                    ready = true;
                    output.println("**game\n" + gameName + "\n" + "getOpponent\n" + nickName + " " + iconPath);
                    output.flush();
                }

                if (playing){
                    switch (e.getKeyCode()){
                        case VK_UP:
                        case VK_W:
                            myGameBoard.rotate();
                            output.println("**game\n" + gameName + "\n" + "rotate\n ");
                            output.flush();
                            break;
                        case VK_DOWN:
                        case VK_S:
                            myGameBoard.moveDown();
                            output.println("**game\n" + gameName + "\n" + "moveDown\n ");
                            output.flush();
                            myScore++;
                            break;
                        case VK_LEFT:
                        case VK_A:
                            myGameBoard.move(-1);
                            output.println("**game\n" + gameName + "\n" + "move\n-1");
                            output.flush();
                            break;
                        case VK_RIGHT:
                        case VK_D:
                            myGameBoard.move(1);
                            output.println("**game\n" + gameName  + "\n" + "move\n1");
                            output.flush();
                            break;
                        case VK_N:
                        case VK_SHIFT:
                            myGameBoard.holdSwitch = true;
                            output.println("**game\n" + gameName + "\n" + "hold\n ");
                            output.flush();
                            break;
                        case VK_SPACE:
                        case VK_CONTROL:
                            myScore += myGameBoard.moveToBottomUp2();
                            output.println("**game\n" + gameName + "\n" + "drop\n ");
                            output.flush();
                            break;
                    }
                }

                if (rewardMode && iWonLevel){
                    switch (e.getKeyCode()){
                        case VK_Z:
                            if (iWonLevel){
                                ((DoublePlayerGameBoard)myGameBoard).gravityDrop();
                                output.println("**game\n" + gameName + "\n" + "gravity\n ");
                                output.flush();
                                rewardMode = false;
                                playing = true;
                            }
                            break;
                        case VK_X:
                            if (iWonLevel){
                                new Thread(() -> {
                                    Point pt = ((DoublePlayerGameBoard)opponentGameBoard).deployRewardPiece();
                                    output.println("**game\n" + gameName + "\n" + "drop\n" + pt.x + " " + pt.y);
                                    output.flush();
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
                        output.println("**game\n" + gameName + "\n" + "pause\n ");
                        output.flush();
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

    public void getOpponent(String nickName, String iconPath){
        opponentIcon.setText(nickName);
        opponentIcon.setIcon(new ImageIcon(iconPath));
    }

    public void handleOpponent(String command, String data){
        switch (command){
            case "getOpponent":
                getOpponent(data.substring(0, data.lastIndexOf(' ')), data.substring(data.lastIndexOf(' ') + 1));
                opponentReady = true;
                break;
            case "pause":
                playing = !playing;
                if (playing){
                    disposePauseScreen();
                } else {
                    setupPauseScreen();
                }
                break;
            case "move":
                opponentGameBoard.move(Integer.valueOf(data));
                break;
            case "moveDown":
                opponentGameBoard.moveDown();
                opponentScore++;
                break;
            case "rotate":
                opponentGameBoard.rotate();
                break;
            case "hold":
                opponentGameBoard.holdSwitch = true;
                break;
            case "drop":
                opponentGameBoard.moveToBottomUp2();
                break;
            case "gravity":
                ((DoublePlayerGameBoard)opponentGameBoard).gravityDrop();
                break;
            case "setRewardPiece":
                ((DoublePlayerGameBoard)opponentGameBoard).setRewardPiece(new Point(Integer.valueOf(data.substring(0, data.indexOf(' '))), Integer.valueOf(data.substring(data.indexOf(' ')) + 1)));
                break;
            default:
                System.out.println("Invalid command received");
                break;
        }
    }

    @Override
    public void setupMyReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Ready?");
        waiting.addSmallMessage("Press any key to continue");

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
    void setupOpponentReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();

        waiting.setBounds(680, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("One sec...");
        waiting.addSmallMessage("Waiting for opponent...");

        while (!opponentReady){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        getLayeredPane().remove(waiting);
        repaint();
        revalidate();

        waiting = new TransparentPanel();

        waiting.setBounds(680, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Let's go");
        waiting.addSmallMessage("Opponent is ready");

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
    public void setupMyGameOverScreen(){
        String message = opponentGameOver ? "You Won!!!" : "You Lost...";

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        waiting.gameOver(message, myScore, myLines, level, true,
                new String[][]{
                        {"AI", "1370582"},
                        {"AI", "672806"},
                        {"AI", "589043"},
                        {"AI", "351792"},
                        {"AI", "310589"}});
        getLayeredPane().add(waiting, 30);

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
        String message = gameOver ? "Opponent Won" : "Opponent Lost";

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(680, 0, 680, 768);
        waiting.gameOver(message, opponentScore, opponentLines, level, true,
                new String[][]{
                        {"AI", "1370582"},
                        {"AI", "672806"},
                        {"AI", "589043"},
                        {"AI", "351792"},
                        {"AI", "310589"}});
        getLayeredPane().add(waiting, 30);

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
        waiting.addSmallMessage(iWonLevel ?
                "<html><p align=\"center\">Your score in this level: " + myLevelScore +
                        "<br>AI's score in this level: " + opponentLevelScore +
                        "<br>Press Z for gravity drop, or" +
                        "<br>Press X to put extra pieces on opponent's board.<p></html>"
                : "One sec... Opponent working...");

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
