package client;

import java.awt.Image;

import javax.swing.*;

public class LogInGUI extends JFrame{
    private JTextField usernameField, ipField, portField;

    public static void main(String[] args){
        new LogInGUI().setVisible(true);
    }

    public LogInGUI(){
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setTitle("Battle Tetromino Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon img1 = new ImageIcon("resources/tetronimo.png");
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(img1);
        lblNewLabel.setBounds(50, 22, 400, 50);
        getContentPane().add(lblNewLabel);

        JLabel lblNickname = new JLabel("Nickname :");
        lblNickname.setBounds(80, 142, 109, 14);
        getContentPane().add(lblNickname);

        JLabel lblIp = new JLabel("IP Address :");
        lblIp.setBounds(80, 178, 109, 14);
        getContentPane().add(lblIp);

        JLabel lblPort = new JLabel("Port :");
        lblPort.setBounds(335, 178, 41, 14);
        getContentPane().add(lblPort);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBounds(185, 218, 126, 34);
        btnNewButton.addActionListener(e -> {
            dispose();
            new WaitLoungeGUI().setVisible(true);
        });
        getContentPane().add(btnNewButton);

        usernameField = new JTextField();
        usernameField.setBounds(180, 140, 200, 24);
        getContentPane().add(usernameField);
        usernameField.setColumns(10);

        ipField = new JTextField();
        ipField.setColumns(10);
        ipField.setBounds(180, 175, 120, 24);
        ipField.setText("127.0.0.1");
        getContentPane().add(ipField);

        portField = new JTextField();
        portField.setColumns(10);
        portField.setBounds(385, 175, 40, 24);
        portField.setText("1234");
        getContentPane().add(portField);

        JLabel lblCooseAPic = new JLabel("Coose a pic :");
        lblCooseAPic.setBounds(78, 95, 109, 14);
        getContentPane().add(lblCooseAPic);

        ImageIcon img2 = new ImageIcon("resources/emojiDevil.png");
        Image newimg2 = img2.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(newimg2));
        lblNewLabel_1.setBounds(197, 90, 32, 32);
        getContentPane().add(lblNewLabel_1);

        ImageIcon img3 = new ImageIcon("resources/emojiFun.png");
        Image newimg3 = img3.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(newimg3));
        label.setBounds(239, 90, 32, 32);
        getContentPane().add(label);

        ImageIcon img4 = new ImageIcon("resources/emojiHaha.png");
        Image newimg4 = img4.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JLabel label_1 = new JLabel("");
        label_1.setIcon(new ImageIcon(newimg4));
        label_1.setBounds(281, 90, 32, 32);
        getContentPane().add(label_1);

        ImageIcon img5 = new ImageIcon("resources/emojiLaugh.png");
        Image newimg5 = img5.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon(newimg5));
        label_2.setBounds(323, 90, 32, 32);
        getContentPane().add(label_2);

        ImageIcon img6 = new ImageIcon("resources/emojiSmirk.png");
        Image newimg6 = img6.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JLabel label_3 = new JLabel("");
        label_3.setIcon(new ImageIcon(newimg6));
        label_3.setBounds(365, 90, 32, 32);
        getContentPane().add(label_3);
    }
}