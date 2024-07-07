package forms.voznjeForms;

import controller.Controller;
import domain.IznajmljivanjeTrotineta;
import domain.TipKorisnika;
import domain.Trotinet;
import domain.VrstaTrotinetaEnum;
import forms.components.DatePicker;
import forms.components.TableModelVoznja;
import forms.mainFormUser;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class VoznjeGeneralFormTM extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton btnEdit;
    private JTable tblVoznje;
    private JButton btnSearch;
    private JTextField txtSearch;
    private JLabel lblSearch;

    public VoznjeGeneralFormTM() {
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

        buttonCancel.addActionListener(new ActionListener() {
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


        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtSearch.getText().trim();
                if (!username.isEmpty()) {
                    List<IznajmljivanjeTrotineta> voznje = null;
                    try {
                        voznje = Controller.getInstance().getAllByCriteria(username);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    TableModelVoznja tableModelVoznja = new TableModelVoznja(voznje);
                    tblVoznje.setModel(tableModelVoznja);
                } else {
                    List<IznajmljivanjeTrotineta> voznje = null;
                    try {
                        voznje = Controller.getInstance().getAllVoznje();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    TableModelVoznja tableModelVoznja = new TableModelVoznja(voznje);
                    tblVoznje.setModel(tableModelVoznja);
                }

            }
        });
    }

    private void prepareView() {
        prepareTableVoznja();
    }

    private void prepareTableVoznja() {
        List<IznajmljivanjeTrotineta> voznje = new ArrayList<>();
        if (Controller.getInstance().getUlogovanKorisnik().getTipKorisnika() == TipKorisnika.Korisnik) {
            try {
                voznje = Controller.getInstance().getAllByCriteria(Controller.getInstance().getUlogovanKorisnik().getUsername());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            txtSearch.setEditable(false);
            btnSearch.setEnabled(false);
            btnEdit.setEnabled(false);
        }
        if (Controller.getInstance().getUlogovanKorisnik().getTipKorisnika() == TipKorisnika.Administrator) {
            try {
                voznje = Controller.getInstance().getAllVoznje();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        TableModelVoznja tableModelVoznja = new TableModelVoznja(voznje);
        tblVoznje.setModel(tableModelVoznja);
        TableColumn tableColumnDate = tblVoznje.getColumnModel().getColumn(1);
        tableColumnDate.setCellEditor(new DatePicker());
    }


    private void onCancel() {
        // add your code here if necessary

        if (Controller.getInstance().getUlogovanKorisnik().getTipKorisnika() == TipKorisnika.Korisnik) {
            dispose();
            new mainFormUser().setVisible(true);
        } else {
            dispose();
            new VoznjeForm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        VoznjeGeneralFormTM dialog = new VoznjeGeneralFormTM();
        dialog.setVisible(true);
    }
}
