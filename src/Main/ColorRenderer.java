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
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Color bg = Main.bg;
		Color fg = Main.fg;
		int warningValue = Settings.getWarningValue();
		int currentValue = Integer.parseInt(table.getValueAt(row, table.getColumnCount() - 1).toString());
		
		if (currentValue <= warningValue)
			fg = Color.YELLOW;
		if (currentValue <= warningValue / 2)
			fg = Color.ORANGE;
		if (currentValue <= 0)
			fg = Color.RED;
		
		setBackground(bg);
		setForeground(fg);
		setText(value.toString());
		return this;
	}
}
