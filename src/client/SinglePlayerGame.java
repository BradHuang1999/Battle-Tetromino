package client;

import client.queue.Queue;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class SinglePlayerGame extends TetrisGame{
    public SinglePlayerGame() {
        super();

        setSize(680, 768);
        setLocationRelativeTo(null);

        myGameBoard = new GameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);

        iconLabel.setBounds(140, 39, 400, 50);
        levelLabel.setBounds(280, 100, 120, 36);

        getContentPane().add(myGameBoard);
    }
}