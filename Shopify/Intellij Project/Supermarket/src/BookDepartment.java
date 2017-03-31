/**
 * Created by Alex on 1/3/2017.
 */
public class BookDepartment extends Department {
    @Override
    public void accept(ShoppingCart shoppingCart) {
        shoppingCart.visit(this);
    }

    public BookDepartment() {
        super();
        this.name = "BookDepartment";
    }

}
