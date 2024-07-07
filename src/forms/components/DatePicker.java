package forms.components;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Date;

public class DatePicker extends AbstractCellEditor implements TableCellEditor{
    private JDateChooser dateChooser = new JDateChooser();

    @Override
    public Object getCellEditorValue() {
        return dateChooser.getDate();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            dateChooser.setDate((Date)value);
        } else {
            dateChooser.setDate(new Date());
        }
        return dateChooser;
    }

}
