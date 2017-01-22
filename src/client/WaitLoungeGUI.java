package client;

import game.games.AIGame;
import game.games.HumanVsAIGame;
import game.games.OnlineBattleGame;
import game.games.SoloGame;

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
import java.util.*;
import java.util.List;

public class WaitLoungeGUI extends JFrame {
	private JTable table;
	private JTextField textField;
	private JTextArea textField_1;
    private JTextPane textPane;

	public WaitLoungeGUI(){
        getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(650, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(LogInGUI.getUsername() + "'s Waiting Lounge");
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 21, 592, 250);
        getContentPane().add(scrollPane);

        table = new JTable();

        DefaultTableModel dm = new DefaultTableModel(0, 0);
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
        lblWarning.setBounds(500,376,120,14);
        lblWarning.setForeground(Color.RED);
        getContentPane().add(lblWarning);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnNewRadioButton);
        group.add(rdbtnWha);
        group.add(rdbtnHumanVs);
        group.add(rdbtnOLBattle);
        group.add(rdbtnBattle);
        group.setSelected(rdbtnNewRadioButton.getModel(), true);

        JButton btnNewButton = new JButton("Game!");
        btnNewButton.setBounds(452, 605, 118, 34);
        getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(e -> {
            if (textField.getText().isEmpty()){
                lblWarning.setText("*Enter a name first!");
                return;
            } else {
                lblWarning.setText("");
                Vector<Object> data = new Vector<Object>();
                data.add(textField.getText());
                if (rdbtnNewRadioButton.isSelected()){
                    data.add("Solo");
                    data.add("1/1");
                } else if (rdbtnWha.isSelected()){
                    data.add("Watch AI");
                    data.add("1/1");
                    //TODO: Just for test purpose
                    data.add("Play unclickable");
                    dm.addRow(data);
                    return;
                    //
                } else if (rdbtnBattle.isSelected()){
                    data.add("Battle");
                    data.add("2/2");
                } else if (rdbtnOLBattle.isSelected()){
                    data.add("OnlineBattle");
                    data.add("1/2");
                } else if (rdbtnHumanVs.isSelected()){
                    data.add("Human vs AI");
                    data.add("1/2");
                }
                data.add("Play");
                dm.addRow(data);
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
            if (!textField_1.getText().isEmpty()){        // if the textField is empty
                String msg = textField_1.getText();

//                // send the message to server
//                client.output.println("SendMessage\n" + friend.getUserName() + "\n" + new Date() + "\n" + msg);
//                client.output.flush();

                // append the message to the chat window
                appendToPane("Me " + new Date(), Color.BLUE);
                appendToPane(msg, Color.BLACK);
                appendToPane("", Color.BLACK);
                textField_1.setText("");
            }
        });
    }
	
	class CompCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	    private static final long serialVersionUID = 1L;
	    private String lastSelected = null;

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    	
        	return getComponent(value, row, column);
	    }
	    
	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    	if(isSelected && value != null){
	    		lastSelected = value.toString();
	    	}
	    	return getComponent(value, row, column);
	    }

		private Component getComponent(Object value, int row, int column) {
			if(value != null && (value.toString().equals("Play"))){
				JPanel p = new JPanel();
				p.setLayout(null);
        		JButton jButton = new JButton("play");
        		jButton.setBounds(5, 5, 62, 20);
        		jButton.addActionListener(e ->{
        			Object mode = table.getValueAt(row, 1);
        			switch (mode.toString()){
                        case "Solo":
                            new SoloGame().setVisible(true);
                            break;
                        case "Watch AI":
                            new AIGame().setVisible(true);
                            break;
                        case "Human VS AI":
                            new HumanVsAIGame().setVisible(true);
                            break;
                        case "Battle":
                            new OnlineBattleGame().setVisible(true);
                            break;
					}
        			lastSelected = "Play";
        		});
        		JButton viewButton = new JButton("view");
        		viewButton.addActionListener(e ->{
        			lastSelected = "Play";
        		});
        		viewButton.setBounds(74, 5, 62, 20);
        		p.add(jButton);
        		p.add(viewButton);
        		return p;
        	}else if(value != null && value.toString().equals("Play unclickable")){
        		JPanel p = new JPanel();
				p.setLayout(null);
        		JButton jButton = new JButton("play");
        		jButton.setBounds(5, 5, 62, 20);
        		jButton.setEnabled(false);
        		JButton viewButton = new JButton("view");
        		viewButton.addActionListener(e ->{
        			lastSelected = "Play";
        		});
        		viewButton.setBounds(74, 5, 62, 20);
        		p.add(jButton);
        		p.add(viewButton);
        		lastSelected = "Play unclickable";
        		return p;
        	}
			lastSelected = value == null ? null : value.toString();
            return (value == null || value.toString() == null) ? null : new JLabel(value.toString());
		}
		
	    @Override
	    public Object getCellEditorValue() {
	        return lastSelected;
	    }

	    @Override
	    public boolean isCellEditable(EventObject anEvent) {	    	
	        return ((JTable)anEvent.getSource()).getSelectedColumn() >= 3;
	    }

	    @Override
	    public boolean shouldSelectCell(EventObject anEvent) {
	        return false;
	    }
	}
	
	public static void main(String[] args) {
		new WaitLoungeGUI().setVisible(true);
	}

    /**
     * append the message to the textArea pane
     *
     * @param msg   message
     * @param color color of the message
     */
    public void appendToPane(String msg, Color color) {
        if (msg.length() < 10 || (!msg.substring(0, 10).equals("//emoji//-"))) {
            msg = msg + "\n";
            appendToPane(textPane, msg, color);
        } else {
            appendToPane(textPane, " ", color);
            textPane.insertIcon(new ImageIcon(msg.substring(10)));
            appendToPane(textPane, "\n", color);
        }
    }

    /**
     * append the message to pane
     *
     * @param tp  pane to append
     * @param msg message to append
     * @param c   color of message
     */
    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
