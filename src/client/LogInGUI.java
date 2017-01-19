package client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        ActionListener imgButtonAC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO set up a variable that indicates which image is clicked
                // TODO also indicate that the button is clicked
            }
        };

        ImageIcon img2 = new ImageIcon("resources/emojiDevil.png");
        Image scaledImg2 = img2.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JButton btnImg2 = new JButton("");
        btnImg2.setIcon(new ImageIcon(scaledImg2));
        btnImg2.setBounds(197, 90, 32, 32);
        btnImg2.addActionListener(imgButtonAC);
        getContentPane().add(btnImg2);

        ImageIcon img3 = new ImageIcon("resources/emojiFun.png");
        Image scaledImg3 = img3.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JButton btnImg3 = new JButton("");
        btnImg3.setIcon(new ImageIcon(scaledImg3));
        btnImg3.setBounds(239, 90, 32, 32);
        btnImg3.addActionListener(imgButtonAC);
        getContentPane().add(btnImg3);
        
        ImageIcon img4 = new ImageIcon("resources/emojiHaha.png");
        Image scaledImg4 = img4.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JButton btnImg4 = new JButton("");
        btnImg4.setIcon(new ImageIcon(scaledImg4));
        btnImg4.setBounds(281, 90, 32, 32);
        btnImg4.addActionListener(imgButtonAC);
        getContentPane().add(btnImg4);

        ImageIcon img5 = new ImageIcon("resources/emojiLaugh.png");
        Image scaledImg5 = img5.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JButton btnImg5 = new JButton("");
        btnImg5.setIcon(new ImageIcon(scaledImg5));
        btnImg5.setBounds(323, 90, 32, 32);
        btnImg5.addActionListener(imgButtonAC);
        getContentPane().add(btnImg5);

        ImageIcon img6 = new ImageIcon("resources/emojiSmirk.png");
        Image scaledImg6 = img6.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        JButton btnImg6 = new JButton("");
        btnImg6.setIcon(new ImageIcon(scaledImg6));
        btnImg6.setBounds(365, 90, 32, 32);
        btnImg6.addActionListener(imgButtonAC);
        getContentPane().add(btnImg6);
    }
}