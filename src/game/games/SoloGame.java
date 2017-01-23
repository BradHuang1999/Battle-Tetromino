package game.games;

import game.gameboards.GameBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bradh on 1/18/2017.
 */
public class SoloGame extends SinglePlayerGame{
    public SoloGame(PrintWriter output) {
        super();

        this.output = output;

        myGameBoard = new GameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        addKeyListener(new KeyListener() {
                           @Override
                           public void keyTyped(KeyEvent e) {
                           }

                           @Override
                           public void keyPressed(KeyEvent e){
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
                                       case VK_SHIFT:
                                           myGameBoard.holdSwitch = true;
                                           break;
                                       case VK_SPACE:
                                           myScore += myGameBoard.moveToBottomUp2();
                                           break;
                                   }
                               }
                           }

                           @Override
                           public void keyReleased(KeyEvent e) {
                           }
                       }
        );
    }

    public void run(){
        this.setVisible(true);
        new Thread(() -> {
            ready = false;
            this.setupMyReadyScreen();
            super.run();
        }).start();
    }
}