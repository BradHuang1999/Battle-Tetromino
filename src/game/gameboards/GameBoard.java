package game.gameboards;

import game.Tetromino;

import javax.swing.*;

import java.awt.*;

public class GameBoard extends JPanel{
    public boolean[][] map = new boolean[14][25];
    public boolean[][] disappearable = new boolean[14][25];
    public boolean[][] movable = new boolean[14][25];
    public Color[][] colors = new Color[14][25];

    public int piecePositionX, piecePositionY;

    public Tetromino current;

    public boolean getHoldSwitch(){
        return holdSwitch;
    }

    public boolean holdSwitch = false;

    public GameBoard(){
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 25; j++){
                this.map[i][j] = (i == 0) || (i == 13) || (j == 0);
                this.disappearable[i][j] = !((i == 0) || (i == 13) || (j == 0));
                this.movable[i][j] = false;
                this.colors[i][j] = null;
            }
        }
    }

    /**
     * move the controlled tetromino laterally
     *
     * @param direction the direction to move the tetromino, negative to the left and positive to the right
     */
    public synchronized void move(int direction){
        if (direction == 0){
            return;
        }
        if (this.movePossible(direction)){
            int[] columns = direction < 0 ? new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12} : new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            for (int i : columns){
                for (int j = 1; j < 25; j++){
                    if (this.movable[i][j]){
                        this.map[i + direction][j] = this.map[i][j];
                        this.movable[i + direction][j] = this.movable[i][j];
                        this.colors[i + direction][j] = this.colors[i][j];

                        this.map[i][j] = false;
                        this.movable[i][j] = false;
                        this.colors[i][j] = null;
                    }
                }
            }
            this.piecePositionX += direction;
        }
        repaint();
    }

    /**
     * move the tetromino down by one unit
     */
    public synchronized void moveDown(){
        if (!this.isAtBottom()){
            for (int j = 1; j < 25; j++){
                for (int i = 1; i < 13; i++){
                    if (this.movable[i][j]){
                        this.map[i][j - 1] = this.map[i][j];
                        this.movable[i][j - 1] = this.movable[i][j];
                        this.colors[i][j - 1] = this.colors[i][j];

                        this.map[i][j] = false;
                        this.movable[i][j] = false;
                        this.colors[i][j] = null;

                    }
                }
            }
        }
        this.piecePositionY--;
        repaint();
    }

    public synchronized void rotate(){
        int movement = this.rotatePossible();

        if (movement != -9999){
            for (int y = 0; y < 25; y++){
                for (int x = 1; x < 13; x++){
                    if (movable[x][y]){
                        this.map[x][y] = false;
                        this.movable[x][y] = false;
                        this.colors[x][y] = null;
                    }
                }
            }
            boolean[][] shape = this.current.getNextShape();
            this.piecePositionX += movement;
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
        repaint();
    }

    public synchronized void replace(Tetromino tetromino){
        boolean[][] shape = tetromino.getCurrentShape();
        int movement = this.replacePossible(shape);

        if (movement != -9999){
            for (int y = 0; y < 25; y++){
                for (int x = 1; x < 13; x++){
                    if (movable[x][y]){
                        this.map[x][y] = false;
                        this.movable[x][y] = false;
                        this.colors[x][y] = null;
                    }
                }
            }

            this.piecePositionX += movement;
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    if (shape[x][y]){
                        this.map[this.piecePositionX + x][this.piecePositionY + y] = true;
                        this.movable[this.piecePositionX + x][this.piecePositionY + y] = true;
                        this.colors[this.piecePositionX + x][this.piecePositionY + y] = this.current.getTetrominoColor();
                    }
                }
            }

            repaint();
        }

        holdSwitch = false;
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

    private int replacePossible(boolean[][] tetrominoShape){
        int pieceX;
        boolean returnable;
        for (int i = 0; i < 4; i++){
            pieceX = this.piecePositionX + i;
            returnable = true;
            outerLoop:
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    try {
                        if (tetrominoShape[x][y] && (!this.movable[pieceX + x][this.piecePositionY + y] && this.map[pieceX + x][this.piecePositionY + y])){
                            returnable = false;
                            break outerLoop;
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        returnable = false;
                        break outerLoop;
                    }
                }
            }
            if (returnable){
                return i;
            }

            pieceX = this.piecePositionX - i;
            returnable = true;
            outerLoop:
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    try {
                        if (tetrominoShape[x][y] && (!this.movable[pieceX + x][this.piecePositionY + y] && this.map[pieceX + x][this.piecePositionY + y])){
                            returnable = false;
                            break outerLoop;
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        returnable = false;
                        break outerLoop;
                    }
                }
            }
            if (returnable){
                return -i;
            }
        }
        return -9999;
    }

    protected int rotatePossible(){
        return this.replacePossible(this.current.peekNextShape());
    }

    public boolean isAtBottom(){
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
        repaint();
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
                for (int y = i; y < 21; y++){
                    for (int x = 1; x < 13; x++){
                        if (this.disappearable[x][y]){
                            if (this.disappearable[x][y + 1]){
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
        repaint();
        return rowDisappered;
    }

    public int moveToBottomUp2() {
        int YCoordinate = 1;
        outerLoop:
        for (int a = 21; a > 0; a--) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (this.current.getCurrentShape()[x][y] && (this.map[piecePositionX + x][a + y - 1] && !this.movable[piecePositionX + x][a + y - 1])) {
                        YCoordinate = a;
                        break outerLoop;
                    }
                }
            }
        }

        int yDestination = YCoordinate + 4;
        new Thread(() -> {
            for (int i = piecePositionY; i > yDestination; i--) {
                try {
                    moveDown();

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }, "GameBoard-moveToBottomUp2").start();

        return piecePositionY - yDestination + 2;
    }

    public boolean gameOver(){
        for (int i = 1; i < 13; i++){
            if (this.map[i][21]){
                return true;
            }
        }
        return false;
    }

    protected int getHeight(boolean[][] map, int left, int right) {
        if (left < 0) {
            left = 0;
        }
        if (right > 13) {
            right = 13;
        }

        int height = 0, columnHeight;

        for (int x = left; x < right; x++) {
            columnHeight = getHeight(map, x);
            height = height > columnHeight ? height : columnHeight;
        }

        return height > 0 ? height : 1;
    }

    protected int getHeight(boolean[][] map, int column) {
        for (int y = 20; y > 0; y--) {
            if (map[column][y]) {
                return y;
            }
        }
        return 0;
    }

    public void newTetromino(Tetromino tetromino){
        this.current = tetromino;
        tetromino.setPhase(0);

        piecePositionX = 5;
        piecePositionY = 21;

        int pieceX, pieceY;

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (tetromino.getCurrentShape()[x][y]){
                    pieceX = piecePositionX + x;
                    pieceY = piecePositionY + y;

                    this.map[pieceX][pieceY] = true;
                    this.movable[pieceX][pieceY] = true;
                    this.colors[pieceX][pieceY] = tetromino.getTetrominoColor();
                }
            }
        }
        repaint();
    }

    /**
     * Override the paintComponent method
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        // Paint the well
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(0, 0, 338, 546, 8, 8);

        for (int i = 1; i < 21; i++){
            for (int j = 1; j < 13; j++){
                g.setColor(Color.BLACK);
                g.fillRoundRect(26 * j - 13, 26 * i - 13, 25, 25, 2, 2);
            }
        }

        drawPiece(g);
    }

    /**
     * Draw the falling piece
     * @param g
     */
    public void drawPiece(Graphics g){
        if (current == null){
            return;
        }

        for (int y = 1; y < 21; y++){
            for (int x = 1; x < 13; x++){
                if (map[x][y]){
                    g.setColor(colors[x][y]);
                    g.fillRoundRect(26 * x - 13, 26 * (21 - y) - 13, 25, 25, 2, 2);
                }
            }
        }
    }
}