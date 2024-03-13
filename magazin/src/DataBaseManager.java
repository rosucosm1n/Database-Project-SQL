import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
public class DataBaseManager {
    private static DataBaseManager dbm=null;
    private Connection c;
    private DataBaseManager()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Baza.db");

        }
        catch (Exception e) {
            System.out.println("Eroare Conectare");
        }


    }
    public static DataBaseManager getDBM()
    {
        if (dbm==null)
            dbm=new DataBaseManager();
        return dbm;
    }

    public List<String> getProduse()
    {
        List<String> s = new ArrayList<String>();
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM produs;");
            while (resultSet.next())
                s.add(resultSet.getString("nume"));
        }
        catch (Exception e)
        {
            System.out.println("Erare getProduse");
        }
        return s;
    }

    public double getPret(String name)
    {
        double d=0;
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM produs where nume = '"+name+"';");
            d=resultSet.getDouble("pretFinal");
        }
        catch (Exception E){}
        return d;
    }
    public int getStoc(String name)
    {
        int s=0;
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM produs where nume = '"+name+"';");
            s=resultSet.getInt("stoc");
        }
        catch (Exception E){}
        return s;
    }
    public int getProdusId(String name)
    {
        int s=0;
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM produs where nume = '"+name+"';");
            s=resultSet.getInt("produsId");
        }
        catch (Exception E){}
        return s;
    }

    public List<String> getTipProduse()
    {
        List<String> s = new ArrayList<String>();
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tipProduse;");
            while (resultSet.next())
                s.add(resultSet.getString("description"));
        }
        catch (Exception e)
        {
            System.out.println("Erare getProduse");
        }
        return s;
    }
    public int getTipProdusID(String s)
    {
        int id=0;
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tipProduse where description ='"+s+"';");
            id= resultSet.getInt("tipID");
        }
        catch (Exception e)
        {
            System.out.println("Erare TipProdusID");
        }
        return id;
    }
    public String getProductData(String name)
    {
        String s="               ";
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT stoc, pretDeBaza, PretFinal FROM produs where nume ='"+name+"';");
            s=s+Integer.toString(resultSet.getInt(1));
            s=s+"               ";
            s=s+Double.toString(resultSet.getDouble(2));
            s=s+"               ";
            s=s+Double.toString(resultSet.getDouble(3));

        }
        catch (Exception e)
        {
            System.out.println("Eroare GetProductData");
        }
        return s;
    }
    public double getAdaosTva(String s){
        double sum=0;
        ResultSet resultSet;
        try {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tipProduse where description ='"+s+"';");
            sum+= resultSet.getDouble("adaosComercialPct");
            sum+= resultSet.getDouble("TVA");
        }
        catch (Exception e)
        {
            System.out.println("Eroare getAdaosTva");
        }
        return sum;
    }
    public void insertIntoCosCump(int produsID,int nrBucati,double pretTotal)
    {
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM cosCump where produsID ='" + Integer.toString(produsID) + "';");
            if (resultSet != null && resultSet.next())
            {
                String arg2=Integer.toString(nrBucati)+",";
                String arg3=Double.toString(pretTotal);
                statement.executeUpdate("Update cosCUmp set nrBucati="+arg2+"pretTotal="+arg3+" where produsID ='" + Integer.toString(produsID) + "';");
            }
            else
            {
                String arg1=Integer.toString(produsID)+",";
                String arg2=Integer.toString(nrBucati)+",";
                String arg3=Double.toString(pretTotal);
                statement.executeUpdate("insert into cosCump Values("+arg1+arg2+arg3+");");
            }
        }
        catch (Exception E) {}
    }
    public void deleteFromCOsCump()
    {
        try {
            Statement statement = c.createStatement();
            statement.executeUpdate("Delete from cosCump");
        }
        catch (Exception E) {}
    }

    public int getNrProdFromCosCump()
    {
        int nrProd=0;
        try {
            Statement statement = c.createStatement();
            ResultSet resultSet=statement.executeQuery("Select Sum(nrBucati) from cosCump;");
            nrProd=resultSet.getInt(1);
        }
        catch (Exception E) {}

        return nrProd;
    }
    public double getPTotalFromCosCump()
    {
        double Ptotal=0;
        try {
            Statement statement = c.createStatement();
            ResultSet resultSet=statement.executeQuery("Select Sum(pretTotal) from cosCump;");
            Ptotal=resultSet.getDouble(1);
        }
        catch (Exception E) {}

        return Ptotal;
    }

    public void insertIntoVanzare(int nrprod,double pret)
    {
        try {
            String arg1=Integer.toString(nrprod)+",";
            String arg2=Double.toString(pret);
            Statement statement = c.createStatement();

            statement.executeUpdate("insert into vanzare(nrProduse, valoareTotala) values("+arg1+arg2+");");
            ResultSet resultSet= statement.executeQuery("select produsID, nrBucati from cosCump;");
            while (resultSet.next())
            {
                int id=resultSet.getInt(1);
                int stoc=resultSet.getInt(2);
                ResultSet resultSet1= statement.executeQuery("select stoc from produs where produsID="+Integer.toString(id)+";");
                int stocCurent=resultSet1.getInt(1);
                dbm.UpdateStoc(id,stocCurent-stoc);
            }
            c.commit();
        }
        catch (Exception e){
            try {
                c.rollback();
            }
            catch (Exception E){}
        }
    }
    public void insertOrUpdateTipProduse (String description, double adaosComercialPct, double TVA)
    {
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tipProduse where description ='" + description + "';");

            String arg1 = Double.toString(adaosComercialPct);
            String arg2 = Double.toString(TVA);

            if (resultSet != null && resultSet.next())
            {
                statement.executeUpdate("UPDATE tipProduse set adaosComercialPct = " + arg1 + ", TVA = " + arg2 + " where description ='" + description + "';");
                System.out.println("Actualizat");
            }
            else
            {
                statement.executeUpdate("INSERT INTO tipProduse(description, adaosComercialPct, TVA) VALUES('"+description+"', " + arg1 + ", " + arg2 + ");");
                System.out.println("Adaugat");
            }
        }
        catch (Exception E) { }
    }
    public void insertOrUpdateProdus (int tipProdus, int stoc, double pretDeBaza, String nume)
    {
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();

            String arg1 = Double.toString(tipProdus);
            String arg2 = Double.toString(stoc);
            String arg3 = Double.toString(pretDeBaza);

            statement.executeUpdate("DELETE FROM produs WHERE nume = '" + nume + "';");
            statement.executeUpdate("INSERT INTO produs(tipProdus, stoc, pretDeBaza, nume) VALUES(" + arg1 + ", " + arg2 + ", " + arg3 + ", '"+nume+"');");
            System.out.println("Produs adaugat: "+nume+"");
        }
        catch (Exception E) { }
    }
    public void UpdateStoc(int Id,int stoc)
    {
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();
            String arg1=Integer.toString(Id)+";";
            String arg2=Integer.toString(stoc);
            statement.executeUpdate("Update produs set stoc="+arg2+" where produsId="+arg1);
        }
        catch (Exception E) {}
    }
    public void UpdatePret(int Id,int Pret)
    {
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();
            String arg1=Integer.toString(Id)+";";
            String arg2=Integer.toString(Pret)+",";
            double r=statement.executeQuery("Select pretFinal/pretDeBaza from produs where produsId="+arg1).getDouble(1);
            double Pfinal=r*Pret;
            String arg3=" pretFinal="+Double.toString(Pfinal) ;
            statement.executeUpdate("Update produs set pretDeBaza="+arg2+arg3+" where produsId="+arg1);
        }
        catch (Exception E) {}
    }
    public void DeleteProdus(String nume)
    {
        try{
            Statement statement = c.createStatement();
            statement.executeUpdate("DELETE FROM produs WHERE nume = '" + nume + "';");
        }
        catch (Exception e){}
    }
    List<vanzare> getVAnzari()
    {
        List<vanzare> l=new ArrayList<vanzare>();
        try {
            ResultSet resultSet;
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery("Select * from vanzare");

            while (resultSet.next())
            {
                double p= resultSet.getDouble("valoareTotala");
                int nr= resultSet.getInt("nrProduse");
                l.add(new vanzare(nr,p));
            }
        }
        catch (Exception e){}


        return l;
    }
}
