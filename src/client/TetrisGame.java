package client;

import client.queue.Queue;

import javax.swing.*;

import java.awt.*;


abstract class TetrisGame extends JFrame{
    protected GameBoard myGameBoard;

    protected boolean ready, playing, gameOver;

    protected int myScore = 0, myLines = 0;
    protected int level = 1;
    protected int tetrominoNum = 0;

    protected JLabel iconLabel, levelLabel;
    protected JLabel myHoldLabel, myScoreLabel, myLineLabel;
    protected JLabel[] myNextLabels = new JLabel[3];

    protected Tetromino myCurrentTetromino, myHoldTetromino = null;
    protected Queue<Tetromino> myTetrominoQueue = new Queue<Tetromino>();

    public TetrisGame(){
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        iconLabel = new JLabel("");
        ImageIcon img = new ImageIcon("resources/tetronimo.png");
        iconLabel.setIcon(img);
        getContentPane().add(iconLabel);

        levelLabel = new JLabel("LEVEL " + level);
        levelLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(levelLabel);

        JLabel lblHold = new JLabel("Hold");
        lblHold.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblHold.setBounds(0, 220, 170, 20);
        lblHold.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblHold);

        myHoldLabel = new JLabel("");
        ImageIcon img1 = new ImageIcon("resources/hold.png");
        myHoldLabel.setIcon(img1);
        myHoldLabel.setBounds(53, 250, 64, 64);
        getContentPane().add(myHoldLabel);

        JLabel lblScore = new JLabel("Score");
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblScore.setBounds(0, 440, 170, 22);
        getContentPane().add(lblScore);

        myScoreLabel = new JLabel("0");
        myScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        myScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myScoreLabel.setBounds(0, 470, 170, 22);
        getContentPane().add(myScoreLabel);

        JLabel lblLines = new JLabel("Lines");
        lblLines.setHorizontalAlignment(SwingConstants.CENTER);
        lblLines.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblLines.setBounds(0, 525, 170, 22);
        getContentPane().add(lblLines);

        myLineLabel = new JLabel("0");
        myLineLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        myLineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLineLabel.setBounds(0, 555, 170, 22);
        getContentPane().add(myLineLabel);

        JLabel lblNext = new JLabel("Next");
        lblNext.setHorizontalAlignment(SwingConstants.CENTER);
        lblNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNext.setBounds(509, 208, 170, 22);
        getContentPane().add(lblNext);

        myNextLabels[0] = new JLabel("");
        myNextLabels[0].setBounds(562, 241, 64, 64);
        getContentPane().add(myNextLabels[0]);

        myNextLabels[1] = new JLabel("");
        myNextLabels[1].setBounds(562, 316, 64, 64);
        getContentPane().add(myNextLabels[1]);

        myNextLabels[2] = new JLabel("");
        myNextLabels[2].setBounds(562, 391, 64, 64);
        getContentPane().add(myNextLabels[2]);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * TODO setup ready screen
     * a semi-transparent screen that asks if the player is ready for the game
     * proceed if the player presses a key
     * a big font "Ready?" at center of the screen
     * and a smaller font under that says "press any key to continue"
     * screen is white semi-transparent
     * black font Ariel
     * covers the left half of the screen if double player, so don't worry about the dimension, just (0,0,680,768)
     */
    protected void setupMyReadyScreen(){}

    /**
     * TODO setup gameOver screen
     * a semi-transparent screen that tells the player the information of the game after it is over
     * proceed if the player presses a key
     * a big font "Game Over" at top half of the screen
     * and a smaller font under that says provides:
     *          - the score of the game
     *          - the lines disappeared in the game
     *          - the levels reached in the game
     *          - a list of 5 highest scores (an empty table is good)
     * screen is white semi-transparent
     * black font Ariel
     * covers the left half of the screen if double player, so don't worry about the dimension, just (0,0,680,768)
     */
    protected void setupMyGameOverScreen(){}


    /**
     * TODO setup countdown screen
     * a semi-transparent screen that counts down for three seconds before the game
     * proceed if the player presses a key
     * a big number at center of the screen
     * screen is white semi-transparent
     * black font Ariel
     * covers THE ENTIRE SCREEN no matter how big it is
     */
    protected void setupCountDownScreen(){}
}