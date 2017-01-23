package client;

import game.Tetromino;
import game.games.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author Brad Huang and Charlie Lin
 * @date Jan 19, 2017
 */
public class WaitLoungeGUI extends JFrame{
    private JTable table;
    private JTextField textField;
    private JTextArea textField_1;
    private JTextPane textPane;

    private TetrominoClient client;
    private DefaultTableModel dm;

    /**
     * create wait GUI
     *
     * @param client the client for I/O use
     * @throws IOException
     */
    public WaitLoungeGUI(TetrominoClient client) throws IOException{
        this.client = client;

        // GUI stuff
        getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(650, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(client.nickName + "'s Waiting Lounge");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 21, 592, 250);
        getContentPane().add(scrollPane);

        addWindowListener(new WindowListener(){
            public void windowClosed(WindowEvent e){
            }

            public void windowOpened(WindowEvent e){
            }

            public void windowClosing(WindowEvent e){
                client.close();
            }

            public void windowIconified(WindowEvent e){
            }

            public void windowDeiconified(WindowEvent e){
            }

            public void windowActivated(WindowEvent e){
            }

            public void windowDeactivated(WindowEvent e){
            }
        });

        table = new JTable();

        dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"Game Name", "Game Type", "Players", ""};
        dm.setColumnIdentifiers(header);
        table.setModel(dm);

        table.setRowHeight(30);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        CompCellEditorRenderer compCellEditorRenderer = new CompCellEditorRenderer();
        table.setDefaultRenderer(Object.class, compCellEditorRenderer);
        table.setDefaultEditor(Object.class, compCellEditorRenderer);

