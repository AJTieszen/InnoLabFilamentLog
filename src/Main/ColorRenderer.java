package Main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	public ColorRenderer() {
		setOpaque(true);
		
		System.out.println("ColorRenderer Created");
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setBackground(Color.CYAN);
		setForeground(Color.ORANGE);
		
		System.out.println("ColorRender Applied");
		return this;
	}

}
