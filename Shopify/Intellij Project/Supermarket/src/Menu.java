import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JMenu implements MouseListener{

    public Menu(JFrame frame)
    {
        super();
        JMenuBar menuBar = new JMenuBar();

        JMenu shopMenu = new JMenu("Administrator");
        shopMenu.setIcon(new ImageIcon("icons/shop.png"));
        shopMenu.addMouseListener(this);
        JMenu cartMenu = new JMenu("Shopping cart");
        cartMenu.setIcon(new ImageIcon("icons/cart.png"));
        cartMenu.addMouseListener(this);
        JMenu wishMenu = new JMenu("Wishlist");
        wishMenu.setIcon(new ImageIcon("icons/wish.png"));
        wishMenu.addMouseListener(this);
        JMenu notificationMenu = new JMenu("Notificari");
        notificationMenu.setIcon(new ImageIcon("icons/notification.png"));
        notificationMenu.addMouseListener(this);

        menuBar.add(shopMenu);
        menuBar.add(cartMenu);
        menuBar.add(wishMenu);
        menuBar.add(notificationMenu);
        frame.setJMenuBar(menuBar);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(((JMenu)e.getSource()).getText().contains("Administrator"))
        {
            Game.getInstance().changePanel("administrator");
        }
        else if(((JMenu)e.getSource()).getText().contains("Shopping"))
        {
            Game.getInstance().changePanel("shoppingcart");
            Game.getInstance().pShop.updateScroll();
        }
        else if(((JMenu)e.getSource()).getText().contains("Wishlist"))
        {
            Game.getInstance().changePanel("wishlist");
            Game.getInstance().pWish.updateScroll();
        }
        else if(((JMenu)e.getSource()).getText().contains("Notificari"))
        {
            Game.getInstance().changePanel("notificari");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
