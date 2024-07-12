package forms.components;

import controller.Controller;
import domain.IznajmljivanjeTrotineta;
import domain.Korisnik;
import domain.Osoba;
import domain.Trotinet;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class TableModelVoznja extends AbstractTableModel {
    private List<IznajmljivanjeTrotineta> voznje;
    private String[] columnNames = new String[]{"IznajmljivanjeID", "Datum", "Broj sati", "TrotinetID", "KorisnikID", "OsobaBrojLK"};
    private Class[] columnClass = new Class[]{Long.class, Date.class, Double.class, Long.class, Long.class, Long.class};

    public TableModelVoznja(List<IznajmljivanjeTrotineta> voznje) {
        this.voznje = voznje;
    }

    @Override
    public int getRowCount() {
        if (voznje == null) {
            return 0;
        } else {
            return voznje.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return false;
        else
            return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IznajmljivanjeTrotineta iznajmljivanjeTrotineta = voznje.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return iznajmljivanjeTrotineta.getIznajmljivanjeID();
            case 1:
                return iznajmljivanjeTrotineta.getDatumVreme();
            case 2:
                return iznajmljivanjeTrotineta.getBrojSati();
            case 3:
                return iznajmljivanjeTrotineta.getTrotinet() != null ? iznajmljivanjeTrotineta.getTrotinet().getTrotinetID() : null;
            case 4:
                return iznajmljivanjeTrotineta.getKorisnik() != null ? iznajmljivanjeTrotineta.getKorisnik().getkorisnikID() : null;
            case 5:
                return iznajmljivanjeTrotineta.getOsoba() != null ? iznajmljivanjeTrotineta.getOsoba().getBrojLicneKarte() : null;
            default:
                return "Nije dostupno";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        IznajmljivanjeTrotineta iznajmljivanjeTrotineta = voznje.get(rowIndex);

        switch (columnIndex) {
            case 0:
                iznajmljivanjeTrotineta.setIznajmljivanjeID((Long) aValue);
                break;
            case 1:
                iznajmljivanjeTrotineta.setDatumVreme((Date) aValue);
                break;
            case 2:
                iznajmljivanjeTrotineta.setBrojSati((Double) aValue);
                break;
            case 3:
                Trotinet trotinet = null;
                try {
                    trotinet = Controller.getInstance().getTrotinetById((Long)aValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iznajmljivanjeTrotineta.setTrotinet(trotinet);
                break;
            case 4:
                Korisnik korisnik = new Korisnik();
                korisnik.setkorisnikID((Long)aValue);
                iznajmljivanjeTrotineta.setKorisnik(korisnik);
                break;
            case 5:
                Osoba osoba = new Osoba();
                osoba.setBrojLicneKarte((Long) aValue);
                iznajmljivanjeTrotineta.setOsoba(osoba);
                break;
        }
    }

    public void addVoznja(IznajmljivanjeTrotineta iznajmljivanjeTrotineta) {
        voznje.add(iznajmljivanjeTrotineta);
        fireTableRowsInserted(voznje.size() - 1, voznje.size() - 1);
    }

    public void removeVoznja(int rowIndex) {
        voznje.remove(rowIndex);
        fireTableDataChanged();
    }

    public List<IznajmljivanjeTrotineta> getVoznje() {
        return voznje;
    }

}
