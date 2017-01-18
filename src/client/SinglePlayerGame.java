package client;

import client.queue.Queue;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class SinglePlayerGame extends TetrisGame{
    protected GameBoard myGameBoard;
    protected Queue<Tetromino> myTetrominoQueue;


    public SinglePlayerGame() {
        super();

        setSize(680, 768);

        myGameBoard = new GameBoard();
        myGameBoard.setBounds(152, 131, 360, 598);

        getContentPane().add(myGameBoard);
    }
}