        table.setBounds(24, 21, 592, 250);
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));

        JLabel lblMessages = new JLabel("MESSAGES");
        lblMessages.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblMessages.setBounds(24, 294, 210, 26);
        getContentPane().add(lblMessages);

        JLabel lblCreateNew = new JLabel("CREATE NEW GAME");
        lblCreateNew.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblCreateNew.setBounds(389, 294, 227, 26);
        getContentPane().add(lblCreateNew);

        JLabel lblGameName = new JLabel("Game Name:");
        lblGameName.setBounds(395, 348, 80, 14);
        getContentPane().add(lblGameName);

        textField = new JTextField();
        textField.setBounds(473, 345, 150, 25);
        getContentPane().add(textField);
        textField.setColumns(10);

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Solo Game");
        rdbtnNewRadioButton.setBounds(460, 400, 109, 23);
        getContentPane().add(rdbtnNewRadioButton);

        JRadioButton rdbtnWha = new JRadioButton("Watch AI");
        rdbtnWha.setBounds(460, 440, 109, 23);
        getContentPane().add(rdbtnWha);

        JRadioButton rdbtnHumanVs = new JRadioButton("Human vs AI");
        rdbtnHumanVs.setBounds(460, 480, 109, 23);
        getContentPane().add(rdbtnHumanVs);

        JRadioButton rdbtnBattle = new JRadioButton("Local Battle");
        rdbtnBattle.setBounds(460, 520, 109, 23);
        getContentPane().add(rdbtnBattle);

        JRadioButton rdbtnOLBattle = new JRadioButton("Online Battle");
        rdbtnOLBattle.setBounds(460, 560, 109, 23);
        getContentPane().add(rdbtnOLBattle);

        JLabel lblWarning = new JLabel();
        lblWarning.setBounds(420, 376, 195, 14);
        lblWarning.setForeground(Color.RED);
        lblWarning.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(lblWarning);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnNewRadioButton);
        group.add(rdbtnWha);
        group.add(rdbtnHumanVs);
        group.add(rdbtnOLBattle);
        group.add(rdbtnBattle);
        group.setSelected(rdbtnNewRadioButton.getModel(), true);

        JButton btnNewButton = new JButton("Create Room");
        btnNewButton.setBounds(452, 605, 118, 34);
        getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(e -> {
            if (textField.getText().isEmpty()){
                lblWarning.setText("*Enter a name first");
            } else {
                for (int i = 0; i < dm.getRowCount(); i++){
                    if (dm.getValueAt(i, 0).equals(textField.getText())){
                        lblWarning.setText("*Please enter a different name");
                        return;
                    }
                }
                lblWarning.setText("");

                client.output.println("**createRoom\n" + textField.getText());      // send messages to server
                if (rdbtnNewRadioButton.isSelected()){
                    client.output.println("Solo");
                } else if (rdbtnWha.isSelected()){
                    client.output.println("Watch AI");
                } else if (rdbtnBattle.isSelected()){
                    client.output.println("Battle");
                } else if (rdbtnOLBattle.isSelected()){
                    client.output.println("Online Battle");
                } else if (rdbtnHumanVs.isSelected()){
                    client.output.println("Human vs AI");
                }
                client.output.flush();
            }
        });

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(24, 331, 350, 232);
        getContentPane().add(scrollPane_1);

        textPane = new JTextPane();
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textPane.setMargin(new Insets(5, 5, 5, 5));
        textPane.setFocusable(false);
        scrollPane_1.setViewportView(textPane);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(24, 573, 271, 80);
        getContentPane().add(scrollPane_2);

        textField_1 = new JTextArea();
        textField_1.setRows(2);
        scrollPane_2.setViewportView(textField_1);

        JButton btnSend = new JButton("Send");
        btnSend.setBounds(302, 578, 70, 70);
        getContentPane().add(btnSend);
        btnSend.addActionListener(e -> {
            if (!textField_1.getText().isEmpty()){
                String msg = textField_1.getText();

                // send the message to server
                client.output.println("**sendMessage\n" + client.nickName + "\n" + client.iconPath + "\n" + new Date() + "\n" + msg);
                client.output.flush();

                // append the message to the chat window
                try {
                    appendMyFirstLineToPane(new Date().toString());
                    appendToPane(msg, Color.BLACK);
                } catch (IOException e1){
                    e1.printStackTrace();
                }

                textField_1.setText("");
            }
        });

        new Thread(this :: getUpdate).start();
    }

    /**
     * get updates from server and handle them
     */
    public void getUpdate(){
        client.outputOpen = true;
        String userInput, userInput1, userInput2, userInput3, userInput4;
        while (client.outputOpen){
            try {
                if (client.input.ready()){
                    userInput = client.input.readLine();
                    switch (userInput){
                        case "**message":
                            userInput1 = client.input.readLine();  // nickname
                            userInput2 = client.input.readLine();  // icon
                            userInput3 = client.input.readLine();  // timeStamp
                            userInput4 = client.input.readLine();  // message
                            appendFirstLineToPane(userInput2, userInput1, userInput3);
                            appendToPane(userInput4, Color.BLACK);
                            break;

                        case "**gameRoom":
                            boolean identified = false;

                            userInput1 = client.input.readLine();  // gameroom name
                            userInput2 = client.input.readLine();  // game type
                            userInput3 = client.input.readLine();  // player count
                            userInput4 = client.input.readLine();  // max count

                            for (int i = 0; i < table.getRowCount(); i++){
                                if (userInput1.equals(table.getValueAt(i, 0))){
                                    identified = true;
                                    if (userInput2 != table.getValueAt(i, 1)){
                                        table.setValueAt(userInput2, i, 1);
                                    }
                                    if ((userInput3 + "/" + userInput4) != table.getValueAt(i, 2)){
                                        table.setValueAt((userInput3 + "/" + userInput4), i, 2);
                                        if (Integer.valueOf(userInput3) >= Integer.valueOf(userInput4)){
                                            table.setValueAt("Play unclickable", i, 3);
                                        } else {
                                            table.setValueAt("Play", i, 3);
                                        }
                                    }
                                }
                            }

                            if (!identified){
                                Vector<Object> data = new Vector<Object>();
                                data.add(userInput1);
                                data.add(userInput2);
                                data.add(userInput3 + "/" + userInput4);
                                if (Integer.valueOf(userInput3) >= Integer.valueOf(userInput4)){
                                    data.add("Play unclickable");
                                } else {
                                    data.add("Play");
                                }
                                dm.addRow(data);
                            }
                            break;

                        case "**permission to play":
                            userInput1 = client.input.readLine();  // game name
                            for (int i = 0; i < dm.getRowCount(); i++){
                                if (userInput1.equals(dm.getValueAt(i, 0))){
                                    switch (dm.getValueAt(i, 1).toString()){
                                        case "Solo":
                                            client.game = new SoloGame(client.output);
                                            break;
                                        case "Watch AI":
                                            client.game = new AIGame(client.output);
                                            break;
                                        case "Human vs AI":
                                            client.game = new HumanVsAIGame(client.nickName, client.iconPath, client.output);
                                            break;
                                        case "Battle":
                                            client.game = new LocalBattleGame(client.nickName, client.iconPath, client.output);
                                            break;
                                        case "Online Battle":
                                            client.game = new OnlineBattleGame(client.nickName, client.iconPath, client.output);
                                            break;
                                    }
                                    client.game.setGameName(userInput1);
                                    new Thread(() -> client.game.run()).start();
                                }
                            }
                            break;

                        case "**permission to view":
                            break;

                        case "**onlineGame":
                            userInput3 = client.input.readLine();   // roomName
                            userInput1 = client.input.readLine();   // name of command
                            userInput2 = client.input.readLine();   // command data

                            if (userInput1.equals("enqueueTetromino")){
                                client.game.enqueueTetromino(new Tetromino(userInput2.charAt(0)));
                            } else {
                                ((OnlineBattleGame)client.game).handleOpponent(userInput1, userInput2);
                            }
                            break;

                        case "**viewGameP1":
                            // TODO
                            break;

                        case "**viewGameP2":
                            // TODO
                            break;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * combine cell editor and renderer for display use
     */
    class CompCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor{
        private String lastSelected = null;

        /**
         * render cells
         *
         * @param table
         * @param value
         * @param isSelected
         * @param hasFocus
         * @param row
         * @param column
         * @return a component to render
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            return getComponent(value, row);
        }

        /**
         * edit cells
         *
         * @param table
         * @param value
         * @param isSelected
         * @param row
         * @param column
         * @return
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            if (isSelected && value != null){
                lastSelected = value.toString();
            }
            return getComponent(value, row);
        }

        /**
         * generate component
         *
         * @param value
         * @param row
         * @return
         */
        private Component getComponent(Object value, int row){
            if (value != null && (value.toString().equals("Play"))){
                JPanel p = new JPanel();
                p.setLayout(null);

                JButton jButton = new JButton("PLAY!");
                jButton.setBounds(3, 3, 140, 24);
                jButton.addActionListener(e -> {
                    client.output.println("**enterRoomAndPlay\n" + table.getValueAt(row, 0));
                    client.output.flush();
                    lastSelected = "Play";
                });

                p.add(jButton);
                return p;

            } else if (value != null && value.toString().equals("Play unclickable")){
                JPanel p = new JPanel();
                p.setLayout(null);

                JButton jButton = new JButton("play");
                jButton.setBounds(3, 3, 140, 24);
                jButton.setEnabled(false);

                lastSelected = "Play unclickable";

                p.add(jButton);
                return p;
            }
            lastSelected = value.toString();
            return new JLabel(lastSelected);
        }

        /**
         * get cell editor value
         *
         * @return the valur selected to put back
         */
        @Override
        public Object getCellEditorValue(){
            return lastSelected;
        }

        /**
         * to prevent cell changing
         *
         * @param anEvent
         * @return if cell can be clicked and changed
         */
        @Override
        public boolean isCellEditable(EventObject anEvent){
            return ((JTable)anEvent.getSource()).getSelectedColumn() >= 3;
        }
    }

    /**
     * append first line of the message to the textpang
     *
     * @param path      file path to the icon of the user, must start with "rescource/"
     * @param nickName  nickname of the use
     * @param timeStamp timestamp
     * @throws IOException
     */
    private void appendFirstLineToPane(String path, String nickName, String timeStamp) throws IOException{
        Image img = ImageIO.read(new File(path)).getScaledInstance(28, 28, java.awt.Image.SCALE_SMOOTH);
        appendToPane(textPane, "", Color.BLUE);
        textPane.insertIcon(new ImageIcon(img));
        appendToPane(textPane, nickName + " @ " + timeStamp + "\n", new Color(10, 90, 20));
    }

    /**
     * append line sent by the user
     *
     * @param timeStamp timestamp of user
     * @throws IOException
     */
    private void appendMyFirstLineToPane(String timeStamp) throws IOException{
        Image img = ImageIO.read(new File(client.iconPath)).getScaledInstance(28, 28, java.awt.Image.SCALE_SMOOTH);
        appendToPane(textPane, "", Color.BLUE);
        textPane.insertIcon(new ImageIcon(img));
        appendToPane(textPane, "Me @ " + timeStamp + "\n", Color.BLUE);
    }

    /**
     * append the message to the textArea pane
     *
     * @param msg   message
     * @param color color of the message
     */
    private void appendToPane(String msg, Color color){
        appendToPane(textPane, "   " + msg + "\n\n", color);
    }

    /**
     * append the message to pane
     *
     * @param tp  pane to append
     * @param msg message to append
     * @param c   color of message
     */
    private void appendToPane(JTextPane tp, String msg, Color c){
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_CENTER);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}