package forms.trotinetForms;

import controller.Controller;
import domain.Trotinet;
import domain.VrstaTrotinetaEnum;
import forms.components.TableModelTrotinet;
import forms.voznjeForms.VoznjeNovaForm;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.List;

public class TrotinetVoznjaForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable tblTrotineti;
    private JComboBox cmbSearch;
    private JButton btnSearch;

    public TrotinetVoznjaForm() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Dodaj trotinet");
        setBounds(400, 200, 500, 300);

        try {
            tblTrotineti.setVisible(true);
            prepareView();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    try {
                        int selectedRow = tblTrotineti.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(buttonOK, "Niste izabrali trotinet", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        Long trotinetID = (Long) tblTrotineti.getValueAt(selectedRow, 0);
                        Trotinet trotinet = Controller.getInstance().getTrotinetById(trotinetID);
                        Controller.getInstance().setIzabraniTrotinet(trotinet);
                        JOptionPane.showMessageDialog(buttonOK, "Uspesno dodavanje trotineta " + trotinet.getVrstaTrotineta() + " , " + trotinet.getModel(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new VoznjeNovaForm().setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(buttonOK, "Neuspesno dodavanje trotineta!", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
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
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbSearch.getSelectedItem() == "Svi trotineti"){
                    List<Trotinet> trotineti = null;
                    try {
                        trotineti = Controller.getInstance().getAllTrotinet();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    TableModelTrotinet tableModelTrotinet = new TableModelTrotinet(trotineti);
                    tblTrotineti.setModel(tableModelTrotinet);
                }
                else{
                    List<Trotinet> trotineti = null;
                    try {
                        trotineti = Controller.getInstance().getAllByVrsta((VrstaTrotinetaEnum) cmbSearch.getSelectedItem());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    TableModelTrotinet tableModelTrotinet = new TableModelTrotinet(trotineti);
                    tblTrotineti.setModel(tableModelTrotinet);
                }

            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
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
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TrotinetVoznjaForm dialog = new TrotinetVoznjaForm();
        dialog.setVisible(true);
    }
}
