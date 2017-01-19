package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bradh on 1/18/2017.
 */
public class SoloGame extends SinglePlayerGame{
    public static void main(String[] args){
        new SoloGame().setVisible(true);
    }

    public SoloGame(){
        super();

        setSize(680, 768);

        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyListener(){
                           @Override
                           public void keyTyped(KeyEvent e){
                           }

                           @Override
                           public void keyPressed(KeyEvent e){
                               switch (e.getKeyCode()){
                                   case VK_UP:
                                       myGameBoard.rotate();
                                       break;
                                   case VK_DOWN:
                                       myGameBoard.moveDown();
                                       myScore++;
                                       break;
                                   case VK_LEFT:
                                       myGameBoard.move(-1);
                                       break;
                                   case VK_RIGHT:
                                       myGameBoard.move(1);
                                       break;
                                   case VK_SPACE:
                                       if (myHoldTetromino == null){
                                           myHoldTetromino = myTetrominoQueue.dequeue();
                                           myTetrominoQueue.enqueue(new Tetromino());

                                           Object[] tetrominos = myTetrominoQueue.peek(3);
                                           for (int i = 0; i < 3; i++){
                                               myNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
                                           }
                                       }

                                       Tetromino buffer = myHoldTetromino;
                                       myHoldTetromino = myCurrentTetromino;
                                       myCurrentTetromino = buffer;

                                       myGameBoard.current = myCurrentTetromino;
                                       myGameBoard.replace(myCurrentTetromino);
                                       myHoldLabel.setIcon(myHoldTetromino.getImg());
                                       repaint();
                               }
                           }

                           @Override
                           public void keyReleased(KeyEvent e){
                           }
                       }
        );

        for (int i = 0; i < 4; i++){
            myTetrominoQueue.enqueue(new Tetromino());
        }

        new Thread(() -> {
            ready = false;
            playing = true;
            gameOver = false;

//            while(!ready){
//                setupMyReadyScreen();
//            }
//
//            for (int i = 3; i > 0; i++){
//                setupCountDownScreen();
//            }

            int linesDisappeared;

            myCurrentTetromino = myTetrominoQueue.dequeue();
            myGameBoard.newTetromino(myCurrentTetromino);

            Object[] tetrominos = myTetrominoQueue.peek(3);
            for (int i = 0; i < 3; i++){
                myNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
            }

            while (!gameOver){
                while (playing){
                    try {
                        if (myGameBoard.isAtBottom()){
                            myGameBoard.solidifyTetromino();

                            if (myGameBoard.gameOver()){
                                playing = false;
                                gameOver = true;
                            }

                            tetrominoNum++;
                            level = tetrominoNum / 25 + 1;
                            myScore += 10 * level;

                            linesDisappeared = myGameBoard.checkDisapperance();
                            myLines += linesDisappeared;
                            myScore += Math.pow(linesDisappeared, 2) * 100;

                            levelLabel.setText("Level " + level);
                            myLineLabel.setText("" + myLines);
                            myScoreLabel.setText("" + myScore);

                            myCurrentTetromino = myTetrominoQueue.dequeue();
                            myGameBoard.newTetromino(myCurrentTetromino);
                            myTetrominoQueue.enqueue(new Tetromino());

                            tetrominos = myTetrominoQueue.peek(3);
                            for (int i = 0; i < 3; i++){
                                myNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
                            }

                            repaint();
                        }

                        myGameBoard.moveDown();

                        Thread.sleep(level < 30 ? (500 - level * 30) : (500 / (level - 20)) + 150);
                    } catch (InterruptedException e){
                    }
                }
            }

//            while(gameOver){
//                setupMyGameOverScreen();
//            }
        }, "player-thread").start();

    }
}