package game.games;

import game.Tetromino;
import game.gameboards.AIDoublePlayerGameBoard;
import game.gameboards.DoublePlayerGameBoard;
import game.gameboards.GameBoard;
import game.queue.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class DoublePlayerGame extends TetrisGame{
    protected GameBoard opponentGameBoard;

    protected Tetromino opponentCurrentTetromino, opponentHoldTetromino = null;
    protected Queue<Tetromino> opponentTetrominoQueue = new Queue<Tetromino>();

    protected boolean opponentReady, opponentGameOver, rewardMode;

    protected int opponentScore = 0, opponentLines = 0, tetrominoEnqueued = 0;

    protected JLabel opponentHoldLabel, opponentScoreLabel, opponentLineLabel, opponentIcon;
    protected JLabel[] opponentNextLabels = new JLabel[3];

    protected boolean iWonLevel;

    public DoublePlayerGame(String nickName, String iconPath){
        super();

        setSize(1360, 768);
        setLocationRelativeTo(null);

        JLabel myIcon = new JLabel(nickName);
        myIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        myIcon.setBounds(180, 80, 300, 64);
        myIcon.setIcon(new ImageIcon(iconPath));
        myIcon.setHorizontalAlignment(SwingConstants.LEFT);
        getContentPane().add(myIcon);

        opponentIcon = new JLabel();
        opponentIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        opponentIcon.setBounds(1360 - 180 - 300, 80, 300, 64);
        opponentIcon.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(opponentIcon);

        iconLabel.setBounds(490, 39, 400, 50);
        levelLabel.setBounds(620, 100, 120, 36);

        JLabel lblHold = new JLabel("Hold");
        lblHold.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblHold.setBounds(680, 220, 170, 20);
        lblHold.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblHold);

        opponentHoldLabel = new JLabel("");
        ImageIcon img1 = new ImageIcon("resources/hold.png");
        opponentHoldLabel.setIcon(img1);
        opponentHoldLabel.setBounds(53 + 680, 250, 64, 64);
        getContentPane().add(opponentHoldLabel);

        JLabel lblScore = new JLabel("Score");
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblScore.setBounds(680, 440, 170, 22);
        getContentPane().add(lblScore);

        opponentScoreLabel = new JLabel("0");
        opponentScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        opponentScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        opponentScoreLabel.setBounds(680, 470, 170, 22);
        getContentPane().add(opponentScoreLabel);

        JLabel lblLines = new JLabel("Lines");
        lblLines.setHorizontalAlignment(SwingConstants.CENTER);
        lblLines.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblLines.setBounds(680, 525, 170, 22);
        getContentPane().add(lblLines);

        opponentLineLabel = new JLabel("0");
        opponentLineLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        opponentLineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        opponentLineLabel.setBounds(680, 555, 170, 22);
        getContentPane().add(opponentLineLabel);

        JLabel lblNext = new JLabel("Next");
        lblNext.setHorizontalAlignment(SwingConstants.CENTER);
        lblNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNext.setBounds(509 + 680, 208, 170, 22);
        getContentPane().add(lblNext);

        opponentNextLabels[0] = new JLabel("");
        opponentNextLabels[0].setBounds(562 + 680, 241, 64, 64);
        getContentPane().add(opponentNextLabels[0]);

        opponentNextLabels[1] = new JLabel("");
        opponentNextLabels[1].setBounds(562 + 680, 316, 64, 64);
        getContentPane().add(opponentNextLabels[1]);

        opponentNextLabels[2] = new JLabel("");
        opponentNextLabels[2].setBounds(562 + 680, 391, 64, 64);
        getContentPane().add(opponentNextLabels[2]);
    }

    public void run(){
        setVisible(true);

        opponentReady = false;
        ready = false;
        requestFocus();

        new Thread(this :: setupMyReadyScreen).start();

        new Thread(this :: setupOpponentReadyScreen).start();

        while (!(ready && opponentReady)){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++){
            requestTetromino();
        }

        setupCountDownScreen();
        repaint();

        playing = true;
        gameOver = false;
        opponentGameOver = false;

        new Thread(this :: upDateLevel).start();

        new Thread(this :: myGameGo).start();

        new Thread(this :: opponentGameGo).start();

        new Thread(this :: checkMyHold).start();

        new Thread(this :: checkOpponentHold).start();
    }

    protected void myGameGo() {
        int linesDisappeared;

        myCurrentTetromino = myTetrominoQueue.dequeue();
        myGameBoard.newTetromino(myCurrentTetromino);

        Object[] tetrominos = myTetrominoQueue.peek(3);
        for (int i = 0; i < 3; i++) {
            myNextLabels[i].setIcon(((Tetromino) tetrominos[i]).getImg());
        }

        gameLoop:
        while (!gameOver) {
            while (playing) {
                try {
                    if (myGameBoard.isAtBottom()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (myGameBoard.isAtBottom()) {


                            myGameBoard.solidifyTetromino();

                            if (myGameBoard.gameOver()) {
                                gameOver = true;
                                break gameLoop;
                            }

                            tetrominoNum++;
                            myScore += 10 * level;

                            linesDisappeared = myGameBoard.checkDisapperance();
                            myLines += linesDisappeared;
                            myScore += Math.pow(linesDisappeared, 2) * 100;
                            try {
                                ((DoublePlayerGameBoard) opponentGameBoard).addLinesOnTop(linesDisappeared - 1);
                            } catch (ClassCastException e) {
                                ((AIDoublePlayerGameBoard) opponentGameBoard).addLinesOnTop(linesDisappeared - 1);
                            }
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (!playing) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        setupMyGameOverScreen();
    }

    protected void opponentGameGo(){
        int linesDisappeared;

        opponentCurrentTetromino = opponentTetrominoQueue.dequeue();
        opponentGameBoard.newTetromino(opponentCurrentTetromino);

        Object[] tetrominos = opponentTetrominoQueue.peek(3);
        for (int i = 0; i < 3; i++){
            opponentNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
        }

        gameLoop:
        while (!opponentGameOver){
            while (playing){
                try {
                    if (opponentGameBoard.isAtBottom()){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        if (opponentGameBoard.isAtBottom()) {

                            opponentGameBoard.solidifyTetromino();

                            if (opponentGameBoard.gameOver()) {
                                opponentGameOver = true;
                                break gameLoop;
                            }

                            tetrominoNum++;
                            opponentScore += 10 * level;

                            linesDisappeared = opponentGameBoard.checkDisapperance();
                            opponentLines += linesDisappeared;
                            opponentScore += Math.pow(linesDisappeared, 2) * 100;
                            ((DoublePlayerGameBoard) myGameBoard).addLinesOnTop(linesDisappeared - 1);

                            opponentLineLabel.setText("" + opponentLines);
                            opponentScoreLabel.setText("" + opponentScore);

                            opponentCurrentTetromino = opponentTetrominoQueue.dequeue();
                            opponentGameBoard.newTetromino(opponentCurrentTetromino);

                            requestTetromino();

                            tetrominos = opponentTetrominoQueue.peek(3);
                            for (int i = 0; i < 3; i++) {
                                opponentNextLabels[i].setIcon(((Tetromino) tetrominos[i]).getImg());
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

                    opponentGameBoard.moveDown();

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

        setupOpponentGameOverScreen();
    }

    public void enqueueTetromino(Tetromino tetromino){
        myTetrominoQueue.enqueue(tetromino);
        opponentTetrominoQueue.enqueue(tetromino);
        tetrominoEnqueued++;
    }

    private void upDateLevel(){
        int prevLevel = level, myPrevScore = 0, opponentPrevScore = 0, myLevelScore, opponentLevelScore;
        while (!(gameOver && opponentGameOver)){
            level = tetrominoEnqueued / 8 + 1;
            levelLabel.setText("Level " + level);

            if (!(this instanceof OnlineBattleGame)){
                if (level != prevLevel && !gameOver && !opponentGameOver){
                    playing = false;
                    rewardMode = true;

                    myLevelScore = myScore - myPrevScore;
                    opponentLevelScore = opponentScore - opponentPrevScore;

                    setupRewardModeScreen(myLevelScore, opponentLevelScore);

                    while (rewardMode){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                    prevLevel = level;
                    myPrevScore = myScore;
                    opponentPrevScore = opponentScore;

                    repaint();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void checkOpponentHold(){
        while (!opponentGameOver){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            if (opponentGameBoard.getHoldSwitch()){
                if (opponentHoldTetromino == null){
                    opponentHoldTetromino = opponentTetrominoQueue.dequeue();
                    opponentTetrominoQueue.enqueue(new Tetromino());

                    requestTetromino();

                    Object[] tetrominos = opponentTetrominoQueue.peek(3);
                    for (int i = 0; i < 3; i++){
                        opponentNextLabels[i].setIcon(((Tetromino)tetrominos[i]).getImg());
                    }
                }

                Tetromino buffer = opponentHoldTetromino;
                opponentHoldTetromino = opponentCurrentTetromino;
                opponentCurrentTetromino = buffer;

                opponentGameBoard.current = opponentCurrentTetromino;
                opponentGameBoard.replace(opponentCurrentTetromino);
                opponentHoldLabel.setIcon(opponentHoldTetromino.getImg());
                repaint();
            }
        }
    }

    /**
     * a semi-transparent screen that asks if the player is ready for the game
     */
    abstract void setupOpponentReadyScreen();

    /**
     * a semi-transparent screen that tells the player the information of the opponent after it is over
     */
    abstract void setupOpponentGameOverScreen();

    /**
     * reward screen
     */
    abstract void setupRewardModeScreen(int myLevelScore, int opponentLevelScore);
}