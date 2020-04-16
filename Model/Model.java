import org.apache.derby.database.Database;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model class manages creation, connections, and queries to database. implements IModel
 */
public class Model implements IModel {

    /**
     * Logger designed to display log messages
     */
    private static Logger logger=Logger.getLogger("Model");
//** Define DRIVER and JDBC_URL *************************************************//
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL="jdbc:derby:ilyadb;create=true";

//** variables decalred and initialized to null **********************************//
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet rs=null;
    private FoodItem fooditem;
    private DefaultTableModel defaultmodel=null;
    private static DatabaseMetaData dmd;


    /**@throws ClassNotFoundException checks if Class.forName(DRIVER) class can be located by the class loader,
     *                                else throws exception.
     *                                if it is able to, DRIVER class is added to class loader
     * @throws CalorieException Class Constructor
     * checks if table already exists, if not it creates it with 5 columns
     *  and distinct id which is a primary key
     */
    Model() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        setConnection();
        logger.info("Checking if database already exists");
        if(firstTime()) {
            statement.execute("CREATE TABLE food(" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "food_name VARCHAR(25) NOT NULL, " +
                    "calorie_amount INTEGER, " +
                    "meal VARCHAR(20), "+
                    "date VARCHAR(20), "+
                    "CONSTRAINT primary_key PRIMARY KEY (id))");

            logger.info("Food table created");
        }
        shutDownConnection();
        //dropTable();

    }


    /**
     * @return This return a list of FoodItems with no specific date to be displayed in TextPanel on the JTable
     * after the "Show the whole table" button is clicked
     * @throws SQLException
     */
    @Override
    public ArrayList<FoodItem> getItems() throws SQLException {
        ArrayList<FoodItem> list =new ArrayList<FoodItem>();
        setConnection();
        rs = statement.executeQuery(
                "SELECT * FROM food ORDER BY date");
        while(rs.next())
        {
            int d = rs.getInt("id");
            String b = rs.getString("food_name");
            int f = rs.getInt("calorie_amount");
            String x=rs.getString("meal");
            String m=rs.getString("date");
            fooditem=new FoodItem( d,  b, f,x,m);
            list.add(fooditem);
        }

        shutDownConnection();

        return list;

    }

    /**
     * @param chosenDate this date signifies the food items that will be passed on to ViewModel, and the SecondPageView
     *                   and be displayed on the table
     *                   There is an ArrayList of foodItem
     *                   Resultset rs displays each row from the query and saves each row to a FoodItem object
     *                   Then it adds the food item object ot the list and passes the list on to
     *                   deconstructed in TextPanel and displayed on the JTable when chosen by a specific date
     *                   with the JComBoBox
     * @return
     * @throws SQLException
     */
    public ArrayList<FoodItem> getSpecificDate(String chosenDate) throws SQLException{
        setConnection();
        ArrayList<FoodItem> list =new ArrayList<FoodItem>();
        logger.info("entering getSpecificDate function");
        if(chosenDate==null)
            rs = statement.executeQuery(
                    "SELECT * FROM food ORDER BY date");
        else
            rs = statement.executeQuery(
                    "SELECT * FROM food WHERE date in('"+chosenDate+"') ORDER BY id ");
        while(rs.next())
        {
            int d = rs.getInt("id");
            String b = rs.getString("food_name");
            int f = rs.getInt("calorie_amount");
            String x=rs.getString("meal");
            String m=rs.getString("date");
            fooditem=new FoodItem( d,  b, f,x,m);
            list.add(fooditem);
        }
        shutDownConnection();

        return list;
    }

    /**
     * @param chosenDate if the chosenDate is "all" it return calorie sum for food of ALL dates
     * @return          if chosenDate is an actual date, getCalories return the calorie sum
     *                  for that specific date/day
     * @throws SQLException
     */
    public int getCalories(String chosenDate) throws SQLException{
        setConnection();
        if(chosenDate.isEmpty())
            chosenDate= LocalDate.now().toString();
        logger.info("entering getCalories function");
        int sum=0;
        if(chosenDate.equals("all"))
            rs = statement.executeQuery(
                    "SELECT SUM(calorie_amount) from food");
        else
            rs = statement.executeQuery(
                    "SELECT SUM(calorie_amount) from food WHERE date IN('"+chosenDate+"')");
        while(rs.next())
            sum+=rs.getInt(1);

        shutDownConnection();

        return sum;
    }

    /**
     * @return returns all the food dates in order to populate the JComboBox(Drop Down Menu)
     * @throws SQLException
     */
    public ArrayList<String> getDates() throws SQLException{
        setConnection();
        logger.info("entering getCalories function");
        ArrayList<String> dates=new ArrayList<String>();
        rs = statement.executeQuery(
                "SELECT DISTINCT date FROM food ORDER BY date");
        while(rs.next())
            dates.add(rs.getString(1));

        shutDownConnection();

        return dates;
    }


    /**
     * @param table adds to this table
     * @param foodname adds this name
     * @param calamount adds this many calories
     * @param currentMeal adds this meal of the day to database
     * @throws CalorieException
     * @throws SQLException
     */
    @Override
    public void addItem(String table,String foodname,int calamount,String currentMeal,String chosenDate) throws SQLException {
        if(chosenDate==null)
            chosenDate= LocalDate.now().toString();

        setConnection();

        logger.info("model add Item called... adding item " );
        logger.info("INSERT INTO "+ table +"(food_name,calorie_amount,meal,date) VALUES" +
                "('"+ foodname + "', " + calamount + ", '"+ currentMeal + "', CURRENT_DATE)" );



        statement.execute("INSERT INTO "+ table +"(food_name,calorie_amount,meal,date) VALUES" +
                "('"+ foodname + "', " + calamount + ", '"+ currentMeal + "', '"+chosenDate+"' )" );



        logger.info("Value added to food table");

        shutDownConnection();

    }

    /**
     * @param id Delete item by his unique id
     * @throws CalorieException
     * @throws SQLException
     */
    @Override
    public void deleteItem(String id) throws SQLException {
        try{
            Integer.parseInt(id);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        setConnection();
        statement.executeUpdate(
                "DELETE FROM food WHERE id="+id);
        shutDownConnection();
    }

    /**
     * @return getMetaData is an interface that provides comprehensive information about the database
     * here I use it to find out whether I already created the table in order for there not to be
     * an error or exception in the next time I start up the app.
     */
    //** Check if the database is already created *********************************//
    public Boolean firstTime()
    {
        try
        {
            dmd = connection.getMetaData();
            rs = dmd.getTables(null, "APP", "FOOD", null);
            return !rs.next();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * @throws SQLException shuts down connection to database
     */
 //shutting down connection and statement, has to be done after every connection to ensure no id increment bug
    public void shutDownConnection() throws SQLException {
        if(statement!=null) try{statement.close();}catch(SQLException e){throw new CalorieException("Statement wasnt shut down",e);}
        if(connection!=null) try{DriverManager.getConnection("jdbc:derby:ilyadb;shutdown=true");}catch(SQLException e){}
        if(rs!=null) try{rs.close();}catch(CalorieException e){}
    }


    /**
     * @throws SQLException Creates connection to database
     */
 //creating connection and statement
    public void setConnection() throws SQLException {
        connection= DriverManager.getConnection(JDBC_URL);
        statement=connection.createStatement();
    }

    /**
     * @throws SQLException
     * drops the table
     */
    public void dropTable() throws SQLException {
        setConnection();
        statement.execute("DROP TABLE food");
        shutDownConnection();
    }
}
