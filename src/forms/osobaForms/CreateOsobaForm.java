package forms.osobaForms;

import controller.Controller;
import domain.Osoba;

import javax.swing.*;
import java.awt.event.*;

public class CreateOsobaForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel lblBrojLK;
    private JTextField txtBrojLK;
    private JLabel lblIme;
    private JTextField txtIme;
    private JTextField txtPrezime;
    private JLabel lblPrezime;

    public CreateOsobaForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(400,200, 450,400);
        setTitle("Kreiraj osobu");
        setAlwaysOnTop(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
    }

    private void onOK() {
        try {
            validation();

            Osoba osoba = new Osoba();
            osoba.setBrojLicneKarte(Long.valueOf(txtBrojLK.getText().trim()));
            osoba.setIme(txtIme.getText().trim());
            osoba.setPrezime(txtPrezime.getText().trim());

            Controller.getInstance().addOsoba(osoba);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio osobu!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sistem ne moze da kreira osobu! " + e.getMessage(), "Greska!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validation() throws Exception {
        if (txtBrojLK.getText().length() != 9) {
            throw new RuntimeException("Broj licne karte mora da ima tacno devet brojeva");
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CreateOsobaForm dialog = new CreateOsobaForm();
        dialog.setVisible(true);
    }
}
