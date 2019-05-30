package client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.BoardModel;
import shared.CharacterIcon;
import shared.Value;

/**
 * Class that handles building the value-window that opens when the user clicks the "Open value-menu" button in the context menu for icons.
 * 
 * @author Oscar Strandmark, Patrik Skuza
 */
public class ValueWindow extends JFrame {

	private static final long serialVersionUID = 4356499130491471637L;

	private JPanel contentPane;
	
	private JTable table;
	
	private JButton btnAddrow;
	private JButton btnRemoveRow;
	private JButton btnClose;
	
	private BoardModel model;
	private ImageIcon img;
	private int index;
	
	// Constructor opens the values for the current icon.
	public ValueWindow(BoardModel model, ImageIcon img) {
		this.model = model;
		this.img = img;
		init();
		getValues();
	}
	
	// Initialize value-menu GUI.
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Values");
		setSize(600, 400);
		
		contentPane = new JPanel(new BorderLayout());
		
		table = new JTable(new DefaultTableModel(new String[]{"Value name", "Value"},0));
	
		JScrollPane tablePane = new JScrollPane();
		tablePane.setViewportView(table);
		contentPane.add(tablePane, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel(new GridLayout(1,3));
	
		
		btnAddrow = new JButton("Add new value");;
		btnRemoveRow = new JButton("Remove selected value");;
		btnClose = new JButton("Save & Close");;
		
		
		buttonPane.add(btnAddrow);
		buttonPane.add(btnRemoveRow);
		buttonPane.add(btnClose);
		contentPane.add(buttonPane,BorderLayout.SOUTH);
		
		//Table config
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		//Buttonlisteners
		Listener l = new Listener();
		
		btnAddrow.addActionListener(l);
		btnRemoveRow.addActionListener(l);
		btnClose.addActionListener(l);
		
		setContentPane(contentPane);
		setVisible(true);
	}
	
	// Method for getting values of current icon.
	private void getValues() {
		index = model.lookupIndex(img);
		CharacterIcon c = model.getChar(index);
		ArrayList<Value> list = c.getValueList();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for(Value v : list) {
			model.addRow(new String[] {v.getName(),v.getValue()});
		}
		
		table.setModel(model);
	}
	
	//Method for setting values of current icon.
	private void setValues() {
		ArrayList<Value> list = new ArrayList<Value>();
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			String name = (String)tableModel.getValueAt(i, 0);
			String value =(String)tableModel.getValueAt(i, 1);
			list.add(new Value(value, name));
		}
		
		model.sendValueUpdate(index, list);
	}
	
	//Inner class acts as a listener.
	private class Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		
			if(e.getSource() == btnAddrow) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new String[] {"NAME","VALUE"});
				table.setModel(model);
			}
			
			else
				
			if(e.getSource() == btnRemoveRow) {
				int i = table.getSelectedRow();
				if(i != -1) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(i);
					table.setModel(model);
				}
			}
			
			else
				
			if(e.getSource() == btnClose) {
				setValues();
				dispose();
			}
		}
	}
}
