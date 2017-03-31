import java.util.Iterator;

/**
 * Created by Alex on 12/28/2016.
 */
public class ShoppingCart extends ItemList implements Visitor{
    public double buget;
    public Customer customerDad;

    public ShoppingCart(double buget) {
        super(new ItemComparatorByPriceAscending());
        this.buget = buget;

    }
    public boolean add(Item item)
    {
        if(buget > item.getPrice()) {
            Store.getInstance().getDepartment(item.getDepId()).enter(customerDad);
            buget -= item.getPrice();
            return super.add(item);
        }
        return false;
    }

    public boolean remove(Item item)
    {
        if(super.remove(item))
        {
            buget += item.getPrice();
            //verific daca dupa stergere mai ramane vreun produs din shoppingCart care sa fie in departament
            boolean ok = false;
            Iterator iterator = listIterator();
            Item aux;
            while(iterator.hasNext())
            {
                aux = (Item)iterator.next();
                if(aux.getDepId() == item.getDepId())
                    ok = true;
            }
            if(ok == false)
                Store.getInstance().getDepartment(item.getDepId()).exit(customerDad);
            return true;
        }
        return false;
    }


    @Override
    public void visit(BookDepartment bookDepartment) {
        double startPrice = getTotalPrice();
        Iterator it = listIterator();
        Item aux;
        while(it.hasNext())
        {
            aux = (Item)it.next();
            if(bookDepartment.items.containsKey(aux.getId())) {
                aux.setPrice(aux.getPrice() * 9 / 10);
            }
        }
        buget += startPrice - getTotalPrice();
    }

    @Override
    public void visit(MusicDepartment musicDepartment) {
        Double s = 0.0;
        Iterator it = listIterator();
        Item aux;
        while(it.hasNext())
        {
            aux = (Item)it.next();
            if(musicDepartment.items.containsKey(aux.getId()))
                s+=aux.getPrice();
        }
        buget += s/10;
    }

    @Override
    public void visit(SoftwareDepartment softwareDepartment) {
        double startPrice = getTotalPrice();
        double min = 5000.0;
        for(Item item : softwareDepartment.items.values())
        {
            if(min < item.getPrice())
                min = item.getPrice();
        }
        if(buget < min) {
            Iterator it = listIterator();
            Item aux;
            while (it.hasNext()) {
                aux = (Item) it.next();
                if (softwareDepartment.items.containsKey(aux.getId()))
                    aux.setPrice(aux.getPrice() * 8 / 10);
            }
            buget += startPrice - getTotalPrice();
        }
    }

    @Override
    public void visit(VideoDepartment videoDepartment) {
        double startPrice = getTotalPrice();
        double s = 0;
        double max = 0;
        for(Item item : videoDepartment.items.values())
        {
            if(max < item.getPrice())
                max = item.getPrice();
        }
        Iterator it = listIterator();
        Item aux;
        while (it.hasNext()) {
            aux = (Item) it.next();
            if (videoDepartment.items.containsKey(aux.getId()))
                s += aux.getPrice();
        }
        if(s > max)
        {
            it = listIterator();
            while (it.hasNext()) {
                aux = (Item) it.next();
                if (videoDepartment.items.containsKey(aux.getId()))
                    aux.setPrice(aux.getPrice() * 85 / 100);
            }
        }
        buget += s*5/100;
        buget += startPrice - getTotalPrice();
    }
}
