package game.gameboards;

import java.awt.*;

/**
 * @author Brad Huang
 * interface for double player gameboards
 * @date Jan 21, 2017
 */
public interface DoublePlayable{
    /**
     * add lines on top of the map
     * @param lineNum lines to be added
     */
    void addLinesOnTop(int lineNum);

    /**
     * gravity drop
     */
    void gravityDrop();

    /**
     * deploy reward piece
     * @return piece position to be deployed
     */
    Point deployRewardPiece();

    /**
     * override drawPiece method
     * @param g graphics to be drawn
     */
    void drawPiece(Graphics g);
}
