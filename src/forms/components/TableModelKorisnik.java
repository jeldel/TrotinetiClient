package forms.components;

import domain.GradEnum;
import domain.Korisnik;
import domain.TipKorisnika;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelKorisnik extends AbstractTableModel {
    private List<Korisnik> korisnici;
    private String[] columnNames = new String[]{"korisnikID", "brojLicneKarte", "ime", "prezime", "email", "grad", "telefon", "username","sifra" , "tipKorisnika"};
    private Class[] columnClass = new Class[]{Long.class, Long.class, String.class,String.class, String.class, GradEnum.class, String.class, String.class, String.class, TipKorisnika.class};

    public TableModelKorisnik(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    @Override
    public int getRowCount() {
        if(korisnici == null){
            return 0;
        }else{
            return korisnici.size();
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
        Korisnik korisnik = korisnici.get(rowIndex);

        switch (columnIndex){
            case 0:
                return korisnik.getkorisnikID();
            case 1:
                return korisnik.getBrojLicneKarte();
            case 2:
                return korisnik.getIme();
            case 3:
                return korisnik.getPrezime();
            case 4:
                return korisnik.getEmail();
            case 5:
                return korisnik.getGrad();
            case 6:
                return korisnik.getTelefon();
            case 7:
                return korisnik.getUsername();
            case 8:
                return korisnik.getSifra();
            case 9:
                return korisnik.getTipKorisnika();
            default:
                return "Nije dostupno";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Korisnik korisnik = korisnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                korisnik.setkorisnikID((Long) aValue);
                break;
            case 1:
                korisnik.setBrojLicneKarte((Long)aValue);
                break;
            case 2:
                korisnik.setIme((String) aValue);
                break;
            case 3:
                korisnik.setPrezime((String) aValue);
                break;
            case 4:
                korisnik.setEmail((String) aValue);
                break;
            case 5:
                korisnik.setGrad((GradEnum) aValue);
                break;
            case 6:
                korisnik.setTelefon((String)aValue);
                break;
            case 7:
                korisnik.setUsername((String)aValue);
                break;
            case 8:
                korisnik.setSifra((String) aValue);
                break;
            case 9:
                korisnik.setTipKorisnika((TipKorisnika) aValue);
                break;
        }
    }
}
