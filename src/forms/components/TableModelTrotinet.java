package forms.components;

import domain.Trotinet;
import domain.VrstaTrotinetaEnum;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelTrotinet extends AbstractTableModel {
    private List<Trotinet> trotineti;
    private String[] columnNames = new String[]{"TrotinetID", "Vrsta trotineta", "Model"};
    private Class[] columnClass = new Class[]{Long.class, VrstaTrotinetaEnum.class, String.class};

    public TableModelTrotinet(List<Trotinet> trotineti) {
        this.trotineti = trotineti;
    }

    @Override
    public int getRowCount() {
        if (trotineti == null) {
            return 0;
        } else {
            return trotineti.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "Nije dostupno";
        }
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columnClass.length) {
            return Object.class;
        }
        return columnClass[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return false;
        else
            return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Trotinet trotinet = trotineti.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return trotinet.getTrotinetID();
            case 1:
                return trotinet.getVrstaTrotineta();
            case 2:
                return trotinet.getModel();
            default:
                return "Nije dostupno";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Trotinet trotinet = trotineti.get(rowIndex);
        switch (columnIndex) {
            case 0:
                trotinet.setTrotinetID((Long) aValue);
                break;
            case 1:
                trotinet.setVrstaTrotineta((VrstaTrotinetaEnum) aValue);
                break;
            case 2:
                trotinet.setModel((String) aValue);
                break;
        }
    }
}