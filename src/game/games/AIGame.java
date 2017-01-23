package game.games;

import game.Tetromino;
import game.gameboards.AIGameBoard;

import java.io.PrintWriter;

/**
 * Created by bradh on 1/18/2017.
 */
public class AIGame extends SinglePlayerGame implements AutoPlayable{

    public AIGame(PrintWriter output){
        super();

        this.output = output;

        myGameBoard = new AIGameBoard();
        myGameBoard.setBounds(170, 155, 338, 546);
        getContentPane().add(myGameBoard);

        new Thread(this :: AIGameGo).start();
    }

    public void run(){
        this.setVisible(true);
        super.run();
    }

    @Override
    public void AIGameGo(){
        while (!gameOver){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            if (((AIGameBoard)myGameBoard).hasUnhandledTetromino){
                ((AIGameBoard)myGameBoard).moveToBestPosition(this.myHoldTetromino != null ? this.myHoldTetromino : (Tetromino)this.myTetrominoQueue.peek(1)[0]);
            }
        }
    }
}