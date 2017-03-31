import java.util.Iterator;

/**
 * Created by Alex on 12/28/2016.
 */
public class WishList extends ItemList {
    public Customer customerDad;
    private Strategy strategy;

    private void chooseStrategy()
    {
        if(customerDad.strategy == 'A')
        {
            strategy = new StrategyA();
        }
        if(customerDad.strategy == 'B')
        {
            strategy = new StrategyB();
        }
        if(customerDad.strategy == 'C')
        {
            strategy = new StrategyC();
        }
    }

    private void chooseComparator()
    {
        if(customerDad.strategy == 'A')
        {
            super.setComparator(new ItemComparatorByPriceAscending());
        }
        if(customerDad.strategy == 'B')
        {
            super.setComparator(new ItemComparatorByName());
        }
        if(customerDad.strategy == 'C')
    {
        super.setComparator(null);
    }
    }

    public WishList(Customer customer) {
        super(null);
        customerDad = customer;
        chooseComparator();
        chooseStrategy();
    }

    public Item executeStrategy()
    {
        return strategy.execute(this);
    }

    public boolean add(Item item)
    {
        Store.getInstance().getDepartment(item.getDepId()).addObserver(customerDad);
        return super.add(item);
    }

    public boolean remove(Item item)
    {
        if(super.remove(item))
        {
            //System.out.println("-----" + customerDad.name + " " + item);
            //verific daca dupa stergere mai ramane vreun produs din shoppingCart care sa fie in departament
            boolean ok = false;
            Iterator iterator = listIterator();
            Item aux;
            while(iterator.hasNext())
            {
                aux = (Item)iterator.next();
                if(aux.getDepId() == item.getDepId()) {
                    ok = true;
                }
            }
            if(ok == false) {
                //Store.getInstance().getDepartment(item.getDepId()).removeObserver(customerDad);
                Store.getInstance().getDepartment(item.getDepId()).temporaryCustomers.add(customerDad);
            }
            return true;
        }
        return false;
    }

}
