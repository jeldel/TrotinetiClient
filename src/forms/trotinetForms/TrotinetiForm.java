package forms.trotinetForms;

import forms.mainFormAdmin;

import javax.swing.*;
import java.awt.event.*;

public class TrotinetiForm extends JDialog {
    private JPanel contentPane;

    private JButton btnReturn;
    private JButton btnCreate;
    private JButton btnAllScooters;

    public TrotinetiForm() {
        setContentPane(contentPane);
        setBounds(500,200,300,200);
        setModal(true);
        setTitle("Rad sa trotinetima");



        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onReturn();}
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
                new CreateTrotinetForm().setVisible(true);
            }
        });
        btnAllScooters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TrotinetGeneralFormTM().setVisible(true);
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
        TrotinetiForm dialog = new TrotinetiForm();
        dialog.setVisible(true);

    }
}
