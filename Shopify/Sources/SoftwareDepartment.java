import java.util.Iterator;

/**
 * Created by Alex on 1/3/2017.
 */
public class SoftwareDepartment extends Department {
    @Override
    public void accept(ShoppingCart shoppingCart) {
        shoppingCart.visit(this);
    }
    public SoftwareDepartment() {
        super();
        this.name = "SoftwareDepartment";
    }
}
