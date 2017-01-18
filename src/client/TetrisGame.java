package client;

import client.queue.Queue;

import javax.swing.*;

import java.awt.*;


abstract class TetrisGame extends JFrame {
    protected String mode;

    protected boolean playing;
    protected boolean gameOver = false;

    protected int level = 1;

    public TetrisGame() {
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        ImageIcon img = new ImageIcon("resources/tetronimo.png");
        lblNewLabel.setIcon(img);
        lblNewLabel.setBounds(459, 39, 460, 51);
        getContentPane().add(lblNewLabel);

        JLabel lblLevel = new JLabel("LEVEL " + level);
        lblLevel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblLevel.setBounds(612, 89, 77, 28);
        getContentPane().add(lblLevel);

        JLabel lblHold = new JLabel("Hold");
        lblHold.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblHold.setBounds(42, 208, 100, 22);
        lblHold.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblHold);

        JLabel lblNewLabel_1 = new JLabel("");
        ImageIcon img1 = new ImageIcon("resources/hold.png");
        lblNewLabel_1.setIcon(img1);
        lblNewLabel_1.setBounds(60, 241, 64, 64);
        getContentPane().add(lblNewLabel_1);

        JLabel lblScore = new JLabel("Score");
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblScore.setBounds(42, 416, 100, 22);
        getContentPane().add(lblScore);

        JTextField textField = new JTextField();
        textField.setBounds(42, 449, 100, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblLines = new JLabel("Lines");
        lblLines.setHorizontalAlignment(SwingConstants.CENTER);
        lblLines.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLines.setBounds(42, 491, 100, 22);
        getContentPane().add(lblLines);

        JTextField textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(42, 524, 100, 20);
        getContentPane().add(textField_1);

        JLabel lblNext = new JLabel("Next");
        lblNext.setHorizontalAlignment(SwingConstants.CENTER);
        lblNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNext.setBounds(522, 208, 100, 22);
        getContentPane().add(lblNext);

        JLabel label_1 = new JLabel("");
        label_1.setBounds(540, 241, 64, 64);
        getContentPane().add(label_1);

        JLabel label = new JLabel("");
        label.setBounds(540, 316, 64, 64);
        getContentPane().add(label);

        JLabel label_2 = new JLabel("");
        label_2.setBounds(540, 391, 64, 64);
        getContentPane().add(label_2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);
    }
}