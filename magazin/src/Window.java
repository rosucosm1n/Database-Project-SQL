import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Window {
    public JFrame frame;
    public JPanel panel;
    public int mode;
    private static final DataBaseManager dbm=DataBaseManager.getDBM();
    public Window() {
        frame = new JFrame("Magazin");
        frame.setSize(800, 600); // Set the size of the window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        mode=0;
    }
    private void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    public void DisplayMeniu(){
        JButton clientButton = new JButton("Client");
        JButton adminButton = new JButton("Administrator");
        clientButton.setBounds(150,0,100,20);
        adminButton.setBounds(250,0,120,20);

        // Add action listeners for the buttons (you can implement specific actions as needed)
        clientButton.addActionListener(e -> {
            // Action for the client button
            mode=1;
            DisplayWindow();
        });

        adminButton.addActionListener(e -> {
            // Action for the administrator button
            mode=2;
            DisplayWindow();
        });

        panel.add(clientButton);
        panel.add(adminButton);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    public void DisplayWindow()
    {
        switch (mode) {
            case 0:
                clearPanel();
                DisplayMeniu();
                break;
            case 1:
                clearPanel();
                DisplayCLient();
                break;
            case 2:
                clearPanel();
                DisplayAdministrator();
                break;
        }
    }
    public void DisplayAdministrator()
    {
        JButton BAckButton = new JButton("Back");
        BAckButton.setBounds(0,0,80,20);
        BAckButton.addActionListener(e -> {
            mode=0;
            DisplayWindow();
        });
        panel.add(BAckButton);

        JButton v = new JButton("Vizualizare Vanzari");
        v.setBounds(0,100,150,20);
        v.addActionListener(e->{
            Vanzari V=new Vanzari();
        });

        JButton I = new JButton("Inserare Produs");
        I.setBounds(0,160,150,20);
        I.addActionListener(e->{
            InserareProdus.Insert();
        });

        JButton P = new JButton("Inserare Tip Produs");
        P.setBounds(0,220,150,20);
        P.addActionListener(e->{
            InserareTipProdus.Insert();
        });

        JButton Prod = new JButton("Vizualizare Produse");
        Prod.setBounds(0,280,150,20);
        Prod.addActionListener(e->{
            ViewProduse.Show();
        });

        panel.add(Prod);
        panel.add(P);
        panel.add(I);
        panel.add(v);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    public void DisplayCLient()
    {
        JButton BAckButton = new JButton("Back");
        BAckButton.setBounds(0,0,80,20);
        BAckButton.addActionListener(e -> {
            dbm.deleteFromCOsCump();
            mode=0;
            DisplayWindow();
        });
        JButton Cumpara = new JButton("Cumpara");
        Cumpara.setBounds(100,20,150,20);
        Cumpara.addActionListener(e -> {
            if (dbm.getNrProdFromCosCump()>0){
            dbm.insertIntoVanzare(dbm.getNrProdFromCosCump(), dbm.getPTotalFromCosCump());
            dbm.deleteFromCOsCump();
            DisplayWindow();
            CosCumparaturi.Update();
        }});

        List<String> s=dbm.getProduse();
        List<CosCumparaturi> c=new ArrayList<CosCumparaturi>();
        for (int i=0;i<s.size();i++) {
            int stoc=dbm.getStoc(s.get(i));
            double pret= dbm.getPret(s.get(i));
            c.add(new CosCumparaturi(i, s.get(i), pret,stoc));
            c.get(i).Display(panel);
        }
        CosCumparaturi.DisplayS(panel);

        panel.add(Cumpara);
        panel.add(BAckButton);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        dbm.deleteFromCOsCump();
        Window w=new Window();
        w.DisplayWindow();
    }
}