
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class Note extends JPanel{

    Vector<Vector<String>> noteTableData = new Vector<>();
    JTable noteTable = new JTable();
    DefaultTableModel noteTableModel;
    Vector<String> headerNames;

    JComboBox customersList;

    JLabel lCustomer = new JLabel("Alegeti clientul:");

    String customerName;


    public Note()
    {
        super();
        setLayout(new MigLayout());
        headerNames = new Vector<>();
        headerNames.add("Tip");
        headerNames.add("ID produs");
        headerNames.add("ID departament");
        headerNames.add("Data");
        noteTableModel = new DefaultTableModel(noteTableData, headerNames);
        noteTable.setModel(noteTableModel);
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
                    createnoteTable(Store.getInstance().getCustomers().get(e.getItem().toString()));
                }
            }
        });
    }

    public void createnoteTable(Customer customer)
    {
        noteTableData.clear();
        for(Notification n : customer.notifications)
        {
            Vector<String> aux = new Vector<>();
            aux.add(n.getNotificationType().toString());
            aux.add("" + n.getIdProduct());
            aux.add("" + n.getIdDepartment());
            aux.add("" + n.getDate().toString());
            noteTableData.add(aux);
        }

        noteTableModel.setDataVector(noteTableData, headerNames);
    }


    public void addElements()
    {
        JScrollPane scrollnote = new JScrollPane(noteTable);
        scrollnote.setPreferredSize(new Dimension(1300, 800));
        add(scrollnote, "wrap, span");
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

        addElements();
    }
}
