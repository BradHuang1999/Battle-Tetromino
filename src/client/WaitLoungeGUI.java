package client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.EventObject;
import java.util.Vector;

public class WaitLoungeGUI extends JFrame {
	private JTable table;
	private JTextField textField;

	public static void main(String[] args) {
		new WaitLoungeGUI().setVisible(true);
	}

	public WaitLoungeGUI() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(650, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 21, 592, 250);
		getContentPane().add(scrollPane);

		table = new JTable();
		
		DefaultTableModel dm = new DefaultTableModel(0, 0);
	    String header[] = new String[] { "Game Name", "Game Type", "Players", "" };
	    dm.setColumnIdentifiers(header);
	    table.setModel(dm);

	    table.setRowHeight(25);
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
		lblGameName.setBounds(389, 339, 74, 14);
		getContentPane().add(lblGameName);
		
		textField = new JTextField();
		textField.setBounds(473, 336, 143, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Solo");
		rdbtnNewRadioButton.setBounds(389, 366, 109, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnWha = new JRadioButton("Watch AI");
		rdbtnWha.setBounds(507, 366, 109, 23);
		getContentPane().add(rdbtnWha);
		
		JRadioButton rdbtnBattle = new JRadioButton("Battle");
		rdbtnBattle.setBounds(389, 392, 109, 23);
		getContentPane().add(rdbtnBattle);
		
		JRadioButton rdbtnHumanVs = new JRadioButton("Human Vs AI");
		rdbtnHumanVs.setBounds(507, 392, 109, 23);
		getContentPane().add(rdbtnHumanVs);
		
		JButton btnNewButton = new JButton("Game!");
		btnNewButton.setBounds(441, 422, 126, 34);
		getContentPane().add(btnNewButton);
		btnNewButton.addActionListener( e ->{
			Vector<Object> data = new Vector<Object>();
	        data.add(textField.getText());
	        if(rdbtnNewRadioButton.isSelected()){
	        	data.add("Solo");
	        	data.add("1/1");
	        }else if(rdbtnWha.isSelected()){
	        	data.add("Watch AI");
	        	data.add("1/1");
	        }else if(rdbtnBattle.isSelected()){
	        	data.add("Battle");
	        	data.add("1/1");
	        }else if(rdbtnHumanVs.isSelected()){
	        	data.add("Human Vs AI");
	        	data.add("1/2");
	        }
	        data.add("Play");
	        dm.addRow(data);
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 331, 355, 130);
		getContentPane().add(scrollPane_1);
		
		JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);
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
			if(value != null && value.toString().equals("Play")){
        		JButton jButton = new JButton("play");
        		jButton.addActionListener(e ->{
        			Object mode = table.getValueAt(row, 1);
//        			new TetrisGame();
        			lastSelected = value.toString();
        		});
        		return jButton;
        	}
			lastSelected = value.toString();
            return (value == null || value.toString() == null) ? null : new JLabel(value.toString());
		}

	    @Override
	    public Object getCellEditorValue() {
	        return lastSelected;
	    }

	    @Override
	    public boolean isCellEditable(EventObject anEvent) {
	        return true;
	    }

	    @Override
	    public boolean shouldSelectCell(EventObject anEvent) {
	        return false;
	    }
	}
}
