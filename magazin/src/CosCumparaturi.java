import javax.swing.*;
import java.awt.*;

public class CosCumparaturi {
    private int x = 0;
    private int stoc=0;
    private double pUnitate=0;
    private double pTotal=0;
    private int nrAdaugate=0;
    private static JLabel jl1=new JLabel("0"),jl2=new JLabel("0");
    private JButton addBtn = new JButton("Adauga");
    private JButton plusBtn= new JButton("+");
    private JButton minusBtn= new JButton("-");
    private JLabel produsNume=new JLabel("");
    private JLabel nrOfUnits=new JLabel("");
    private JLabel pretUnitate=new JLabel("");
    private JLabel pretTotal=new JLabel("0");
    private static final DataBaseManager dbm=DataBaseManager.getDBM();

    CosCumparaturi(int index, String s,double pret,int stoc)
    {
        pUnitate=pret;
        this.stoc=stoc;
        produsNume.setText(s);
        nrOfUnits.setText(Integer.toString(x));
        pretUnitate.setText(Double.toString(pUnitate));

        produsNume.setBounds(40,100+index*20,150,20);
        plusBtn.setBounds(190,100+index*20,50,20);
        nrOfUnits.setBounds(240,100+index*20,80,20);
        minusBtn.setBounds(320,100+index*20,40,20);
        addBtn.setBounds(360,100+index*20,150,20);
        pretUnitate.setBounds(510,100+index*20,80,20);
        pretTotal.setBounds(590,100+index*20,80,20);
        minusBtn.addActionListener(e->{
            if (x>0)
                x--;
            nrOfUnits.setText(Integer.toString(x));
        });

        plusBtn.addActionListener(e->{
            if (x<this.stoc)
                x++;
            nrOfUnits.setText(Integer.toString(x));
        });

        addBtn.addActionListener(e -> {

            pTotal=x*pUnitate;
            nrAdaugate=x;
            pretTotal.setText(Double.toString(pTotal));
            dbm.insertIntoCosCump(dbm.getProdusId(s),nrAdaugate,pTotal);
            CosCumparaturi.Update();
        });
    }
    public void Display(JPanel panel)
    {
        panel.add(addBtn);
        panel.add(minusBtn);
        panel.add(plusBtn);
        panel.add(produsNume);
        panel.add(nrOfUnits);
        panel.add(pretTotal);
        panel.add(pretUnitate);

        panel.repaint();
    }
    public static void DisplayS(JPanel panel)
    {
        jl1.setBounds(250,20,80,20);
        jl2.setBounds(330,20,80,20);
        panel.add(jl1);
        panel.add(jl2);
        panel.repaint();
    }
    public static void Update()
    {
        jl1.setText(Integer.toString(dbm.getNrProdFromCosCump()));
        jl2.setText(Double.toString(dbm.getPTotalFromCosCump()));
    }
}
