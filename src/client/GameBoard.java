package client;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private boolean[][] map = new boolean[14][25];
    private boolean[][] disappearable = new boolean[14][25];
    private boolean[][] movable = new boolean[14][25];
    private Color[][] colors = new Color[14][25];

    private int score, lines;
    private Tetromino current, hold, next1, next2, next3;

    private int piecePositionX, piecePositionY;

    public GameBoard() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 25; j++) {
                this.map[i][j] = (i == 0) || (i == 13) || (j == 0);
                this.disappearable[i][j] = !((i == 0) || (i == 13) || (j == 0));
                this.movable[i][j] = false;
                this.colors[i][j] = null;
            }
        }

        this.score = 0;
        this.lines = 0;
    }

    /**
     * move the controlled tetromino laterally
     * @param direction the direction to move the tetromino, negative to the left and positive to the right
     */
    public void move(int direction) {
        if (direction == 0){
            return;
        }
        if (this.movePossible(direction)) {
            int[] columns = direction < 0 ? new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12} : new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            for (int i : columns) {
                for (int j = 1; j < 25; j++) {
                    if (this.movable[i][j]) {
                        this.map[i + direction][j] = this.map[i][j];
                        this.movable[i + direction][j] = this.movable[i][j];
                        this.colors[i + direction][j] = this.colors[i][j];

                        this.map[i][j] = false;
                        this.movable[i][j] = false;
                        this.colors[i][j] = null;

                        this.piecePositionX += direction;
                    }
                }
            }
        }
    }

    /**
     * move the tetromino down by one unit
     */
    public void moveDown() {
        if (!this.isAtBottom()) {
            for (int j = 1; j < 25; j++) {
                for (int i = 1; i < 13; i++) {
                    if (this.movable[i][j]) {
                        this.map[i][j - 1] = this.map[i][j];
                        this.movable[i][j - 1] = this.movable[i][j];
                        this.colors[i][j - 1] = this.colors[i][j];

                        this.map[i][j] = false;
                        this.movable[i][j] = false;
                        this.colors[i][j] = null;

                        this.piecePositionY--;
                    }
                }
            }
        }
    }

    public void rotate() {
        int movement = this.rotatePossible();

        if (movement != -9999){
            boolean[][] shape = this.current.getCurrentShape();
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    if (shape[x][y]){
                        this.map[this.piecePositionX + x][this.piecePositionY + y] = false;
                        this.movable[this.piecePositionX + x][this.piecePositionY + y] = false;
                        this.colors[this.piecePositionX + x][this.piecePositionY + y] = null;
                    }
                }
            }
            shape = this.current.getNextShape();
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    if (shape[x][y]){
                        this.map[this.piecePositionX + x][this.piecePositionY + y] = true;
                        this.movable[this.piecePositionX + x][this.piecePositionY + y] = true;
                        this.colors[this.piecePositionX + x][this.piecePositionY + y] = this.current.getTetrominoColor();
                    }
                }
            }
        }
    }

    private boolean movePossible(int direction){
        for (int i = 1; i < 13; i++){
            for (int j = 1; j < 25; j++){
                if (this.movable[i][j]){
                    if (this.map[i + direction][j] && !this.movable[i + direction][j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int rotatePossible() {
        int pieceX;
        boolean returnable;
        for (int i = 0; i < 3; i++) {
            pieceX = this.piecePositionX + i;
            returnable = true;
            outerLoop:
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (this.current.peekNextShape()[x][y] && !this.movable[pieceX][this.piecePositionY]) {
                        returnable = false;
                        break outerLoop;
                    }
                }
            }
            if (returnable) {
                return i;
            }

            pieceX = this.piecePositionX - i;
            returnable = true;
            outerLoop:
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (this.current.peekNextShape()[x][y] && !this.movable[pieceX][this.piecePositionY]) {
                        returnable = false;
                        break outerLoop;
                    }
                }
            }
            if (returnable) {
                return -i;
            }
        }
        return -9999;
    }

    public boolean isAtBottom() {
        for (int i = 1; i < 13; i++){
            for (int j = 1; j < 25; j++){
                if (this.movable[i][j]){
                    if (this.map[i][j - 1] && !this.movable[i][j - 1]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void solidifyTetromino(){
        for (int i = 1; i < 21; i++){
            for (int j = 1; j < 13; j++){
                this.movable[j][i] = false;
            }
        }
    }

    public int checkDisapperance(){
        int rowDisappered = 0;
        boolean disappear;
        for (int i = 1; i < 21; i++){
            disappear = true;
            for (int j = 1; j < 13; j++){
                if (this.disappearable[j][i] && !this.map[j][i]){
                    disappear = false;
                    break;
                }
            }
            if (disappear){
                for(int x = i; x < 21; x++){
                    for (int y = 1; y < 13; y++){
                        if (this.disappearable[x][y]) {
                            if (this.disappearable[x][y + 1]) {
                                this.map[x][y] = this.map[x][y + 1];
                                this.colors[x][y] = this.colors[x][y + 1];
                            } else {
                                this.map[x][y] = false;
                                this.colors[x][y] = null;
                            }
                        }
                    }
                }
                i--;
                rowDisappered++;
            }
        }
        return rowDisappered;
    }

    public void newTetromino(Tetromino tetromino){
        this.current = tetromino;
        piecePositionX = 5;
        piecePositionX = 21;

        int pieceX, pieceY;

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (tetromino.getCurrentShape()[x][y]){
                    pieceX = piecePositionX + x;
                    pieceY = piecePositionY + y;

                    this.map[pieceX][pieceY] = true;
                    this.movable[pieceX][pieceY] = false;
                    this.colors[pieceX][pieceY] = tetromino.getTetrominoColor();
                }
            }
        }
    }

}