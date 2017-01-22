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
    public SinglePlayerGame(PrintWriter output){
        super(output);

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
            myTetrominoQueue.enqueue(new Tetromino());
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

//                    + " " + ((AIGameBoard)myGameBoard).getAggHeight(myGameBoard.map)
//                    + " " + ((AIGameBoard)myGameBoard).getCompleteLines(myGameBoard.map)
//                    + " " + ((AIGameBoard)myGameBoard).getHoleNum(myGameBoard.map)
//                    + " " + ((AIGameBoard)myGameBoard).getBumpiness(myGameBoard.map)
//                    + " " + ((AIGameBoard)myGameBoard).getSpikiness(myGameBoard.map)
//            );

        gameLoop:
        while (!gameOver){
            while (playing){
                try {
                    if (myGameBoard.isAtBottom()){
                        myGameBoard.solidifyTetromino();
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        if (myGameBoard.gameOver()){
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

//                                    + " " + ((AIGameBoard)myGameBoard).getAggHeight(myGameBoard.map)
//                                    + " " + ((AIGameBoard)myGameBoard).getCompleteLines(myGameBoard.map)
//                                    + " " + ((AIGameBoard)myGameBoard).getHoleNum(myGameBoard.map)
//                                    + " " + ((AIGameBoard)myGameBoard).getBumpiness(myGameBoard.map)
//                                    + " " + ((AIGameBoard)myGameBoard).getSpikiness(myGameBoard.map)
//                            );

                        myLineLabel.setText("" + myLines);
                        myScoreLabel.setText("" + myScore);

                        myCurrentTetromino = myTetrominoQueue.dequeue();
                        myGameBoard.newTetromino(myCurrentTetromino);
                        myTetrominoQueue.enqueue(new Tetromino());

                        tetrominos = myTetrominoQueue.peek(3);
                        for (int i = 0; i < 3; i++){
                            myNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
                        }

                        if (linesDisappeared > 0){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                        repaint();

//                            TESTING
//                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                            for (int b = 20; b > 0; b--){
//                                for (int a = 1; a < 13; a++){
//                                    if (myGameBoard.map[a][b]){
//                                        System.out.print(1);
//                                    } else {
//                                        System.out.print(0);
//                                    }
//                                }
//                                System.out.println();
//                            }
//                            System.out.println(" \taggHeight = " + ((AIGameBoard)myGameBoard).getAggHeight(myGameBoard.map) +
//                                    " \tcompleteLines = " + ((AIGameBoard)myGameBoard).getCompleteLines(myGameBoard.map) +
//                                    " \tholeNum = " + ((AIGameBoard)myGameBoard).getHoleNum(myGameBoard.map) +
//                                    " \tbumpiness = " + ((AIGameBoard)myGameBoard).getBumpiness(myGameBoard.map) +
//                                    " \tspikiness = " + ((AIGameBoard)myGameBoard).getSpikiness(myGameBoard.map)
//                            );
//
//                            System.out.println();
//                            System.out.println();

                    }

                    myGameBoard.moveDown();

//                        Thread.sleep(60);
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
}