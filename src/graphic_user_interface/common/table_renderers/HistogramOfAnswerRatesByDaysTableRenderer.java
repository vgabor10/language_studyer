package graphic_user_interface.common.table_renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class HistogramOfAnswerRatesByDaysTableRenderer implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
            
	    Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
	    value, isSelected, hasFocus, row, column);
            
            if (column == 0) {
                c.setBackground(new Color(255,255,255));    //white
                return c;
            } 
            else if (row == table.getModel().getRowCount()-1) {
                c.setBackground(new Color(51,194,242));  //light blue
                return c;
            }
            else {
                Integer n = (Integer)table.getModel().getValueAt(row,column);
                Integer nBefore = (Integer)table.getModel().getValueAt(row+1,column);
                
                if (Objects.equals(n, nBefore)) {
                    c.setBackground(new Color(51,194,242));  //light blue
                } 
                else if (n > nBefore) {
                    c.setBackground(new Color(0,255,89));   //light green
                }
                else if (n < nBefore) {
                    c.setBackground(new Color(255,71,71));  //light red
                }
                return c;
            }
            
	}
}
