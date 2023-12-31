package Main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class ColorRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	private static Border selectedBorder;
	private static Border noBorder;
	
	public ColorRenderer() {
		setOpaque(true);
		selectedBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(99, 130, 191));
		noBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, getBackground());
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color selected = new Color(184, 207, 229);
		int warningValue = Settings.getWarningValue();
		int currentValue = Integer.parseInt(table.getValueAt(row, table.getColumnCount() - 1).toString());
		
//		Format cells based on usage
		if (currentValue <= warningValue)
			fg = Color.YELLOW;
		if (currentValue <= warningValue / 2)
			fg = Color.ORANGE;
		if (currentValue <= 0)
			fg = new Color(255, 50, 50);
		
//		Increase contrast if too low
		int contrast = (contrast(bg, fg));
		if (Math.abs(contrast) < 150) {
			if (contrast > 0)
				fg = fg.darker();
			else
				fg = fg.brighter();
		}
		
//		Highlight selected cells
		if(isSelected) {
			bg = selected;
		}
		
		if(hasFocus) {
			setBorder(selectedBorder);
		}
		else {
			setBorder(noBorder);
		}
		
//		Apply conditional formatting
		setForeground(fg);
		setBackground(bg);
		setText(value.toString());
		return this;
	}
	
	private int contrast(Color bg, Color fg) {
		int bgBrightness = bg.getRed() + bg.getGreen() + bg.getBlue() / 3;
		int fgBrightness = fg.getRed() + fg.getGreen() + fg.getBlue() / 3;
		
		int contrast = bgBrightness - fgBrightness;
 		return contrast;
	}
}
