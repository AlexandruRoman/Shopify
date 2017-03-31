import java.util.Comparator;

/**
 * Created by Alex on 1/3/2017.
 */
class ItemComparatorByName implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getName().compareTo(item2.getName());
    }
}

class ItemComparatorByPriceAscending implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getPrice().compareTo(item2.getPrice());
    }
}