import java.util.Date;

/**
 * Created by Alex on 12/28/2016.
 */
public class Notification {
    private Date date;
    public enum NotificationType{
        ADD, REMOVE, MODIFY
    }

    private NotificationType notificationType;
    private int IdDepartment;
    private int IdProduct;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public int getIdDepartment() {
        return IdDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        IdDepartment = idDepartment;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public Notification(Date date, NotificationType notificationType, int idDepartment, int idProduct) {
        this.date = date;
        this.notificationType = notificationType;
        IdDepartment = idDepartment;
        IdProduct = idProduct;
    }

    public String toString()
    {
        return notificationType.toString() + ";" + IdProduct + ";" + IdDepartment;
    }
}
