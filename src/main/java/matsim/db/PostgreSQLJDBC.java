package matsim.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostgreSQLJDBC {
    public static Map<String, ArrayList<BasePopulation>> GetDataFromDB_Population() throws SQLException {
        String url="jdbc:postgresql://39.107.177.223:5432/postgres";
        String user="postgres";
        String passowrd="admin";
        String sql = "select " +
                "bp.bp_id, bp.bp_name, bp.bp_layer_min,bp.bp_layer_max, bp.bp_people, bp.bp_far_min, bp.bp_far_max," +
                "bp.bp_density_max,bp.bp_green_min,bp.bp_height_max,bf.name, bf.relative_name " +
                "from " +
                "building_population bp, building_functions bf " +
                "where" +
                " bp.bp_func_id = bf.id;";

        var result=SelectFromDB_Population(url,user,passowrd,sql);
        return result;
    }

    //"jdbc:postgresql://39.107.177.223:5432/postgres",
    //"postgres", "admin"
    public static void OpenDB(String url, String user, String password) {
        Connection c=null;
        try{
            Class.forName("org.postgresql.Driver");
            c=DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void SelectData(String url, String user, String password, String sql){
        Connection c=null;
        Statement stmt=null;
        ResultSet rs=null;
        Map<String, ArrayList<BasePopulation>> basePopDic=new HashMap<>();
        try{
            Class.forName("org.postgresql.Driver");
            c=DriverManager.getConnection(url,user,password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt=c.createStatement();
            rs=stmt.executeQuery(sql);

            basePopDic=ParseData(rs);

            rs.close();
            stmt.close();
            c.close();
        }
        catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return basePopDic;
    }

    private static Map<String, ArrayList<BasePopulation>> ParseData(ResultSet rs, String[] attrList) throws SQLException {
        Map<String, ArrayList<BasePopulation>> basePopDic=new HashMap<>();
        if(rs!=null){
//            rs.last();
//            int count=rs.getRow();
//
//            rs.beforeFirst();
            while(rs.next()){
                String Id=rs.getString("bp_name");
                int LayerMin=rs.getInt("bp_layer_min");
                int LayerMax=rs.getInt("bp_layer_max");
                int People=rs.getInt("bp_people");
                double FARMin=rs.getDouble("bp_far_min");
                double FARMax=rs.getDouble("bp_far_max");
                double MaxDensity=rs.getDouble("bp_density_max");
                double MinGreen=rs.getDouble("bp_green_min");
                double MaxHeight=rs.getDouble("bp_height_max");
                String FuncName=rs.getString("name");
                String RelativeName=rs.getString("relative_name");
                BasePopulation basePopulation=new BasePopulation(
                        Id, LayerMin, LayerMax, People,FARMin,FARMax,
                        MaxDensity, MinGreen, MaxHeight, FuncName, RelativeName
                );

                if(basePopDic.containsKey(basePopulation.relativeName)){
                    basePopDic.get(basePopulation.relativeName).add(basePopulation);
                }else{
                    ArrayList<BasePopulation> tempList=new ArrayList<BasePopulation>();
                    tempList.add(basePopulation);
                    basePopDic.put(basePopulation.relativeName, tempList);
                }
            }
        }
        return basePopDic;
    }

    private static Map<String, ArrayList<BasePopulation>> SelectFromDB_Population(String url, String user, String password, String sql){
        Connection c=null;
        Statement stmt=null;
        ResultSet rs=null;
        Map<String, ArrayList<BasePopulation>> basePopDic=new HashMap<>();
        try{
            Class.forName("org.postgresql.Driver");
            c=DriverManager.getConnection(url,user,password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt=c.createStatement();
            rs=stmt.executeQuery(sql);

            basePopDic=ParseData_Population(rs);

            rs.close();
            stmt.close();
            c.close();
        }
        catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return basePopDic;
    }

    private static Map<String, ArrayList<BasePopulation>> ParseData_Population(ResultSet rs) throws SQLException {
        Map<String, ArrayList<BasePopulation>> basePopDic=new HashMap<>();
        if(rs!=null){
//            rs.last();
//            int count=rs.getRow();
//
//            rs.beforeFirst();
            while(rs.next()){
                String Id=rs.getString("bp_name");
                int LayerMin=rs.getInt("bp_layer_min");
                int LayerMax=rs.getInt("bp_layer_max");
                int People=rs.getInt("bp_people");
                double FARMin=rs.getDouble("bp_far_min");
                double FARMax=rs.getDouble("bp_far_max");
                double MaxDensity=rs.getDouble("bp_density_max");
                double MinGreen=rs.getDouble("bp_green_min");
                double MaxHeight=rs.getDouble("bp_height_max");
                String FuncName=rs.getString("name");
                String RelativeName=rs.getString("relative_name");
                BasePopulation basePopulation=new BasePopulation(
                        Id, LayerMin, LayerMax, People,FARMin,FARMax,
                        MaxDensity, MinGreen, MaxHeight, FuncName, RelativeName
                );

                if(basePopDic.containsKey(basePopulation.relativeName)){
                    basePopDic.get(basePopulation.relativeName).add(basePopulation);
                }else{
                    ArrayList<BasePopulation> tempList=new ArrayList<BasePopulation>();
                    tempList.add(basePopulation);
                    basePopDic.put(basePopulation.relativeName, tempList);
                }
            }
        }
        return basePopDic;
    }
}
