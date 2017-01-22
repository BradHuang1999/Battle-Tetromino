package game.gameboards;

import java.awt.*;

/**
 * Created by Brad Huang on 1/21/2017.
 */
public interface DoublePlayable{
    void addLinesOnTop(int lineNum);

    void gravityDrop();

    void deployRewardPiece();

    void drawPiece(Graphics g);
}
