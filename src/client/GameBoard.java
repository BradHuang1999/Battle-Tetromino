package client;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel{
    private boolean[][] map = new boolean[14][25];
    private boolean[][] disappearable = new boolean[14][25];
    private boolean[][] movable = new boolean[14][25];
    private Color[][] colors = new Color[14][25];

    private int score, level, lines;
    private Tetromino hold, next0, next1, next2;

    private int piecePositionX, piecePositionY;

    public GameBoard(){
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 25; j++) {
                this.map[i][j] = (i == 0) || (i == 13) || (j == 0);
                this.disappearable[i][j] = !((i == 0) || (i == 13) || (j == 0));
                this.movable[i][j] = false;
                this.colors[i][j] = null;
            }
        }
    }

    public boolean moveLeft(){
        // TODO

        return this.atBottom();
    }

    public boolean moveRight(){
        // TODO

        return this.atBottom();
    }

    public boolean moveDown(){
        // TODO

        return this.atBottom();
    }

    public boolean rotate(){
        // TODO

        return this.atBottom();
    }

    private boolean atBottom(){
        // TODO
        boolean atBottom = true;

        return atBottom;
    }
}