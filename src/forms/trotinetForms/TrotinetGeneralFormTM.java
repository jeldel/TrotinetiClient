package forms.trotinetForms;


import controller.Controller;
import domain.Trotinet;
import domain.VrstaTrotinetaEnum;
import forms.components.TableModelTrotinet;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.List;

public class TrotinetGeneralFormTM extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton btnSearch;
    private JButton btnDelete;
    private JButton btnEdit;
    private JTable tblTrotineti;
    private JComboBox cmbSearch;

    public TrotinetGeneralFormTM() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(400, 200, 700, 400);
        setTitle("Rad sa trotinetima");

        try {
            tblTrotineti.setVisible(true);
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
                dispose();
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
                if (cmbSearch.getSelectedItem() == "Svi trotineti") {
                    List<Trotinet> trotineti = null;
                    try {
                        trotineti = Controller.getInstance().getAllTrotinet();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    TableModelTrotinet tableModelTrotinet = new TableModelTrotinet(trotineti);
                    tblTrotineti.setModel(tableModelTrotinet);
                } else {
                    List<Trotinet> trotineti = null;
                    try {
                        trotineti = Controller.getInstance().getAllByVrsta((VrstaTrotinetaEnum) cmbSearch.getSelectedItem());
                        if (!trotineti.isEmpty()) {
                            JOptionPane.showMessageDialog(btnSearch, "Sistem je nasao trotinete po zadatoj vrednosti", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(btnSearch, "Sistem ne moze da nadje trotinete po zadatoj vrednosti", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    TableModelTrotinet tableModelTrotinet = new TableModelTrotinet(trotineti);
                    tblTrotineti.setModel(tableModelTrotinet);
                }

            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = tblTrotineti.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(btnEdit, "Sistem ne moze da zapamti trotinet", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Long trotinetID = (Long) tblTrotineti.getValueAt(selectedRow, 0);
                        Trotinet t = Controller.getInstance().getTrotinetById(trotinetID);
                        t.setVrstaTrotineta((VrstaTrotinetaEnum) tblTrotineti.getValueAt(selectedRow, 1));
                        t.setModel((String) tblTrotineti.getValueAt(selectedRow, 2));

                        Controller.getInstance().updateTrotinet(t);
                        JOptionPane.showMessageDialog(btnEdit, "Sistem je zapamtio trotinet", "Success", JOptionPane.INFORMATION_MESSAGE);
                        prepareView();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(btnEdit, "Sistem ne moze da zapamti trotinet", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = tblTrotineti.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(btnDelete, "Sistem ne moze da obrise trotinet", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Long trotinetID = (Long) tblTrotineti.getValueAt(selectedRow, 0);
                        Controller.getInstance().deleteTrotinet(trotinetID);
                        JOptionPane.showMessageDialog(btnDelete, "Uspesno pozvana operacija za brisanje trotineta! Ukoliko je trotinet jos uvek u tabeli " +
                                "ne mozete ga obrisati jer postoji voznja za njega", "Success", JOptionPane.INFORMATION_MESSAGE);
                        prepareView();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(btnDelete, "Sistem ne moze da obrise trotinet ", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    private void prepareView() {
        prepareTableVoznja();
    }

    private void prepareTableVoznja() {
        List<Trotinet> trotineti = null;
        try {
            trotineti = Controller.getInstance().getAllTrotinet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TableModelTrotinet tableModelTrotinet = new TableModelTrotinet(trotineti);
        tblTrotineti.setModel(tableModelTrotinet);

        JComboBox cmbVrstaTrotineti = new JComboBox(VrstaTrotinetaEnum.values());
        TableColumn tableColumnVrsta = tblTrotineti.getColumnModel().getColumn(1);
        tableColumnVrsta.setCellEditor(new DefaultCellEditor(cmbVrstaTrotineti));

        cmbSearch.removeAllItems();
        for (VrstaTrotinetaEnum vrstaTrotinetaEnum : VrstaTrotinetaEnum.values()) {
            cmbSearch.addItem(vrstaTrotinetaEnum);
        }
        cmbSearch.addItem("Svi trotineti");

    }

    private void onCancel() {
        dispose();
        new TrotinetiForm().setVisible(true);
    }

    public static void main(String[] args) {
        TrotinetGeneralFormTM dialog = new TrotinetGeneralFormTM();
        dialog.setVisible(true);
    }
}
