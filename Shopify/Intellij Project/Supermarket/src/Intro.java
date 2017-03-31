import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 1/9/2017.
 */
public class Intro extends JPanel implements ActionListener{

    JButton bIncarca = new JButton("Incarca datele");

    public Intro() {
        super();
        setLayout(new MigLayout());
        bIncarca.addActionListener(this);
        add(bIncarca);
    }

    public void incarcaDatele()
    {
        String path = "tests/test05/";
        PreluareDate.preluareStore(path);
        PreluareDate.preluareCustomers(path);
        PreluareDate.preluareEvents(path);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        incarcaDatele();
        Game.getInstance().pAdministrator.action();
        Game.getInstance().pShop.action();
        Game.getInstance().pWish.action();
        Game.getInstance().pNote.action();
        Game.getInstance().changePanel("administrator");
    }
}
