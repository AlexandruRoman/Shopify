import java.util.HashMap;

/**
 * Created by Alex on 12/28/2016.
 */
public class Store {
    private static Store instance = new Store();
    private String name;
    private HashMap<Integer, Department> departments;
    private HashMap<String, Customer> customers;

    public static Store getInstance()
    {
        return instance;
    }
    public Store() {
        departments = new HashMap<>();
        customers = new HashMap<>();
    }
    public void enter(Customer customer)
    {
        customers.put(customer.name, customer);
    }
    public void exit(Customer customer)
    {
        customers.remove(customer.name);
    }
    public ShoppingCart getShoppingCart(double buget)
    {
        return new ShoppingCart(buget);
    }
    public HashMap<String, Customer> getCustomers()
    {
        return customers;
    }
    public HashMap<Integer, Department> getDepartments()
    {
        return departments;
    }
    public void addDepartment(Department department)
    {
        departments.put(department.getId(), department);
    }
    public Department getDepartment(Integer id)
    {
        return departments.get(id);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Item findItem(int id)
    {
        for(Department d : departments.values())
        {
            if(d.getItems().containsKey(id))
                return d.getItems().get(id);
        }
        return null;
    }
}
