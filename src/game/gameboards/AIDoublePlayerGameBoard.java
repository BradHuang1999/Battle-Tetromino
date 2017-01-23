package game.gameboards;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

/**
 * @author Brad Huang
 * @date Jan 21, 2017
 */
public class AIDoublePlayerGameBoard extends AIGameBoard implements DoublePlayable{
    private boolean deployed;
    private boolean[][] rewardPiece;
    private int rewardPieceX, rewardPieceY;

    // construct keylistener for rewards first so that it can be added and removed
    KeyListener rewardKL = new KeyListener(){
        @Override
        public void keyTyped(KeyEvent e){

        }

        // alter reward piece position
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case VK_UP:
                case VK_W:
                    if (rewardPieceY < 12){
                        rewardPiece[rewardPieceX][rewardPieceY] = false;
                        rewardPieceY++;
                        rewardPiece[rewardPieceX][rewardPieceY] = true;
                        repaint();
                    }
                    break;
                case VK_DOWN:
                case VK_S:
                    if (rewardPieceY > 1){
                        rewardPiece[rewardPieceX][rewardPieceY] = false;
                        rewardPieceY--;
                        rewardPiece[rewardPieceX][rewardPieceY] = true;
                        repaint();
                    }
                    break;
                case VK_RIGHT:
                case VK_D:
                    if (rewardPieceX < 12){
                        rewardPiece[rewardPieceX][rewardPieceY] = false;
                        rewardPieceX++;
                        rewardPiece[rewardPieceX][rewardPieceY] = true;
                        repaint();
                    }
                    break;
                case VK_LEFT:
                case VK_A:
                    if (rewardPieceX > 1){
                        rewardPiece[rewardPieceX][rewardPieceY] = false;
                        rewardPieceX--;
                        rewardPiece[rewardPieceX][rewardPieceY] = true;
                        repaint();
                    }
                    break;
                case VK_ENTER:
                case VK_CONTROL:
                    map[rewardPieceX][rewardPieceY] = true;
                    colors[rewardPieceX][rewardPieceY] = Color.WHITE;
                    deployed = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e){

        }
    };

    /**
     * add lines on top of the gameBoard for punishment
     * @param lineNum lines to be added, >= 0
     */
    @Override
    public synchronized void addLinesOnTop(int lineNum){
        if (lineNum <= 0){
            return;     // if no line added go back
        }
        for (int i = 1; i < 13; i++){
            for (int j = 21; j > 0; j--){
                if (map[i][j - 1] && !movable[i][j - 1]){
                    for (int k = 0; k < lineNum; k++){
                        map[i][j + k] = true;
                        if (movable[i][j + k]){
                            movable[i][j + k] = false;
                        } else {
                            colors[i][j + k] = Color.WHITE;     // add piece
                        }
                    }
                    break;
                }
            }
        }
        repaint();
    }

    /**
     * drop all the dangling pieces on field
     */
    @Override
    public synchronized void gravityDrop(){
        for (int i = 1; i < 13; i++){
            for (int j = 21; j > 0; j--){
                if ((!movable[i][j]) && map[i][j] && (!map[i][j - 1])){     // ignore the moving ones
                    for (int k = j; k < 22; k++){
                        if (movable[i][k]){
                            break;
                        } else {
                            map[i][k - 1] = map[i][k];      // drop the piece
                            colors[i][k - 1] = colors[i][k];
                            disappearable[i][k - 1] = disappearable[i][k];

                            map[i][k] = false;
                            colors[i][k] = null;
                            disappearable[i][k] = true;
                        }
                    }
                }
            }
        }
        repaint();
    }

    /**
     * deploy reward piece for the opponent
     * @return the point to be delivered
     */
    @Override
    public synchronized Point deployRewardPiece(){
        deployed = false;

        rewardPiece = new boolean[14][25];
        rewardPiece[6][10] = true;
        rewardPieceX = 6;
        rewardPieceY = 10;

        repaint();
        requestFocus();
        addKeyListener(rewardKL);

        while (!deployed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        rewardPiece = null;
        removeKeyListener(rewardKL);
        repaint();

        return new Point(rewardPieceX, rewardPieceY);
    }

    /**
     * update the drawpiece function to include rewardpieces
     * @param g graphic component
     */
    @Override
    public void drawPiece(Graphics g){
        super.drawPiece(g);
        g.setColor(Color.YELLOW);
        if (rewardPiece != null){
            for (int y = 1; y < 21; y++){
                for (int x = 1; x < 13; x++){
                    if (rewardPiece[x][y]){
                        g.fillRoundRect(26 * x - 13, 26 * (21 - y) - 13, 25, 25, 2, 2);
                    }
                }
            }
        }
    }
}