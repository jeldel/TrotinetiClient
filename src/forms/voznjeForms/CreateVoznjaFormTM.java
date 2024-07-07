package forms.voznjeForms;

import controller.Controller;
import domain.IznajmljivanjeTrotineta;
import forms.components.DatePicker;
import forms.components.TableModelVoznja;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateVoznjaFormTM extends JDialog {
    private JPanel contentPane;
    private JTable tblVoznje;
    private JButton btnAdd;
    private JButton btnSave;
    private JButton btnRemove;


    public CreateVoznjaFormTM() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(500, 200, 700, 400);
        setTitle("Rad sa voznjama");

        try {
            tblVoznje.setVisible(true);
            prepareView();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


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


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IznajmljivanjeTrotineta iznajmljivanjeTrotineta = new IznajmljivanjeTrotineta();
                TableModelVoznja tableModelVoznja = (TableModelVoznja) tblVoznje.getModel();
                tableModelVoznja.addVoznja(iznajmljivanjeTrotineta);

            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tblVoznje.getSelectedRow();
                if (selected == -1) {
                    JOptionPane.showMessageDialog(btnRemove, "Niste izabrali red!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    TableModelVoznja tableModelVoznja = (TableModelVoznja) tblVoznje.getModel();
                    tableModelVoznja.removeVoznja(selected);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelVoznja tableModelVoznja = (TableModelVoznja) tblVoznje.getModel();
                List<IznajmljivanjeTrotineta> voznje = tableModelVoznja.getVoznje();
                try {
                    if (voznje.isEmpty()) {
                        JOptionPane.showMessageDialog(btnSave, "Niste uneli nikakvu voznju", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        Controller.getInstance().addAllVoznje(voznje);
                        JOptionPane.showMessageDialog(btnSave, "Uspesno ste sacuvali voznje", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new VoznjeForm().setVisible(true);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(btnSave, "Greska pri cuvanju liste voznji", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void prepareView() {
        prepareTableVoznja();
    }

    private void prepareTableVoznja() {
        List<IznajmljivanjeTrotineta> voznje = new ArrayList<>();
        TableModelVoznja tableModelVoznja = new TableModelVoznja(voznje);
        tblVoznje.setModel(tableModelVoznja);

        TableColumn tableColumnDate = tblVoznje.getColumnModel().getColumn(1);
        tableColumnDate.setCellEditor(new DatePicker());

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
        CreateVoznjaFormTM dialog = new CreateVoznjaFormTM();
        dialog.setVisible(true);
    }
}
