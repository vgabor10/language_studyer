package graphic_user_interface;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CardTestesStatisticsTableRenderer implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
            
	    Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
	    value, isSelected, hasFocus, row, column);
            
            String rarAfter = (String)table.getModel().getValueAt(row,2);
            String rarBefore = (String)table.getModel().getValueAt(row,3);
            
            double rarChange;
            if (rarBefore.equals("-")) {
                rarChange = 0;
            }
            else {
                rarChange = Double.parseDouble((String)rarAfter) 
                    - Double.parseDouble((String)rarBefore);
            }
            
            if (rarChange > 0) {
                c.setBackground(Color.green);
            }
            

            if (rarChange == 0) {
                c.setBackground(Color.lightGray);
            }
            
            if (rarChange < 0) {
                c.setBackground(Color.red);
            }
            
            return c;
	}
}
