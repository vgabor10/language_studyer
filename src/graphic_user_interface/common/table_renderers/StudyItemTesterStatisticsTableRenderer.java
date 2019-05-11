package graphic_user_interface.common.table_renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import language_studyer.common.GeneralFunctions;

public class StudyItemTesterStatisticsTableRenderer implements TableCellRenderer {
    
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
            
	    Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
	    value, isSelected, hasFocus, row, column);
            
            String rarAfter 
                    = (String)table.getModel().getValueAt(row, table.getColumnCount()-3);
            String rarBefore 
                    = (String)table.getModel().getValueAt(row, table.getColumnCount()-2);

            if (!isDouble(rarAfter)) {
                c.setBackground(new Color(255,255,255));  //white
            }
            else
            if (!isDouble(rarBefore)) {
                c.setBackground(new Color(51,194,242)); //light blue
            }
            else {
                double rarChange = Double.parseDouble((String)rarAfter) 
                    - Double.parseDouble((String)rarBefore);
                                    
                if (rarChange > 0) {
                    c.setBackground(new Color(0,255,89));   //light green
                }


                if (rarChange == 0) {
                    c.setBackground(Color.lightGray);
                }

                if (rarChange < 0) {
                    c.setBackground(new Color(255,71,71));  //light red
                }
            }
            
            return c;
	}
}
