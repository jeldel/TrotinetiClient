package forms.voznjeForms;

import com.toedter.calendar.JDateChooser;
import controller.Controller;
import domain.IznajmljivanjeTrotineta;
import domain.Korisnik;
import domain.Osoba;
import domain.Trotinet;
import forms.osobaForms.OsobeMainForm;
import forms.trotinetForms.TrotinetVoznjaForm;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;

public class VoznjeNovaForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
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
    private JTable tblDate;
    private JLabel lblDate;
    private JButton btnSelectTrotinet;
    private JPanel datePickerPanel;
    private JButton btnCancel;
    private JButton btnGetAll;
    JDateChooser dateChooser;

    public VoznjeNovaForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(400, 200, 500, 300);
        setTitle("Kreiraj voznju");
        getRootPane().setDefaultButton(buttonOK);


        try {
            prepareView();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    IznajmljivanjeTrotineta voznja = new IznajmljivanjeTrotineta();
                    voznja.setKorisnik(Controller.getInstance().getUlogovanKorisnik());
                    voznja.setOsoba(Controller.getInstance().getIzabranaOsoba());
                    voznja.setTrotinet(Controller.getInstance().getIzabraniTrotinet());
                    voznja.setDatumVreme(dateChooser.getDate());
                    voznja.setBrojSati(Double.parseDouble(txtHours.getText()));
                    Controller.getInstance().addVoznja(voznja);
                    JOptionPane.showMessageDialog(buttonOK, "Uspesno ste kreirali voznju! ", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(buttonOK, "Greska pri kreiranju voznje! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
