package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tetromino {
    private char tetrominoType;
    private Color tetrominoColor;

    private int phase = 0;
    private ImageIcon img;

    private boolean[][][] shapes = new boolean[4][][];

    /**
     * generate a tetromino based on given type
     * @param type type of tetromino
     */
    public Tetromino(char type){
        this.tetrominoType = type;
        this.phase = 0;
        this.img = new ImageIcon("resources/" + this.tetrominoType + ".png");

        switch (type){
            case 'I':
                this.shapes[0] = new boolean[][]{
                        {true, false, false, false},
                        {true, false, false, false},
                        {true, false, false, false},
                        {true, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {true, true, true, true},
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {true, false, false, false},
                        {true, false, false, false},
                        {true, false, false, false},
                        {true, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {true, true, true, true},
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(250, 100, 0);
                break;

            case 'S':
                this.shapes[0] = new boolean[][]{
                        {true, false, false, false},
                        {true, true, false, false},
                        {false, true, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {false, true, true, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {true, false, false, false},
                        {true, true, false, false},
                        {false, true, false, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {false, true, true, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(200, 150, 0);
                break;

            case 'O':
                this.shapes[0] = new boolean[][]{
                        {true, true, false, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {true, true, false, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {true, true, false, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {true, true, false, false},
                        {true, true, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(150, 200, 0);
                break;

            case 'L':
                this.shapes[0] = new boolean[][]{
                        {true, true, false, false},
                        {true, false, false, false},
                        {true, false, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {false, false, false, false},
                        {true, false, false, false},
                        {true, true, true, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, true, true, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {true, true, true, false},
                        {false, false, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(50, 250, 50);
                break;

            case 'Z':
                this.shapes[0] = new boolean[][]{
                        {false, true, false, false},
                        {true, true, false, false},
                        {true, false, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {true, true, false, false},
                        {false, true, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {false, true, false, false},
                        {true, true, false, false},
                        {true, false, false, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {true, true, false, false},
                        {false, true, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(0, 100, 250);
                break;

            case 'T':
                this.shapes[0] = new boolean[][]{
                        {false, true, false, false},
                        {false, true, true, false},
                        {false, true, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {false, true, false, false},
                        {true, true, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {false, true, false, false},
                        {true, true, false, false},
                        {false, true, false, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {false, false, false, false},
                        {true, true, true, false},
                        {false, true, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(150, 0, 200);
                break;

            case 'J':
                this.shapes[0] = new boolean[][]{
                        {true, false, false, false},
                        {true, false, false, false},
                        {true, true, false, false},
                        {false, false, false, false}};

                this.shapes[1] = new boolean[][]{
                        {false, false, false, false},
                        {false, false, true, false},
                        {true, true, true, false},
                        {false, false, false, false}};

                this.shapes[2] = new boolean[][]{
                        {false, true, true, false},
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, false, false}};

                this.shapes[3] = new boolean[][]{
                        {true, true, true, false},
                        {true, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

                this.tetrominoColor = new Color(250, 0, 100);
                break;
        }
    }

    /**
     * generate a random tetromino
     */
    public Tetromino(){
        this(new char[]{'I', 'S', 'O', 'L', 'Z', 'T', 'J'}[new Random().nextInt(7)]);
    }

    public boolean[][] getCurrentShape(){
        return this.shapes[this.phase];
    }

    public boolean[][] peekNextShape(){
        return this.shapes[this.phase == 3 ? 0 : this.phase + 1];
    }

    public boolean[][] getNextShape(){
        this.phase++;
        if (this.phase > 3){
            this.phase = 0;
        }
        return this.getCurrentShape();
    }

    public Color getTetrominoColor() {
        return tetrominoColor;
    }

    public ImageIcon getImg() {
        return img;
    }

    public int getPhase(){
        return phase;
    }

    public boolean[][][] getShapes() {
        return shapes;
    }

    public void setPhase(int phase){
        this.phase = phase;
    }
}