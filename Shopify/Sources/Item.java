import java.text.DecimalFormat;

/**
 * Created by Alex on 12/28/2016.
 */
public class Item {
    private String name;
    private int Id;
    private Double price;
    private int depId;

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public Item(String name)
    {
        this.name = name;
    }

    public Item(String name, int id, Double price, int depId) {
        this.name = name;
        Id = id;
        this.price = price;
        this.depId = depId;
    }
    public Item(Item item)
    {
        name = item.name;
        Id = item.Id;
        price = item.price;
        depId = item.depId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return name + ";" + Id + ";" + String.format("%.2f", price);
    }
}
