package game.games;

import game.Tetromino;
import game.TransparentPanel;
import game.gameboards.AIDoublePlayerGameBoard;
import game.gameboards.DoublePlayerGameBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bradh on 1/18/2017.
 */
public class HumanVsAIGame extends DoublePlayerGame implements AutoPlayable{
    public static void main(String[] args){
        new HumanVsAIGame().run();
    }

    public HumanVsAIGame(){
        super();

        myGameBoard = new DoublePlayerGameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        opponentGameBoard = new AIDoublePlayerGameBoard();
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
                }

                if (playing){
                    switch (e.getKeyCode()){
                        case VK_UP:
                        case VK_W:
                            myGameBoard.rotate();
                            break;
                        case VK_DOWN:
                        case VK_S:
                            myGameBoard.moveDown();
                            myScore++;
                            break;
                        case VK_LEFT:
                        case VK_A:
                            myGameBoard.move(-1);
                            break;
                        case VK_RIGHT:
                        case VK_D:
                            myGameBoard.move(1);
                            break;
                        case VK_N:
                        case VK_SHIFT:
                            myGameBoard.holdSwitch = true;
                            break;
                        case VK_SPACE:
                        case VK_CONTROL:
                            myScore += myGameBoard.moveToBottomUp2();
                            break;
                    }
                }

                if (rewardMode && iWonLevel){
                    switch (e.getKeyCode()){
                        case VK_Z:
                            if (iWonLevel){
                                ((DoublePlayerGameBoard)myGameBoard).gravityDrop();
                                rewardMode = false;
                                playing = true;
                            }
                            break;
                        case VK_X:
                            if (iWonLevel){
                                new Thread(() -> {
                                    ((AIDoublePlayerGameBoard)opponentGameBoard).deployRewardPiece();
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

        new Thread(this :: AIGameGo).start();
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
    public void setupOpponentReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(680, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("AI is ready");
        waiting.addSmallMessage("Let's go");

        opponentReady = true;

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
        waiting.gameOver(message, myScore, myLines, level, false,
                new String[][]{
                        {"aaa", "5000"},
                        {"bbb", "4000"},
                        {"ccc", "3000"},
                        {"ddd", "2000"},
                        {"eee", "1000"}});
        getLayeredPane().add(waiting, 30);

        while (true){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setupOpponentGameOverScreen(){
        String message = gameOver ? "AI Won" : "AI Lost";

        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(680, 0, 680, 768);
        waiting.gameOver(message, opponentScore, opponentLines, level, false,
                new String[][]{
                        {"aaa", "5000"},
                        {"bbb", "4000"},
                        {"ccc", "3000"},
                        {"ddd", "2000"},
                        {"eee", "1000"}});
        getLayeredPane().add(waiting, 30);

        while (true){
            try{
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
                : "One sec... AI working...");

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

    @Override
    public void AIGameGo(){
        while (!opponentGameOver){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            if (((AIDoublePlayerGameBoard)opponentGameBoard).hasUnhandledTetromino){
                ((AIDoublePlayerGameBoard)opponentGameBoard).moveToBestPosition(this.level, this.opponentHoldTetromino != null ? this.opponentHoldTetromino : (Tetromino)this.opponentTetrominoQueue.peek(1)[0]);
            }

            if (rewardMode && !iWonLevel){
                if (((AIDoublePlayerGameBoard)opponentGameBoard).getHoleNum(((AIDoublePlayerGameBoard)opponentGameBoard).map) > 5){
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ((AIDoublePlayerGameBoard)opponentGameBoard).gravityDrop();
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    rewardMode = false;
                    playing = true;
                } else {
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ((DoublePlayerGameBoard)myGameBoard).deployRandomRewardPiece();
                    requestFocus();
                    rewardMode = false;
                    setupCountDownScreen();
                    playing = true;
                }
            }
        }
    }
}