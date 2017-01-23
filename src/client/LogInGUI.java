package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

/**
 * @author Brad Huang
 * Log In page GUI
 */
public class LogInGUI extends JFrame{
    private static JTextField usernameField;
	private JTextField ipField, portField;

    public LogInGUI(TetrominoClient client){
        // GUI Stuff
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setTitle("Battle Tetromino Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                client.close();
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

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

        JLabel lblChooseAPic = new JLabel("Choose a pic :");
        lblChooseAPic.setBounds(78, 95, 109, 14);
        getContentPane().add(lblChooseAPic);

        ImageIcon[] imageIcons = new ImageIcon[5];
        Image[] scaledImgs = new Image[5];
        JButton[] btnImgs = new JButton[5];
        String[] iconSources = {"resources/emojiDevil.png", "resources/emojiFun.png", "resources/emojiHaha.png", "resources/emojiLaugh.png", "resources/emojiSmirk.png"};

        ActionListener imgButtonAC = e -> {
            for(Component comp : getContentPane().getComponents()){
                if(comp.getClass().toString().equals(JButton.class.toString())){
                    comp.setBackground(new JButton().getBackground());
                }
            }
            JButton bt = (JButton) e.getSource();
            bt.setBackground(Color.BLACK);
            client.iconPath = iconSources[(bt.getBounds().x - 197) / 42];
        };

        for (int i = 0; i < 5; i++){
            imageIcons[i] = new ImageIcon(iconSources[i]);
            scaledImgs[i] = imageIcons[i].getImage().getScaledInstance(28, 28, java.awt.Image.SCALE_SMOOTH);
            btnImgs[i] = new JButton("");
            btnImgs[i].setIcon(new ImageIcon(scaledImgs[i]));
            btnImgs[i].setBounds(197 + 42 * i, 90, 32, 32);
            btnImgs[i].addActionListener(imgButtonAC);
            getContentPane().add(btnImgs[i]);
        }

        JLabel lblWarning = new JLabel();
        lblWarning.setBounds(0,205,500,14);
        lblWarning.setForeground(Color.RED);
        lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblWarning);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBounds(200, 230, 100, 24);
        btnNewButton.addActionListener(e -> {
            if (usernameField.getText().equals("")){
                lblWarning.setText("*Please enter username");
            } else if (client.iconPath == null){
                lblWarning.setText("*Please choose a picture");
            } else {
                try {
                    // attempt to make a connection
                    System.out.println("Attempting to make a connection..");

                    client.nickName = usernameField.getText();

                    client.socket = new Socket(ipField.getText(), Integer.valueOf(portField.getText()));  // attempt socket connection (local address). This will wait until a connection is made

                    InputStreamReader stream1 = new InputStreamReader(client.socket.getInputStream()); // stream for network input
                    client.input = new ClientBufferedReader(stream1);
                    client.output = new PrintWriter(client.socket.getOutputStream()); // assign printwriter to network stream

                    client.output.println("**login\n" + usernameField.getText() + "\n" + client.iconPath);
                    client.output.flush();

                    client.outputOpen = true;
                    while(client.outputOpen){
                        if (client.input.ready()){
                            if (client.input.readLine().equals("**successful")){
                                client.outputOpen = false;
                            }
                        }
                    }

                    System.out.println("Connection made.");
                    dispose();

                    new WaitLoungeGUI(client).setVisible(true);     // if successful then go to wait lounge
                } catch (IOException ex){
                    // connection error occuring
                    System.out.println("Connection to Server Failed");
                    System.exit(-1);
                }
            }
        });
        getContentPane().add(btnNewButton);
    }
}