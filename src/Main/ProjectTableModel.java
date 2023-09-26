package Main;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ProjectTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Date", "NetID / Course", "Name", "Project", "Ticket #", "Usage (g)", "Material"};
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}
	
	public boolean isCsllEditable(int row, int col) {
		return false;
	}
	
	public void setValueAt(Object value, int row, int col) {
		
//		Create new row
		if (getRowCount() <= row) {
//			Create new vector and fill with zeros
			Vector<Object> v = new Vector<Object>();
			v.setSize(getColumnCount());
				
//			Pad out data to needed length
			for (int i = getRowCount(); i < row; i ++) {
				data.add(new Vector<Object>(v));
			}
//			Add data to row
			v.set(col, value);
			data.add(v);
		}
//		Update existing row
		else {
			Vector<Object> v = new Vector<Object>(data.get(row));
			v.set(col, value);
			data.set(row,  v);
		}
//		Update table
		fireTableCellUpdated(row, col);
	}
}