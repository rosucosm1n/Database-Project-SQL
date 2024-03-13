import javax.swing.*;
import java.util.List;

public class ViewProduse{
    public static void Show()
    {
        JFrame frame = new JFrame("Produse");
        frame.setSize(600, 800); // Set the size of the window

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        DataBaseManager dmn=DataBaseManager.getDBM();

        List<String> s=dmn.getProduse();
        JLabel lb0=new JLabel("nume");
        JLabel lb1=new JLabel("nr articole");
        JLabel lb2=new JLabel("pretBaza");
        JLabel lb3=new JLabel("pretFinal");
        for (int i=0;i<s.size();i++)
        {
            String data=s.get(i);
            data=data+dmn.getProductData(s.get(i));
            JLabel Data=new JLabel(data);
            Data.setBounds(0,(i+1)*20,420,20);
            panel.add(Data);
        }

        lb0.setBounds(0,0,100,20);
        lb1.setBounds(100,0,100,20);
        lb2.setBounds(200,0,100,20);
        lb3.setBounds(300,0,100,20);

        panel.add(lb0);
        panel.add(lb1);
        panel.add(lb2);
        panel.add(lb3);

        frame.setVisible(true);

    }
}
