package forms.voznjeForms;

import forms.mainFormAdmin;

import javax.swing.*;
import java.awt.event.*;

public class VoznjeForm extends JDialog {
    private JPanel contentPane;
    private JButton btnReturn;
    private JButton btnCreate;
    private JButton btnSearch;

    public VoznjeForm() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(500,200, 300,200);

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
                new VoznjeNovaForm().setVisible(true);
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VoznjeGeneralFormTM().setVisible(true);
            }
        });
    }

    private void onReturn() {
        dispose();
        new mainFormAdmin().setVisible(true);
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        VoznjeForm dialog = new VoznjeForm();
        dialog.setVisible(true);
    }
}
