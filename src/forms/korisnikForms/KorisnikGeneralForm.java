package forms.korisnikForms;

import controller.Controller;
import domain.GradEnum;
import domain.Korisnik;
import domain.TipKorisnika;
import forms.components.TableModelKorisnik;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.List;

public class KorisnikGeneralForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable tblKorisnik;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnDelete;
    private JButton btnEdit;
    private JLabel lblSearch;

    public KorisnikGeneralForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(400, 200, 700, 400);
        setTitle("Rad sa korisnicima");

        try {
            tblKorisnik.setVisible(true);
            prepareView();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    List<Korisnik> korisnici = null;
                    try {
                        korisnici = Controller.getInstance().getAllKorisnik();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    TableModelKorisnik tableModelKorisnik = new TableModelKorisnik(korisnici);
                    tblKorisnik.setModel(tableModelKorisnik);
                } else {
                    List<Korisnik> korisnici = null;
                    try {
                        korisnici = Controller.getInstance().getAllByUsername(txtSearch.getText());
                        if (!korisnici.isEmpty()) {
                            JOptionPane.showMessageDialog(btnSearch, "Sistem je nasao korisnike po zadatoj vrednosti", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(btnSearch, "Sistem ne moze da nadje korisnike po zadatoj vrednosti", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    TableModelKorisnik tableModelKorisnik = new TableModelKorisnik(korisnici);
                    tblKorisnik.setModel(tableModelKorisnik);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = tblKorisnik.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(btnDelete, "Sistem ne moze da obrise korisnika", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String username = (String) tblKorisnik.getValueAt(selectedRow, 7);
                        Controller.getInstance().deleteKorisnik(username);
                        JOptionPane.showMessageDialog(btnDelete, "Uspesno pozvana operacija za brisanje korisnika! Ukoliko je korisnik jos uvek u tabeli " +
                                "ne mozete ga obrisati jer postoji voznja za njega", "Success", JOptionPane.INFORMATION_MESSAGE);
                        prepareView();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(btnDelete, "Sistem ne moze da obrise korisnika ", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = tblKorisnik.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(btnEdit, "Sistem ne moze da zapamti korisnika", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String username = (String) tblKorisnik.getValueAt(selectedRow, 7);
                        List<Korisnik> korisnici = Controller.getInstance().getAllByUsername(username);

                        Korisnik k = korisnici.get(0);
                        k.setBrojLicneKarte((Long) tblKorisnik.getValueAt(selectedRow, 1));
                        k.setIme((String) tblKorisnik.getValueAt(selectedRow, 2));
                        k.setPrezime((String) tblKorisnik.getValueAt(selectedRow, 3));
                        k.setEmail((String) tblKorisnik.getValueAt(selectedRow, 4));
                        k.setGrad((GradEnum) tblKorisnik.getValueAt(selectedRow, 5));
                        k.setTelefon((String) tblKorisnik.getValueAt(selectedRow, 6));
                        k.setSifra((String) tblKorisnik.getValueAt(selectedRow, 8));
                        k.setTipKorisnika((TipKorisnika) tblKorisnik.getValueAt(selectedRow, 9));

                        Controller.getInstance().updateKorisnik(k);
                        JOptionPane.showMessageDialog(btnEdit, "Sistem je zapamtio korisnika", "Success", JOptionPane.INFORMATION_MESSAGE);
                        prepareView();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(btnEdit, "Sistem ne moze da zapamti korisnika", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    private void prepareView() {
        prepareTableVoznja();
    }

    private void prepareTableVoznja() {
        List<Korisnik> korisnici = null;
        try {
            korisnici = Controller.getInstance().getAllKorisnik();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TableModelKorisnik tableModelKorisnik = new TableModelKorisnik(korisnici);
        tblKorisnik.setModel(tableModelKorisnik);

        JComboBox cmbGrad = new JComboBox(GradEnum.values());
        TableColumn tableColumnGrad = tblKorisnik.getColumnModel().getColumn(5);
        tableColumnGrad.setCellEditor(new DefaultCellEditor(cmbGrad));

        JComboBox cmbTipKorisnika = new JComboBox(TipKorisnika.values());
        TableColumn tableColumnTipKorisnika = tblKorisnik.getColumnModel().getColumn(9);
        tableColumnTipKorisnika.setCellEditor(new DefaultCellEditor(cmbTipKorisnika));
    }


    private void onCancel() {
        dispose();
        new KorisniciForm().setVisible(true);
    }

    public static void main(String[] args) {
        KorisnikGeneralForm dialog = new KorisnikGeneralForm();
        dialog.setVisible(true);
    }
}
