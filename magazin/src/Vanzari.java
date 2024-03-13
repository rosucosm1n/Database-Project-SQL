import javax.swing.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class Vanzari {
    public Vanzari()
    {
        JFrame frame = new JFrame("Magazin");
        frame.setSize(200, 800); // Set the size of the window

        JPanel panel = new JPanel();
        panel.setLayout(null);
        DataBaseManager dmn=DataBaseManager.getDBM();
        List<vanzare> V=dmn.getVAnzari();

        JLabel lb1=new JLabel("nr articole");
        JLabel lb2=new JLabel("pret");

        lb1.setBounds(0,0,100,20);
        lb2.setBounds(100,0,100,20);

        panel.add(lb1);
        panel.add(lb2);

        for (int i=0;i<V.size();i++)
        {
            int n=V.get(i).nrItems;
            double p=V.get(i).pret;
            JLabel st=new JLabel(Integer.toString(n));
            JLabel dr=new JLabel(Double.toString(p));
            st.setBounds(0,(i+1)*20,100,20);
            dr.setBounds(100,(i+1)*20,100,20);

            panel.add(st);
            panel.add(dr);
        }
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
