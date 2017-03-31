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
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;

public class Shop extends JPanel implements ActionListener {

    Vector<Vector<String>> shopTableData = new Vector<>();
    JTable shopTable = new JTable();
    DefaultTableModel shopTableModel;
    Vector<String> headerNames;
    JScrollPane scrollShop;

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
    JButton bComanda = new JButton("Comanda");
    JButton bSelect = new JButton("Strategy");

    JLabel lTotal = new JLabel("Total:");
    JLabel lBuget = new JLabel("Buget:");
    JLabel fTotal = new JLabel("            ");
    JLabel fBuget = new JLabel("            ");

    String customerName;


    public Shop()
    {
        super();
        setLayout(new MigLayout());
        headerNames = new Vector<>();
        headerNames.add("Nume");
        headerNames.add("ID");
        headerNames.add("ID departament");
        headerNames.add("Pret");
        shopTableModel = new DefaultTableModel(shopTableData, headerNames);
        shopTable.setModel(shopTableModel);
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
                    createShopTable(Store.getInstance().getCustomers().get(e.getItem().toString()));
                    updatePrice();
                }
            }
        });
    }

    public void createShopTable(Customer customer)
    {
        shopTableData.clear();
        Iterator<Item> it = customer.shoppingCart.listIterator();
        while(it.hasNext())
        {
            Item x = it.next();
            Vector<String> aux = new Vector<>();
            aux.add(x.getName());
            aux.add("" + x.getId());
            aux.add("" + x.getDepId());
            aux.add("" + x.getPrice());
            shopTableData.add(aux);
        }

        shopTableModel.setDataVector(shopTableData, headerNames);
        shopTableListener();
    }

    public void shopTableListener()
    {
        shopTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if(event.getFirstIndex()>=0 && event.getLastIndex()<=shopTableData.size() && event.getFirstIndex()<event.getLastIndex()) {
                            int viewRow = shopTable.getSelectedRow();
                            fID.setText(shopTableModel.getValueAt(viewRow, 1).toString());
                            fName.setText(shopTableModel.getValueAt(viewRow, 0).toString());
                            fIDDep.setText(shopTableModel.getValueAt(viewRow, 2).toString());
                            fPrice.setText(shopTableModel.getValueAt(viewRow, 3).toString());
                        }
                        else
                            if(event.getFirstIndex()>=0 && event.getLastIndex()<=shopTableData.size() && event.getFirstIndex()==event.getLastIndex())
                            {
                                int viewRow = event.getFirstIndex();
                                fID.setText(shopTableModel.getValueAt(viewRow, 1).toString());
                                fName.setText(shopTableModel.getValueAt(viewRow, 0).toString());
                                fIDDep.setText(shopTableModel.getValueAt(viewRow, 2).toString());
                                fPrice.setText(shopTableModel.getValueAt(viewRow, 3).toString());
                            }
                    }
                }
        );
    }
    public void updateScroll()
    {
        if (scrollShop == null)
        {
            scrollShop = new JScrollPane(shopTable);
            scrollShop.setPreferredSize(new Dimension(1300, 800));
        }
        else scrollShop.setViewportView(shopTable);
    }
    public void updatePrice()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        fTotal.setText("" + df.format(Store.getInstance().getCustomers().get(customerName).shoppingCart.getTotalPrice()));
        fBuget.setText("" + df.format(Store.getInstance().getCustomers().get(customerName).shoppingCart.buget));
    }

    public void addElements()
    {

        if (scrollShop == null)
        {
            scrollShop = new JScrollPane(shopTable);
            scrollShop.setPreferredSize(new Dimension(1300, 800));
        }
        else scrollShop.setViewportView(shopTable);
        add(scrollShop, "wrap, span");

        Font font = new Font("Verdana", Font.PLAIN, 30);

        lID.setFont(font);
        fID.setFont(font);
        add(lID);
        add(fID);

        bRemove.setFont(font);
        bAdauga.setFont(font);
        bSelect.setFont(font);
        add(bRemove);
        add(bAdauga);
        add(bSelect,  "gapleft 30");


        bSelect.addActionListener(this);
        bRemove.addActionListener(this);
        bAdauga.addActionListener(this);
    }
    public void action()
    {
        createCustomersList();
        customersListListener();
        customersList.setSelectedIndex(0);
        Font font = new Font("Verdana", Font.PLAIN, 30);
        customersList.setFont(font);
        lCustomer.setFont(font);
        lTotal.setFont(font);
        fTotal.setFont(font);
        lBuget.setFont(font);
        fBuget.setFont(font);
        add(lCustomer, "split 7, span");
        add(customersList);
        add(lTotal);
        add(fTotal);
        add(lBuget);
        add(fBuget);
        bComanda.setFont(font);
        add(bComanda, "wrap");
        bComanda.addActionListener(this);

        shopTableListener();
        addElements();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().contains("Elimina"))
        {
            PreluareDate.implementEvents(("delItem;" + fID.getText() + ";ShoppingCart;" + customerName).split(";"));
            int i = 0;
            for(Vector<String> x : shopTableData)
            {
                if(x.get(1).equals(fID.getText()))
                    i = shopTableData.indexOf(x);
            }
            shopTableData.remove(i);
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
                for (Vector<String> d : shopTableData) {
                    if (d.get(1).equals(fID.getText()))
                        i = shopTableData.indexOf(d);
                }
                if (i != -1) {
                    JOptionPane.showMessageDialog(Game.getInstance(), "Obiectul exista deja in cosul de cumparaturi.");
                } else {
                    shopTableData.add(x);
                    shopTableModel.fireTableDataChanged();
                    PreluareDate.implementEvents(("addItem;" + aux.getId() + ";ShoppingCart;" + customerName).split(";"));
                }
            }
        }
        if(((JButton)e.getSource()).getText().contains("Comanda"))
        {
            double pret = Store.getInstance().getCustomers().get(customerName).shoppingCart.getTotalPrice();
            Store.getInstance().getCustomers().get(customerName).shoppingCart.buget -= pret;
            for(Vector<String> x : shopTableData)
            {
                PreluareDate.implementEvents(("delItem;" + x.get(1) + ";ShoppingCart;" + customerName).split(";"));
            }

            shopTableData.clear();
            shopTableModel.setDataVector(shopTableData, headerNames);
            shopTableListener();
        }
        if(((JButton)e.getSource()).getText().contains("Strategy"))
        {
            Item aux = Store.getInstance().getCustomers().get(customerName).wishList.executeStrategy();
            if(Store.getInstance().getCustomers().get(customerName).shoppingCart.buget >= aux.getPrice())
            {
                Vector<String> x = new Vector<>();
                x.add(aux.getName());
                x.add("" + aux.getId());
                x.add("" + aux.getDepId());
                x.add("" + aux.getPrice());
                shopTableData.add(x);
                shopTableModel.fireTableDataChanged();
            }
        }
        updateScroll();
        updatePrice();
    }
}
