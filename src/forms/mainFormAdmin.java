package forms;

import forms.korisnikForms.KorisniciForm;
import forms.trotinetForms.TrotinetiForm;
import forms.voznjeForms.VoznjeForm;

import javax.swing.*;
import java.awt.event.*;

public class mainFormAdmin extends JDialog {
    private JPanel contentPane;

    private JButton btnClose;
    private JButton btnUsers;
    private JButton btnTrotineti;
    private JButton btnVoznja;
    private JLabel lblWelcome;

    public mainFormAdmin() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Admin forma");
        setBounds(500,200,300,300);

        btnClose.addActionListener(new ActionListener() {
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


        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KorisniciForm().setVisible(true);
            }
        });
        btnTrotineti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TrotinetiForm().setVisible(true);
            }
        });
        btnVoznja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VoznjeForm().setVisible(true);
            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        mainFormAdmin dialog = new mainFormAdmin();
        dialog.setVisible(true);
    }
}
