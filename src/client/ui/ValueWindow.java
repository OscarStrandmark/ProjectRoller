package client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import shared.BoardModel;
import shared.CharacterIcon;
import shared.Value;

public class ValueWindow extends JFrame {

	private JPanel contentPane;
	
	private JTable table;
	
	private JButton btnAddrow;
	private JButton btnRemoveRow;
	private JButton btnClose;
	
	private BoardModel model;
	private JLabel label;
	
	public ValueWindow(BoardModel model, JLabel label) {
		this.model = model;
		this.label = label;
		init();
		getValues();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	private void getValues() {
		CharacterIcon icon = model.lookup(label);
		Iterator<Value> iter = icon.getValueIterator();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		while(iter.hasNext()) {
			Value val = iter.next();
			
			model.addRow(new String[] {val.getName(),val.getValue()});
		}
		
		table.setModel(model);
	}
	
	private void setValues() {
		CharacterIcon icon = model.lookup(label);
		ArrayList<Value> list = new ArrayList<Value>();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (int i = 0; i < model.getRowCount(); i++) {
			String name = (String)model.getValueAt(i, 0);
			String value =(String)model.getValueAt(i, 1);
			list.add(new Value(value, name));
		}
		
		icon.setValues(list);
		this.model.synchToServer();
	}
	
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
