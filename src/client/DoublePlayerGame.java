package client;

import client.GameBoard;
import client.TetrisGame;
import client.queue.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bradh on 1/18/2017.
 */
abstract class DoublePlayerGame extends TetrisGame {
    protected GameBoard myGameBoard, opponentGameBoard;
    protected Queue<Tetromino> myTetrominoQueue, opponentTetrominoQueue;

    protected boolean rewardMode;

    public DoublePlayerGame(){
        super();

        setSize(1360, 768);
        setLocationRelativeTo(null);

        myGameBoard = new GameBoard();
        myGameBoard.setBounds(152, 131, 360, 598);
        getContentPane().add(myGameBoard);

        opponentGameBoard = new GameBoard();
        opponentGameBoard.setBounds(783, 131, 360, 598);
        getContentPane().add(opponentGameBoard);

        JLabel label_3 = new JLabel("Next");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_3.setBounds(1153, 208, 100, 22);
        getContentPane().add(label_3);

        JLabel label_4 = new JLabel("");
        label_4.setBounds(1171, 241, 64, 64);
        getContentPane().add(label_4);

        JLabel label_5 = new JLabel("");
        label_5.setBounds(1171, 316, 64, 64);
        getContentPane().add(label_5);

        JLabel label_6 = new JLabel("");
        label_6.setBounds(1171, 391, 64, 64);
        getContentPane().add(label_6);

        JLabel label_7 = new JLabel("Hold");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_7.setBounds(673, 208, 100, 22);
        getContentPane().add(label_7);

        JLabel label_8 = new JLabel("");
        ImageIcon img8 = new ImageIcon("resources/hold.png");
        label_8.setIcon(img8);
        label_8.setBounds(690, 241, 64, 64);
        getContentPane().add(label_8);

        JLabel label_9 = new JLabel("Score");
        label_9.setHorizontalAlignment(SwingConstants.CENTER);
        label_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_9.setBounds(673, 416, 100, 22);
        getContentPane().add(label_9);

        JTextField textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(673, 449, 100, 20);
        getContentPane().add(textField_2);

        JLabel label_10 = new JLabel("Lines");
        label_10.setHorizontalAlignment(SwingConstants.CENTER);
        label_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_10.setBounds(673, 491, 100, 22);
        getContentPane().add(label_10);

        JTextField textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(673, 524, 100, 20);
        getContentPane().add(textField_3);
    }
}
