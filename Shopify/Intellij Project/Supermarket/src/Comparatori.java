import java.util.Comparator;
import java.util.Vector;

/**
 * Created by Alex on 1/3/2017.
 */
class RowComparator_AscendingByName implements Comparator<Vector<String>> {
    @Override
    public int compare(Vector<String> row1, Vector<String> row2) {
        return row1.get(0).compareTo(row2.get(0));
    }
}
class RowComparator_DescendingByName implements Comparator<Vector<String>> {
    @Override
    public int compare(Vector<String> row1, Vector<String> row2) {
        return row2.get(0).compareTo(row1.get(0));
    }
}
class RowComparator_AscendingByPrice implements Comparator<Vector<String>> {
    @Override
    public int compare(Vector<String> row1, Vector<String> row2) {
        return row1.get(3).compareTo(row2.get(3));
    }
}
class RowComparator_DescendingByPrice implements Comparator<Vector<String>> {
    @Override
    public int compare(Vector<String> row1, Vector<String> row2) {
        return row2.get(3).compareTo(row1.get(3));
    }
}