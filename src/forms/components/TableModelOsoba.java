package forms.components;

import domain.Osoba;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelOsoba extends AbstractTableModel {
    private List<Osoba> osobe;
    private String[] columnNames = new String[]{ "brojLicneKarte", "ime", "prezime"};
    private Class[] columnClass = new Class[]{Long.class, String.class, String.class,};

    public TableModelOsoba(List<Osoba> osobe) {
        this.osobe = osobe;
    }

    @Override
    public int getRowCount() {
        if(osobe == null){
            return 0;
        }else{
            return osobe.size();
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
            return true;
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Osoba osoba = osobe.get(rowIndex);

        switch (columnIndex){
            case 0:
                return osoba.getBrojLicneKarte();
            case 1:
                return osoba.getIme();
            case 2:
                return osoba.getPrezime();
            default:
                return "Nije dostupno";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Osoba osoba = osobe.get(rowIndex);
        switch (columnIndex) {
            case 0:
                osoba.setBrojLicneKarte((Long)aValue);
                break;
            case 1:
                osoba.setIme((String) aValue);
                break;
            case 2:
                osoba.setPrezime((String) aValue);
                break;
        }
    }
}
