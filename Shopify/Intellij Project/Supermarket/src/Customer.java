import java.util.Vector;

/**
 * Created by Alex on 12/28/2016.
 */
public class Customer implements Observer{
    public String name;
    public ShoppingCart shoppingCart;
    public WishList wishList;
    public Vector<Notification> notifications;
    public Character strategy;

    public Customer(String name, double buget, Character strategy) {
        this.name = name;
        this.strategy = strategy;
        shoppingCart = Store.getInstance().getShoppingCart(buget);
        shoppingCart.customerDad = this;
        wishList = new WishList(this);
        notifications = new Vector<>();
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
        if(notification.getNotificationType() == Notification.NotificationType.MODIFY)
        {
            Item aux = Store.getInstance().getDepartment(notification.getIdDepartment()).getItems().get(notification.getIdProduct());
            Item auxShoppingCart = shoppingCart.getItem(shoppingCart.indexOf(Store.getInstance().findItem(notification.getIdProduct())));
            if(auxShoppingCart != null) {
                shoppingCart.remove(auxShoppingCart);
                shoppingCart.add(aux);
            }
            Item auxWishList = wishList.getItem(wishList.indexOf(aux));
            if(auxWishList != null) {
                wishList.remove(auxWishList);
                wishList.add(aux);
            }
        }
        else if(notification.getNotificationType() == Notification.NotificationType.REMOVE)
        {
            Item aux = Store.getInstance().getDepartment(notification.getIdDepartment()).getItems().get(notification.getIdProduct());
            shoppingCart.remove(aux);
            wishList.remove(aux);
        }
    }

    public void showNotifications()
    {
        String s = "[";
        for(Notification n : notifications)
        {
            if(s.equals("["))
                s += n.toString();
            else
                s += ", " + n.toString();
        }
        s += "]";
        System.out.println(s);
    }
}
