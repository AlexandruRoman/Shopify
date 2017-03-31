import java.util.*;

/**
 * Created by Alex on 12/28/2016.
 */
public abstract class Department implements Subject {
    public String name;
    private int Id;
    protected HashMap<Integer, Item> items;
    private LinkedHashMap<String, Customer> customersServed;
    private LinkedHashMap<String, Customer> customersWishing;
    public void enter(Customer customer)
    {
        customersServed.put(customer.name, customer);
    }
    public void exit(Customer customer)
    {
        customersServed.remove(customer.name);
    }
    public LinkedHashMap<String, Customer> getCustomers()
    {
        return customersServed;
    }
    public Vector<Customer> temporaryCustomers;

    public void showObservers() {

        String s = "[";
        for(Customer c : customersWishing.values())
        {
            if(s.equals("["))
                s += c.name;
            else
                s += ", " + c.name;
        }
        s += "]";
        System.out.println(s);

    }

    public int getId()
    {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public void addItem(Item item)
    {
        items.put(item.getId(), item);
    }
    public HashMap<Integer, Item> getItems()
    {
        return items;
    }
    public void addObserver(Customer customer)
    {
        customersWishing.put(customer.name, customer);
    }
    public void removeObserver(Customer customer)
    {
        customersWishing.remove(customer.name);
    }
    public void notifyAllObservers(Notification notification)
    {
        for(Customer c : customersWishing.values())
        {
            c.update(notification);
        }
        if(notification.getNotificationType() == Notification.NotificationType.REMOVE)
        for(Customer c : temporaryCustomers) {
            removeObserver(c);
        }
        temporaryCustomers.clear();
    }

    public Department()
    {
        items = new HashMap<>();
        customersServed = new LinkedHashMap<>();
        customersWishing = new LinkedHashMap<>();
        temporaryCustomers = new Vector<>();
    }


    public abstract void accept(ShoppingCart shoppingCart);
}
