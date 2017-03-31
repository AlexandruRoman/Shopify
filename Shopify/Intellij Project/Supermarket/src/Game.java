import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Game extends JFrame {

    private static Game instance;

    Intro pIntro;
    Administrator pAdministrator;
    Shop pShop;
    Wish pWish;
    Note pNote;
    JPanel controlPanel = new JPanel();
    CardLayout cardLayout = new CardLayout();

    HashMap<String, JPanel> panelsList = new HashMap<>();

    public Game(String name)
    {
        super(name);
        setBasic();
    }
    public void action()
    {
        Menu menu = new Menu(this);
        addPanels();
    }
    public static Game getInstance(){
        return instance;
    }
    public void addPanels()
    {
        pIntro = new Intro();
        pAdministrator = new Administrator();
        pShop = new Shop();
        pWish = new Wish();
        pNote = new Note();

        controlPanel.setLayout(cardLayout);
        //Panels
        controlPanel.add(pIntro, "intro");
        controlPanel.add(pAdministrator, "administrator");
        controlPanel.add(pShop, "shoppingcart");
        controlPanel.add(pWish, "wishlist");
        controlPanel.add(pNote, "notificari");

        add(controlPanel);
        changePanel("intro");
    }
    public void changePanel(String name)
    {
        cardLayout.show(controlPanel, name);
    }
    public void setBasic()
    {
        setSize(1280, 960);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new MigLayout());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                instance = new Game("Shopify");
                instance.action();
            }
        });
    }
}
