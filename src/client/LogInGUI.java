package client;

import java.awt.Image;

import javax.swing.*;

public class LogInGUI extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public LogInGUI() {
		getContentPane().setLayout(null);
		setTitle("Battle Tetromino Login");
		
		ImageIcon img1 = new ImageIcon("resources/tetronimo.png");
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(img1);
		lblNewLabel.setBounds(53, 22, 400, 54);
		getContentPane().add(lblNewLabel);
		
		JLabel lblCooseAPic = new JLabel("Coose a pic :");
		lblCooseAPic.setBounds(78, 95, 109, 14);
		getContentPane().add(lblCooseAPic);
		
		JLabel lblNickname = new JLabel("Nickname :");
		lblNickname.setBounds(78, 134, 109, 14);
		getContentPane().add(lblNickname);
		
		JLabel lblIp = new JLabel("Ip :");
		lblIp.setBounds(78, 165, 109, 14);
		getContentPane().add(lblIp);
		
		JLabel lblPort = new JLabel("Port :");
		lblPort.setBounds(323, 165, 41, 14);
		getContentPane().add(lblPort);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(185, 209, 126, 34);
		btnNewButton.addActionListener(e ->{
			dispose();
			new WaitLoungeGUI().setVisible(true);
		});
		getContentPane().add(btnNewButton);
		
		ImageIcon img2 = new ImageIcon("resources/emojiDevil.png");
		Image newimg2 = img2.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(newimg2));
		lblNewLabel_1.setBounds(197, 86, 32, 32);
		getContentPane().add(lblNewLabel_1);
		
		ImageIcon img3 = new ImageIcon("resources/emojiFun.png");
		Image newimg3 = img3.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(newimg3));
		label.setBounds(239, 86, 32, 32);
		getContentPane().add(label);
		
		ImageIcon img4 = new ImageIcon("resources/emojiHaha.png");
		Image newimg4 = img4.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(newimg4));
		label_1.setBounds(281, 86, 32, 32);
		getContentPane().add(label_1);
		
		ImageIcon img5 = new ImageIcon("resources/emojiLaugh.png");
		Image newimg5 = img5.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(newimg5));
		label_2.setBounds(323, 86, 32, 32);
		getContentPane().add(label_2);
		
		ImageIcon img6 = new ImageIcon("resources/emojiSmirk.png");
		Image newimg6 = img6.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(newimg6));
		label_3.setBounds(365, 86, 32, 32);
		getContentPane().add(label_3);
		
		textField = new JTextField();
		textField.setBounds(197, 131, 114, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(197, 162, 114, 20);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(373, 162, 58, 20);
		getContentPane().add(textField_2);
		
		setSize(500, 300);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new LogInGUI().setVisible(true);
	}
}
