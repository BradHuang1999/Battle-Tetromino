package client;

import java.awt.*;
import java.util.Random;

public class Tetromino {
    private char tetrominoType;
    private Color tetrominoColor;

    private boolean[][] p0, p1, p2, p3;

    /**
     * generate a tetromino based on given type
     * @param type type of tetromino
     */
    public Tetromino(char type) {
        this.tetrominoType = type;
        switch (type) {
            case 'I':
                this.p0 = new boolean[][]{
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false}};

                this.p1 = new boolean[][]{
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false},
                        {true, true, true, true}};

                this.p2 = new boolean[][]{
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false},
                        {true, true, true, true}};

                this.p3 = new boolean[][]{
                        {false, false, true, false},
                        {false, false, true, false},
                        {false, false, true, false},
                        {true, true, true, true}};

                this.tetrominoColor = new Color(250,100,0);
                break;

            case 'S':
                this.tetrominoColor = new Color(200,150,0);
                break;

            case 'O':
                this.tetrominoColor = new Color(150,200,0);
                break;

            case 'L':
                this.tetrominoColor = new Color(50,250,50);
                break;

            case 'Z':
                this.tetrominoColor = new Color(0,100,250);
                break;

            case 'T':
                this.tetrominoColor = new Color(150,0,200);
                break;

            case 'J':
                this.tetrominoColor = new Color(250,0,100);
                break;
        }
    }

    /**
     * generate a random tetromino
     */
    public Tetromino(){
        this(new char[]{'I', 'S', 'O', 'L', 'Z', 'T', 'J'}[new Random().nextInt(7)]);
    }
}