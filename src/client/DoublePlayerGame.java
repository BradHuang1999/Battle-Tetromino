package client;

import client.queue.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class DoublePlayerGame extends TetrisGame {
    protected GameBoard opponentGameBoard;

    protected Tetromino opponentCurrentTetromino, opponentHoldTetromino = null;
    protected Queue<Tetromino> opponentTetrominoQueue = new Queue<Tetromino>();

    protected boolean rewardMode;

    protected int opponentScore, opponentLines = 0;

    protected JLabel opponentHoldLabel, opponentScoreLabel, opponentLineLabel;
    protected JLabel[] opponentNextLabels = new JLabel[3];

    public DoublePlayerGame(){
        super();

        setSize(1360, 768);
        setLocationRelativeTo(null);

        myGameBoard = new GameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        opponentGameBoard = new GameBoard();
        opponentGameBoard.setBounds(170 + 680, 155, 338, 546);
        getContentPane().add(opponentGameBoard);

        iconLabel.setBounds(490, 39, 400, 50);
        levelLabel.setBounds(620, 100, 120, 36);

        //
        JLabel lblHold = new JLabel("Hold");
        lblHold.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblHold.setBounds(0 + 680, 220, 170, 20);
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
        lblScore.setBounds(0 + 680, 440, 170, 22);
        getContentPane().add(lblScore);

        opponentScoreLabel = new JLabel("0");
        opponentScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        opponentScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        opponentScoreLabel.setBounds(0 + 680, 470, 170, 22);
        getContentPane().add(opponentScoreLabel);

        JLabel lblLines = new JLabel("Lines");
        lblLines.setHorizontalAlignment(SwingConstants.CENTER);
        lblLines.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblLines.setBounds(0 + 680, 525, 170, 22);
        getContentPane().add(lblLines);

        opponentLineLabel = new JLabel("0");
        opponentLineLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        opponentLineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        opponentLineLabel.setBounds(0 + 680, 555, 170, 22);
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

    /**
     * TODO setup opponent ready screen
     * a semi-transparent screen that asks if the player is ready for the game
     * don't worry about keyListener
     * a big font "Waiting for Opponent" at center of the screen
     * after opponent is ready, change the words to "Opponent is ready"
     * screen is white semi-transparent
     * black font Ariel
     * covers the right half of the screen, so don't worry about the dimension, just (680,0,680,768)
     */
    protected void setupOpponentReadyScreen(){}

    /**
     * TODO setup opponent gameOver screen
     * a semi-transparent screen that tells the player the information of the opponent after it is over
     * don't worry about keyListener
     * a big font "Game Over" at top half of the screen
     * and a smaller font under that says provides:
     *          - opponent's score of the game
     *          - opponent's lines disappeared in the game
     *          - opponent's levels reached in the game
     *          - a list of 5 highest scores (an empty table is good)
     * screen is white semi-transparent
     * black font Ariel
     * covers the right half of the screen, so don't worry about the dimension, just (680,0,680,768)
     */
    protected void setupOpponentGameOverScreen(){}
}