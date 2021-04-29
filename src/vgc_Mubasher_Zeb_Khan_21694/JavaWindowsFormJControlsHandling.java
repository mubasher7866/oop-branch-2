package vgc_Mubasher_Zeb_Khan_21694;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class JavaWindowsFormJControlsHandling {

	// This function will be used for hiding and showing any j-table column
	// visibility
	public static void show_hide_column(int index, String operation,JTable inJTable) {

		if (operation.toLowerCase().equals("hide") == true) {
			TableColumn column = inJTable.getColumnModel().getColumn(index);
			column.setMinWidth(0);
			column.setMaxWidth(0);
			column.setWidth(0);
			column.setPreferredWidth(0);
		} else if (operation.toLowerCase().equals("show") == true) {
			final int width = 250;
			TableColumn column = inJTable.getColumnModel().getColumn(index);
			column.setMinWidth(width);
			column.setMaxWidth(width);
			column.setWidth(width);
			column.setPreferredWidth(width);
		}
	}
	
}
