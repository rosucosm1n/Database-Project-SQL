import javax.swing.*;
import java.awt.*;

public class InserareTipProdus {
    static String s="";
    static double tva=-1;
    static double adaos=-1;
    private static final  DataBaseManager dbm=DataBaseManager.getDBM();

    public static void Insert()
    {
        JFrame frame = new JFrame("Inserare TIp Produs");
        frame.setSize(1200, 200);
        frame.setLocation(900, 700);
        JPanel panel = new JPanel(new FlowLayout()); // Use a layout manager
        frame.add(panel);

        JLabel text1=new JLabel("Descriere Tip Produs:");
        TextField name=new TextField("  ");
        name.addActionListener(e->{
            s= name.getText();
        });

        panel.add(text1);
        panel.add(name);

        JLabel text2=new JLabel("Adaos intre 0.01 si 0.70:");
        TextField Adaos=new TextField(" ");
        Adaos.addActionListener(e->{
            try {
                adaos= Double.parseDouble(Adaos.getText());
            }
            catch (Exception E){
                System.out.println("Aici");}
        });

        panel.add(text2);
        panel.add(Adaos);


        JLabel text3=new JLabel("TVA intre 0.09 si 0.24:");
        TextField Tva=new TextField(" ");
        Tva.addActionListener(e->{
            try {
                tva= Double.parseDouble(Tva.getText());
            }
            catch (Exception E){System.out.println("Aici");}
        });

        panel.add(text3);
        panel.add(Tva);

        JButton Submit=new JButton("Adauga");
        Submit.addActionListener(e->{
            if (s!=""&&tva!=-1&&adaos!=-1)
            {
                dbm.insertOrUpdateTipProduse(s,adaos,tva);
                s="";
                tva=-1;
                adaos=-1;
                name.setText("  ");
                Adaos.setText(" ");
                Tva.setText(" ");
            }
            else{
                System.out.println("Erori date "+s+" "+Double.toString(adaos)+" "+Double.toString(tva));
            }
        });

        panel.add(Submit);
        frame.setVisible(true);
    }
}
