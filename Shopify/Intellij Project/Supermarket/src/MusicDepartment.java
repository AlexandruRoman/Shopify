/**
 * Created by Alex on 1/3/2017.
 */
public class MusicDepartment extends Department{
    @Override
    public void accept(ShoppingCart shoppingCart) {
        shoppingCart.visit(this);
    }
    public MusicDepartment() {
        super();
        this.name = "MusicDepartment";
    }
}
