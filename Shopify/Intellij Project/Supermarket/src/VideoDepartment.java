/**
 * Created by Alex on 1/4/2017.
 */
public class VideoDepartment extends Department{

    @Override
    public void accept(ShoppingCart shoppingCart) {
        shoppingCart.visit(this);
    }

    public VideoDepartment() {
        super();
        this.name = "VideoDepartment";
    }
}
