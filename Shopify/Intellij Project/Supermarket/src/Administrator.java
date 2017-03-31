import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Vector;

public class Administrator extends JPanel implements ActionListener{


    Vector<Vector<String>> data = new Vector<>();
    JTable productsTable;
    DefaultTableModel defaultTableModel;
    Vector<String> headerNames;

    JLabel lName = new JLabel("Nume");
    JLabel lID = new JLabel("ID");
    JLabel lIDDep = new JLabel("ID Dep.");
    JLabel lPrice = new JLabel("Pret");

    JTextField fName = new JTextField(15);
    JTextField fID = new JTextField(5);
    JTextField fIDDep = new JTextField(5);
    JTextField fPrice = new JTextField(15);

    JButton bRemove = new JButton("Elimina");
    JButton bModify = new JButton("Modifica");
    JButton bAdauga = new JButton("Adauga");

    public void createProductsTable()
    {
        for(Department d : Store.getInstance().getDepartments().values())
        {
            for(Item x : d.getItems().values())
            {
                Vector<String> aux = new Vector<>();
                aux.add(x.getName());
                aux.add("" + x.getId());
                aux.add("" + x.getDepId());
                aux.add("" + x.getPrice());
                data.add(aux);
            }
        }

        headerNames = new Vector<>();
        headerNames.add("Nume");
        headerNames.add("ID");
        headerNames.add("ID departament");
        headerNames.add("Pret");

        defaultTableModel = new DefaultTableModel(data, headerNames);
        productsTable = new JTable(defaultTableModel);
    }

    public Administrator()
    {
        super();
        setLayout(new MigLayout());
    }
    public void tabel() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(productsTable.getModel());
        productsTable.setRowSorter(sorter);
        sorter.setSortable(1, false);
        sorter.setSortable(2, false);
        sorter.setComparator(3, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                Double aux1 = Double.parseDouble(name1);
                Double aux2 = Double.parseDouble(name2);
                return aux1.compareTo(aux2);
            }
        });

        productsTable.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    System.out.println(event.getFirstIndex() + ":" + event.getLastIndex());
                    if(event.getFirstIndex()>=0 && event.getLastIndex()<data.size() && event.getFirstIndex()<event.getLastIndex()) {
                        int viewRow = productsTable.getSelectedRow();
                        int modelRow = productsTable.convertRowIndexToModel(viewRow);
                        fID.setText(data.get(modelRow).get(1));
                        fName.setText(data.get(modelRow).get(0));
                        fIDDep.setText(data.get(modelRow).get(2));
                        fPrice.setText(data.get(modelRow).get(3));
                    }
                }
            }
        );
    }

    public void addElements()
    {
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(1300, 800));
        add(scrollPane, "growx, span");
        add(lName);
        add(lID);
        add(lIDDep);
        add(lPrice, "wrap");

        add(fName);
        add(fID);
        add(fIDDep);
        add(fPrice);

        add(bRemove);
        add(bModify);
        add(bAdauga);

        bRemove.addActionListener(this);
        bModify.addActionListener(this);
        bAdauga.addActionListener(this);
    }
    public void action()
    {
        createProductsTable();
        tabel();
        addElements();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().contains("Elimina"))
        {
            PreluareDate.implementEvents(("delProduct;" + fID.getText()).split(";"));
            int i = 0;
            for(Vector<String> x : data)
            {
                if(x.get(1).equals(fID.getText()))
                    i = data.indexOf(x);
            }
            data.remove(i);

        }
        if(((JButton)e.getSource()).getText().contains("Modifica"))
        {
            int i = 0;
            for(Vector<String> x : data)
            {
                if(x.get(1).equals(fID.getText()))
                    i = data.indexOf(x);
            }
            data.get(i).set(3, fPrice.getText());
            PreluareDate.implementEvents(("modifyProduct;" + data.get(i).get(2) + ";" + fID.getText() + ";" + data.get(i).get(3)).split(";"));
        }
        if(((JButton)e.getSource()).getText().contains("Adauga"))
        {
            Vector<String> x = new Vector<>();
            x.add(fName.getText());
            x.add(fID.getText());
            x.add(fIDDep.getText());
            x.add(fPrice.getText());

            int i = -1;
            for(Vector<String> d : data)
            {
                if(d.get(1).equals(fID.getText()))
                    i = data.indexOf(d);
            }
            if(i != -1)
            {
                JOptionPane.showMessageDialog(Game.getInstance(), "Obiectul exista deja in magazin.");
            }
            else
            {
                data.add(x);
                defaultTableModel.fireTableDataChanged();
                PreluareDate.implementEvents(("addProduct;" + fIDDep.getText() + ";" + fID.getText() + ";" + fPrice.getText() + ";" + fName.getText()).split(";"));
            }
        }

    }
}
