/**
 * Created by Alex on 1/4/2017.
 */
public interface Subject {
    void addObserver(Customer customer);
    void removeObserver(Customer customer);
    void notifyAllObservers(Notification notification);
}
