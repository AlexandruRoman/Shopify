import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Vector;

public class Wish extends JPanel implements ActionListener {

    Vector<Vector<String>> WishTableData = new Vector<>();
    JTable WishTable = new JTable();
    DefaultTableModel wishTableModel;
    Vector<String> headerNames;
    JScrollPane scrollWish;

    JComboBox customersList;

    JLabel lCustomer = new JLabel("Alegeti clientul:");
    JLabel lName = new JLabel("Nume");
    JLabel lID = new JLabel("ID");
    JLabel lIDDep = new JLabel("ID Dep.");
    JLabel lPrice = new JLabel("Pret");

    JTextField fName = new JTextField(15);
    JTextField fID = new JTextField(5);
    JTextField fIDDep = new JTextField(5);
    JTextField fPrice = new JTextField(15);

    JButton bRemove = new JButton("Elimina");
    JButton bAdauga = new JButton("Adauga");

    String customerName;


    public Wish()
    {
        super();
        setLayout(new MigLayout());
        headerNames = new Vector<>();
        headerNames.add("Nume");
        headerNames.add("ID");
        headerNames.add("ID departament");
        headerNames.add("Pret");
        wishTableModel = new DefaultTableModel(WishTableData, headerNames);
        WishTable.setModel(wishTableModel);
    }

    public void createCustomersList()
    {
        Vector<String> items = new Vector<>();
        for(Customer c : Store.getInstance().getCustomers().values())
        {
            items.add(c.name);
        }
        customersList = new JComboBox(items);
    }

    public void customersListListener()
    {
        customersList.addItemListener (new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    customerName = e.getItem().toString();
                    createWishTable(Store.getInstance().getCustomers().get(e.getItem().toString()));
                }
            }
        });
    }

    public void createWishTable(Customer customer)
    {
        WishTableData.clear();
        Iterator<Item> it = customer.wishList.listIterator();
        while(it.hasNext())
        {
            Item x = it.next();
            Vector<String> aux = new Vector<>();
            aux.add(x.getName());
            aux.add("" + x.getId());
            aux.add("" + x.getDepId());
            aux.add("" + x.getPrice());
            WishTableData.add(aux);
        }

        wishTableModel.setDataVector(WishTableData, headerNames);
        WishTableListener();
    }

    public void WishTableListener()
    {
        WishTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if(event.getFirstIndex()>=0 && event.getLastIndex()<=WishTableData.size() && event.getFirstIndex()<event.getLastIndex()) {
                            int viewRow = WishTable.getSelectedRow();
                            fID.setText(wishTableModel.getValueAt(viewRow, 1).toString());
                            fName.setText(wishTableModel.getValueAt(viewRow, 0).toString());
                            fIDDep.setText(wishTableModel.getValueAt(viewRow, 2).toString());
                            fPrice.setText(wishTableModel.getValueAt(viewRow, 3).toString());
                        }
                        else
                        if(event.getFirstIndex()>=0 && event.getLastIndex()<=WishTableData.size() && event.getFirstIndex()==event.getLastIndex())
                        {
                            int viewRow = event.getFirstIndex();
                            fID.setText(wishTableModel.getValueAt(viewRow, 1).toString());
                            fName.setText(wishTableModel.getValueAt(viewRow, 0).toString());
                            fIDDep.setText(wishTableModel.getValueAt(viewRow, 2).toString());
                            fPrice.setText(wishTableModel.getValueAt(viewRow, 3).toString());
                        }
                    }
                }
        );
    }
    public void updateScroll()
    {
        if (scrollWish == null)
        {
            scrollWish = new JScrollPane(WishTable);
            scrollWish.setPreferredSize(new Dimension(1300, 800));
        }
        else scrollWish.setViewportView(WishTable);
    }

    public void addElements()
    {
        scrollWish = new JScrollPane(WishTable);
        scrollWish.setPreferredSize(new Dimension(1300, 800));
        add(scrollWish, "wrap, span");

        Font font = new Font("Verdana", Font.PLAIN, 30);

        lID.setFont(font);
        fID.setFont(font);
        add(lID);
        add(fID);

        bRemove.setFont(font);
        bAdauga.setFont(font);
        add(bRemove);
        add(bAdauga);

        bRemove.addActionListener(this);
        bAdauga.addActionListener(this);
    }
    public void action()
    {
        createCustomersList();
        customersListListener();
        Font font = new Font("Verdana", Font.PLAIN, 30);
        customersList.setFont(font);
        lCustomer.setFont(font);

        add(lCustomer, "split 4, span");
        add(customersList, "wrap");

        WishTableListener();
        addElements();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().contains("Elimina"))
        {
            PreluareDate.implementEvents(("delItem;" + fID.getText() + ";WishList;" + customerName).split(";"));
            int i = 0;
            for(Vector<String> x : WishTableData)
            {
                if(x.get(1).equals(fID.getText()))
                    i = WishTableData.indexOf(x);
            }
            WishTableData.remove(i);
        }
        if(((JButton)e.getSource()).getText().contains("Adauga"))
        {
            Item aux = Store.getInstance().findItem(Integer.parseInt(fID.getText()));
            if(aux == null)
            {
                JOptionPane.showMessageDialog(Game.getInstance(), "Obiectul nu exista in magazin.");
            }
            else {
                Vector<String> x = new Vector<>();
                x.add(aux.getName());
                x.add("" + aux.getId());
                x.add("" + aux.getDepId());
                x.add("" + aux.getPrice());

                int i = -1;
                for (Vector<String> d : WishTableData) {
                    if (d.get(1).equals(fID.getText()))
                        i = WishTableData.indexOf(d);
                }
                if (i != -1) {
                    JOptionPane.showMessageDialog(Game.getInstance(), "Obiectul exista deja in lista de dorinte.");
                } else {
                    WishTableData.add(x);
                    wishTableModel.fireTableDataChanged();
                    PreluareDate.implementEvents(("addItem;" + aux.getId() + ";WishList;" + customerName).split(";"));
                }
            }
        }
        updateScroll();
    }
}
