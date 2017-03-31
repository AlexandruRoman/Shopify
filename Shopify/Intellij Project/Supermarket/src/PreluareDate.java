import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Alex on 1/4/2017.
 */
public class PreluareDate {

    public static void preluareStore(String path)
    {
        File file = new File(path + "store.txt");
        String line;
        String[] texts;
        String numeProdus;
        int IdProdus;
        double pretProdus;
        int nrLinii;
        try {
            Scanner scanner = new Scanner(file);
            String storeName = scanner.nextLine();
            Store.getInstance().setName(storeName);
            for(int i=0; i<4; i++)
            {
                line = scanner.nextLine();
                texts = line.split(";");
                Department department = DepartmentFactory.getDepartment(texts[0]);
                department.setId(Integer.parseInt(texts[1]));
                Store.getInstance().addDepartment(department);

                line = scanner.nextLine();
                nrLinii = Integer.parseInt(line);
                for(int j=0; j<nrLinii; j++)
                {
                    line = scanner.nextLine();
                    texts = line.split(";");
                    numeProdus = texts[0];
                    IdProdus = Integer.parseInt(texts[1]);
                    pretProdus = Double.parseDouble(texts[2]);
                    department.addItem(new Item(numeProdus, IdProdus,pretProdus, department.getId()));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void preluareCustomers(String path)
    {
        File file = new File(path + "customers.txt");
        String line;
        String[] texts;
        String numeCustomer;
        double buget;
        Character strategy;
        int nrLinii;
        try {
            Scanner scanner = new Scanner(file);
            line = scanner.nextLine();
            nrLinii = Integer.parseInt(line);
            for(int i=0; i<nrLinii; i++)
            {
                line = scanner.nextLine();
                texts = line.split(";");
                numeCustomer = texts[0];
                buget = Double.parseDouble(texts[1]);
                strategy = texts[2].charAt(0);
                Store.getInstance().enter(new Customer(numeCustomer, buget, strategy));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void preluareEvents(String path)
    {
        File file = new File(path + "events.txt");
        String line;
        String[] texts;
        int nrLinii;
        try {
            Scanner scanner = new Scanner(file);
            line = scanner.nextLine();
            nrLinii = Integer.parseInt(line);
            for(int i=0; i<nrLinii; i++)
            {
                line = scanner.nextLine();
                texts = line.split(";");
                implementEvents(texts);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void implementEvents(String[] tokens)
    {
        String command = tokens[0];
        if(command.equals("addItem"))
        {
            int itemId = Integer.parseInt(tokens[1]);
            String listOption = tokens[2];
            String customerName = tokens[3];
            Item aux = Store.getInstance().findItem(itemId);

            if(listOption.equalsIgnoreCase("shoppingcart")) {
                Store.getInstance().getCustomers().get(customerName).shoppingCart.add(aux);
                //System.out.println(Store.getInstance().getCustomers().get(customerName).shoppingCart.toString());
            }
            if(listOption.equalsIgnoreCase("wishlist"))
                Store.getInstance().getCustomers().get(customerName).wishList.add(aux);
        }
        else if(command.equals("delItem"))
        {
            int itemId = Integer.parseInt(tokens[1]);
            String listOption = tokens[2];
            String customerName = tokens[3];
            Item aux = Store.getInstance().findItem(itemId);

            if(listOption.equalsIgnoreCase("shoppingcart"))
                Store.getInstance().getCustomers().get(customerName).shoppingCart.remove(aux);
            if(listOption.equalsIgnoreCase("wishlist"))
                Store.getInstance().getCustomers().get(customerName).wishList.remove(aux);
            for(Customer c : Store.getInstance().getDepartment(Store.getInstance().findItem(itemId).getDepId()).temporaryCustomers)
            {
                Store.getInstance().getDepartment(Store.getInstance().findItem(itemId).getDepId()).removeObserver(c);
            }
        }
        else if(command.equals("addProduct"))
        {
            int depId = Integer.parseInt(tokens[1]);
            int itemId = Integer.parseInt(tokens[2]);
            double price = Double.parseDouble(tokens[3]);
            String name = tokens[4];

            Store.getInstance().getDepartment(depId).addItem(new Item(name, itemId, price, depId));
            Notification notification = new Notification(new Date(), Notification.NotificationType.ADD, depId, itemId);
            Store.getInstance().getDepartment(depId).notifyAllObservers(notification);
        }
        else if(command.equals("modifyProduct"))
        {
            int depId = Integer.parseInt(tokens[1]);
            int itemId = Integer.parseInt(tokens[2]);
            double price = Double.parseDouble(tokens[3]);

            Item aux = Store.getInstance().getDepartment(depId).getItems().get(itemId);
            aux.setPrice(price);
            Notification notification = new Notification(new Date(), Notification.NotificationType.MODIFY, depId, itemId);
            Store.getInstance().getDepartment(depId).notifyAllObservers(notification);
        }
        else if(command.equals("delProduct"))
        {
            int itemId = Integer.parseInt(tokens[1]);
            int depId = Store.getInstance().findItem(itemId).getDepId();

            Notification notification = new Notification(new Date(), Notification.NotificationType.REMOVE, depId, itemId);
            Store.getInstance().getDepartment(depId).notifyAllObservers(notification);
            Store.getInstance().getDepartment(depId).getItems().remove(itemId);

        }
        else if(command.equals("getItem"))
        {
            String customerName = tokens[1];
            System.out.println(Store.getInstance().getCustomers().get(customerName).wishList.executeStrategy());
        }
        else if(command.equals("getItems"))
        {
            String listOption = tokens[1];
            String customerName = tokens[2];

            if(listOption.equalsIgnoreCase("shoppingcart"))
            {
                System.out.println(Store.getInstance().getCustomers().get(customerName).shoppingCart.toString(new ItemComparatorByPriceAscending()));
            }
            if(listOption.equalsIgnoreCase("wishlist"))
            {
                System.out.println(Store.getInstance().getCustomers().get(customerName).wishList.toString(new ItemComparatorByName()));
            }
        }
        else if(command.equals("getTotal"))
        {
            String listOption = tokens[1];
            String customerName = tokens[2];
            DecimalFormat df = new DecimalFormat("#.00");


            if(listOption.equalsIgnoreCase("shoppingcart"))
            {
                System.out.println(df.format(Store.getInstance().getCustomers().get(customerName).shoppingCart.getTotalPrice()));
            }
            if(listOption.equalsIgnoreCase("wishlist"))
            {
                System.out.println(df.format(Store.getInstance().getCustomers().get(customerName).wishList.getTotalPrice()));
            }
        }
        else if(command.equals("accept"))
        {
            int depId = Integer.parseInt(tokens[1]);
            String customerName = tokens[2];

            Store.getInstance().getDepartment(depId).accept(Store.getInstance().getCustomers().get(customerName).shoppingCart);
        }
        else if(command.equals("getObservers"))
        {
            int depId = Integer.parseInt(tokens[1]);

            Store.getInstance().getDepartment(depId).showObservers();
        }
        else if(command.equals("getNotifications"))
        {
            String customerName = tokens[1];

            Store.getInstance().getCustomers().get(customerName).showNotifications();
        }
    }

}
