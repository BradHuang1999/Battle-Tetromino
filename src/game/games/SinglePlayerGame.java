package game.games;

import game.Tetromino;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import static java.awt.event.KeyEvent.VK_P;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class SinglePlayerGame extends TetrisGame{
    public SinglePlayerGame(){
        super();

        setSize(680, 768);
        setLocationRelativeTo(null);

        iconLabel.setBounds(140, 39, 400, 50);
        levelLabel.setBounds(280, 100, 120, 36);

        levelLabel.setBounds(0, 100, 680, 36);

        setFocusable(true);

        addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){

            }

            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == VK_P){
                    playing = !playing;
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e){

            }
        });
    }

    public void run(){
        new Thread(this :: myGameGo).start();
        new Thread(this :: checkMyHold).start();
    }

    protected void myGameGo(){
        for (int i = 0; i < 5; i++){
            requestTetromino();
        }

        setupCountDownScreen();
        repaint();
        requestFocus();

        playing = true;
        gameOver = false;

        int linesDisappeared;

        myCurrentTetromino = myTetrominoQueue.dequeue();
        myGameBoard.newTetromino(myCurrentTetromino);

        Object[] tetrominos = myTetrominoQueue.peek(3);
        for (int i = 0; i < 3; i++){
            myNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
        }

        levelLabel.setText("Level " + level);

        gameLoop:
        while (!gameOver){
            while (playing){
                try {
                    if (myGameBoard.isAtBottom()){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        if (myGameBoard.isAtBottom()) {

                            myGameBoard.solidifyTetromino();

                            if (myGameBoard.gameOver()) {
                                playing = false;
                                gameOver = true;
                                break gameLoop;
                            }

                            tetrominoNum++;
                            level = tetrominoNum / 35 + 1;
                            myScore += 10 * level;

                            linesDisappeared = myGameBoard.checkDisapperance();
                            myLines += linesDisappeared;
                            myScore += Math.pow(linesDisappeared, 2) * 100;

                            levelLabel.setText("Level " + level);
                            myLineLabel.setText("" + myLines);
                            myScoreLabel.setText("" + myScore);

                            myCurrentTetromino = myTetrominoQueue.dequeue();
                            myGameBoard.newTetromino(myCurrentTetromino);

                            requestTetromino();

                            tetrominos = myTetrominoQueue.peek(3);
                            for (int i = 0; i < 3; i++) {
                                myNextLabels[i].setIcon(((Tetromino) tetrominos[i]).getImg());
                            }

                            if (linesDisappeared > 0) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            repaint();
                        }
                    }

                    myGameBoard.moveDown();

                    Thread.sleep(level < 7 ? (500 - level * 40) : (340 / (level - 5)) + 150);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            while (!playing){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        setupMyGameOverScreen();
    }

    @Override
    public void enqueueTetromino(Tetromino tetromino){
        myTetrominoQueue.enqueue(tetromino);
    }
}