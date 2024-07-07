package forms.korisnikForms;

import forms.mainFormAdmin;

import javax.swing.*;
import java.awt.event.*;

public class KorisniciForm extends JDialog {
    private JPanel contentPane;

    private JButton btnReturn;
    private JButton btnCreate;
    private JButton btnAllUsers;

    public KorisniciForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(500,200, 500,200);
        setTitle("Rad sa korisnicima");

        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onReturn();
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

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateKorisnikForm().setVisible(true);
            }
        });
        btnAllUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KorisnikGeneralForm().setVisible(true);
            }
        });
    }

    private void onReturn() {
        dispose();
        new mainFormAdmin().setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        KorisniciForm dialog = new KorisniciForm();
        dialog.setVisible(true);
    }
}
