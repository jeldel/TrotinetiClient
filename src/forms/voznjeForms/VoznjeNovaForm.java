package forms.voznjeForms;

import com.toedter.calendar.JDateChooser;
import controller.Controller;
import domain.IznajmljivanjeTrotineta;
import domain.Korisnik;
import domain.Osoba;
import domain.Trotinet;
import forms.components.TableModelVoznja;
import forms.osobaForms.OsobeMainForm;
import forms.trotinetForms.TrotinetVoznjaForm;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VoznjeNovaForm extends JDialog {
    private JPanel contentPane;
    private JButton btnSave;
    private JLabel lblUser;
    private JTextField txtUser;
    private JLabel lblOsoba;
    private JLabel lblTrotinet;
    private JTextField txtOsoba;
    private JTextField txtTrotinet;
    private JLabel lblHours;
    private JTextField txtHours;
    private JButton btnSelectOsoba;
    private JTextField txtDate;
    private JLabel lblDate;
    private JButton btnSelectTrotinet;
    private JPanel datePickerPanel;
    private JButton btnCancel;
    private JButton btnGetAll;
    private JButton btnAdd;
    private JTable tblVoznje;
    JDateChooser dateChooser;

    public VoznjeNovaForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(400, 200, 700, 300);
        setTitle("Kreiraj voznju");
        getRootPane().setDefaultButton(btnSave);
        tblVoznje.setEnabled(false);

        try {
            prepareView();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                TableModelVoznja tableModelVoznja = (TableModelVoznja) tblVoznje.getModel();
                List<IznajmljivanjeTrotineta> voznje = tableModelVoznja.getVoznje();
                try {
                    if (voznje.isEmpty()) {
                        IznajmljivanjeTrotineta voznja = new IznajmljivanjeTrotineta();
                        voznja.setKorisnik(Controller.getInstance().getUlogovanKorisnik());
                        voznja.setOsoba(Controller.getInstance().getIzabranaOsoba());
                        voznja.setTrotinet(Controller.getInstance().getIzabraniTrotinet());
                        voznja.setDatumVreme(dateChooser.getDate());
                        voznja.setBrojSati(Double.parseDouble(txtHours.getText()));
                        voznja.setOsoba(Controller.getInstance().getIzabranaOsoba());
                        Controller.getInstance().addVoznja(voznja);
                        JOptionPane.showMessageDialog(btnSave, "Uspesno ste kreirali voznju! ", "Success", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        Controller.getInstance().addAllVoznje(voznje);
                        JOptionPane.showMessageDialog(btnSave, "Uspesno ste sacuvali voznje", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    Controller.getInstance().setIzabraneVoznje(new ArrayList<>());
                    Controller.getInstance().setIzabraniTrotinet(null);
                    Controller.getInstance().setIzabranaOsoba(null);
                    dispose();
                    new VoznjeNovaForm().setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(btnSave, "Sistem ne moze da zapamti voznju", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        btnSelectOsoba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OsobeMainForm().setVisible(true);
            }
        });
        btnSelectTrotinet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TrotinetVoznjaForm().setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        btnGetAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VoznjeGeneralFormTM().setVisible(true);
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    IznajmljivanjeTrotineta iznajmljivanjeTrotineta = new IznajmljivanjeTrotineta();
                    iznajmljivanjeTrotineta.setKorisnik(Controller.getInstance().getUlogovanKorisnik());
                    iznajmljivanjeTrotineta.setTrotinet(Controller.getInstance().getIzabraniTrotinet());
                    iznajmljivanjeTrotineta.setBrojSati(Double.valueOf(txtHours.getText()));
                    iznajmljivanjeTrotineta.setDatumVreme(dateChooser.getDate());
                    iznajmljivanjeTrotineta.setOsoba(Controller.getInstance().getIzabranaOsoba());
                    Controller.getInstance().getIzabraneVoznje().add(iznajmljivanjeTrotineta);
                } catch (Exception exception){
                    exception.printStackTrace();
                }

                dispose();
                new VoznjeNovaForm().setVisible(true);

            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void prepareView() {
        ulogovaniKorisnik();
        izabranaOsoba();
        izabraniTrotinet();
        postaviDatum();
        pripremaTabele();
    }

    private void pripremaTabele() {
        List<IznajmljivanjeTrotineta> voznje = null;
        try {
            voznje = Controller.getInstance().getIzabraneVoznje();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TableModelVoznja tableModelVoznja = new TableModelVoznja(voznje);
        tblVoznje.setModel(tableModelVoznja);
    }

    private void postaviDatum() {
        dateChooser = new JDateChooser();
        txtDate.setEditable(false);
        datePickerPanel.setLayout(new java.awt.BorderLayout());
        datePickerPanel.add(dateChooser, java.awt.BorderLayout.CENTER);

        dateChooser.getDateEditor().addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName())) {
                Object newValue = e.getNewValue();
                if (newValue instanceof Date) {
                    Date selectedDate = (Date) newValue;
                    Date sqlDate = new Date(selectedDate.getTime());
                    txtDate.setText(sqlDate.toString());

                }
            }
        });

    }

    private void izabraniTrotinet() {
        Trotinet trotinet = Controller.getInstance().getIzabraniTrotinet();
        if (trotinet != null) {
            txtTrotinet.setText(trotinet.getVrstaTrotineta() + ", " + trotinet.getModel());
        } else {
            txtTrotinet.setText("");
        }
    }

    private void izabranaOsoba() {
        Osoba osoba = Controller.getInstance().getIzabranaOsoba();
        if (osoba != null) {
            txtOsoba.setText(osoba.getIme() + " " + osoba.getPrezime() + " - brojLK :" + osoba.getBrojLicneKarte());
        } else {
            txtOsoba.setText("");
        }
    }

    private void ulogovaniKorisnik() {
        Korisnik loggedInKorisnik = Controller.getInstance().getUlogovanKorisnik();
        if (loggedInKorisnik != null) {
            txtUser.setText(loggedInKorisnik.getUsername());
        } else {
            txtUser.setText("");
        }
    }


    public static void main(String[] args) {
        VoznjeNovaForm dialog = new VoznjeNovaForm();
        dialog.setVisible(true);
    }
}
