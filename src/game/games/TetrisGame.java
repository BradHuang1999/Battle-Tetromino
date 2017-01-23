package game.games;

import game.Tetromino;
import game.TransparentPanel;
import game.gameboards.GameBoard;
import game.queue.Queue;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintWriter;


public abstract class TetrisGame extends JFrame{
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

    protected TransparentPanel pauseScreen;

    protected PrintWriter output;

    protected String gameName;

    public TetrisGame(){

        setTitle(gameName);

        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        addWindowListener(new WindowListener(){
            public void windowClosed(WindowEvent e){
            }

            public void windowOpened(WindowEvent e){
            }

            public void windowClosing(WindowEvent e){
                output.println("**leaveRoom\n" + gameName);
                output.flush();
            }

            public void windowIconified(WindowEvent e){
            }

            public void windowDeiconified(WindowEvent e){
            }

            public void windowActivated(WindowEvent e){
            }

            public void windowDeactivated(WindowEvent e){
            }
        });

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

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    public abstract void run();

    abstract void myGameGo();

    public abstract void enqueueTetromino(Tetromino tetromino);

    public void requestTetromino(){
        output.println("**game\n" + gameName + "\n" + "requestTetromino\n ");
        output.flush();
    }

    /**
     * sets up a semi-transparent screen that asks if the player is ready for the game
     * proceed if the player presses a key
     */
    protected void setupMyReadyScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        getLayeredPane().add(waiting, 30);
        waiting.addCenterMessage("Ready?");
        waiting.addSmallMessage("Press any key to continue");
        waiting.requestFocus();
        waiting.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){

            }

            @Override
            public void keyPressed(KeyEvent e){
                ready = true;
            }

            @Override
            public void keyReleased(KeyEvent e){

            }
        });

        while (!ready){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        getLayeredPane().remove(waiting);
        repaint();
        revalidate();
    }

    protected void setupPauseScreen(){
        Rectangle r = this.getBounds();
        pauseScreen = new TransparentPanel();
        pauseScreen.setBounds(0, 0, r.width, r.height);
        getLayeredPane().add(pauseScreen);

        pauseScreen.addCenterMessage("Paused");
        pauseScreen.addSmallMessage("Press P to resume");
    }

    protected void disposePauseScreen(){
        getLayeredPane().remove(pauseScreen);
        repaint();
    }

    /**
     * a semi-transparent screen that tells the player the information of the game after it is over
     * proceed if the player presses a key
     */
    protected void setupMyGameOverScreen(){
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, 680, 768);
        waiting.gameOver("Game Over", myScore, myLines, level, true,
                new String[][]{
                        {"AI", "1370582"},
                        {"AI", "672806"},
                        {"AI", "589043"},
                        {"AI", "351792"},
                        {"AI", "310589"}});
        getLayeredPane().add(waiting, 30);

        try{
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
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * sets up a semi-transparent screen that counts down for three seconds before the game
     * proceed after 3 seconds
     */
    protected void setupCountDownScreen(){
        Rectangle r = this.getBounds();
        TransparentPanel waiting = new TransparentPanel();
        waiting.setBounds(0, 0, r.width, r.height);
        getLayeredPane().add(waiting);
        waiting.countdown(3);
        getLayeredPane().remove(waiting);
        repaint();
        revalidate();
    }

    protected void checkMyHold(){
        while (!gameOver){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            if (myGameBoard.getHoldSwitch()){
                if (myHoldTetromino == null){
                    myHoldTetromino = myTetrominoQueue.dequeue();
                    myTetrominoQueue.enqueue(new Tetromino());

                    requestTetromino();

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
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

}