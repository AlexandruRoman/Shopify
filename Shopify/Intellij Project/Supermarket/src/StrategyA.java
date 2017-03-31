/**
 * Created by Alex on 1/6/2017.
 */
public class StrategyA implements Strategy{
    @Override
    public Item execute(WishList wishList) {
        Item aux = wishList.getItem(1);
        wishList.remove(aux);
        for(Customer c : Store.getInstance().getDepartment(aux.getDepId()).temporaryCustomers)
        {
            Store.getInstance().getDepartment(aux.getDepId()).removeObserver(c);
        }
        Store.getInstance().getDepartment(aux.getDepId()).temporaryCustomers.clear();
        wishList.customerDad.shoppingCart.add(aux);
        return aux;
    }
}
