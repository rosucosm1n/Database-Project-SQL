import javax.swing.*;
import java.awt.*;

public class InserareProdus{
    static int x = 0;
    static double P=-1;
    static double Pf;
    static String s="";
    static int tip=-1;
    private static final  DataBaseManager dbm=DataBaseManager.getDBM();
    public static void Insert() {
        JFrame frame = new JFrame("Inserare Produs");
        frame.setSize(1200, 200);
        frame.setLocation(900, 700);

        JPanel panel = new JPanel(new FlowLayout()); // Use a layout manager
        frame.add(panel);



        JLabel nume =new JLabel("Nume :");
        JTextField insNume=new JTextField("                  ");
        insNume.addActionListener(e->{
            s=insNume.getText();
        });
        panel.add(nume);
        panel.add(insNume);

        JLabel lbl = new JLabel("Tip Produs:");
        panel.add(lbl);
        String[] choices = dbm.getTipProduse().toArray(new String[0]);
        final JComboBox<String> cb = new JComboBox<>(choices);
        cb.setMaximumSize(cb.getPreferredSize());
        panel.add(cb);


        JLabel lbl3=new JLabel("Pret Baza: ");
        JLabel pretfinal=new JLabel("pret final: ");
        JTextField pret=new JTextField("           ");
        pret.addActionListener(e->{
            try{
            P=Double.parseDouble(pret.getText());
            Pf=P*(1+dbm.getAdaosTva(choices[cb.getSelectedIndex()]));
            pretfinal.setText("pret final: "+Double.toString(Pf));}
            catch (Exception E)
            {
                pretfinal.setText("Introduceti nr ex: 3.12");
            }
        });



        JLabel lbl2 = new JLabel(Integer.toString(x));

        JButton btn = new JButton("Adauga");
        btn.addActionListener(e -> {
            if (x!=0&&P!=-1&&s!="") {
                dbm.insertOrUpdateProdus(dbm.getTipProdusID(choices[cb.getSelectedIndex()]), x, P, s);
                x=0;
                lbl2.setText(Integer.toString(x));
                P=-1;
                pret.setText("       ");
                insNume.setText("        ");
                s="";
            }
        });

        JButton btn2 = new JButton("+");
        JButton btn3=new JButton("-");
        btn3.addActionListener(e->{
            if (x>0){
                x--;

            }
        });
        btn2.addActionListener(e -> {
            x++;
            lbl2.setText(Integer.toString(x));
        });

        panel.add(lbl3);
        panel.add(pret);
        panel.add(pretfinal);

        panel.add(new JLabel("Stoc: "));
        panel.add(lbl2);
        panel.add(btn2);
        panel.add(btn3);

        panel.add(btn);
        frame.setVisible(true);
    }
}
