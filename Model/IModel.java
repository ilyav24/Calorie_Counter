import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface implemented by Model
 */
public interface IModel {
    /**
     * @throws SQLException
     * Drops the table
     */
    public void dropTable() throws SQLException;
    /**
     * @param chosenDate
     * @return
     * @throws SQLException
     * gets sum of calories where date=chosenDate
     */
    public int getCalories(String chosenDate) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * gets list of all foodItem type from database regardless of date
     * that will populate the JTable
     */
    public ArrayList<FoodItem> getItems() throws SQLException;

    /**
     * @param table
     * @param foodname
     * @param calamount
     * @param currentMeal
     * @param chosenDate
     * @throws SQLException
     * adds item to database with above mentioned parameters
     */
    public void addItem(String table,String foodname,int calamount,String currentMeal,String chosenDate) throws SQLException;

    /**
     * @param id
     * @throws SQLException
     * delete item from database by id
     */
    public void deleteItem(String id) throws SQLException;

    /**
     * @return checks if app is running for the first time to see
     * if there is a need ot create a table
     */
    public Boolean firstTime();

    /**
     * @throws SQLException closes connection ot database
     */
    public void shutDownConnection() throws SQLException;

    /**
     * @throws SQLException
     * sets up connection to database(rs,statment,connection)
     */
    public void setConnection() throws SQLException;

    /**
     * @return
     * @throws SQLException
     * returns list of dates
     */
    public ArrayList<String> getDates() throws SQLException;

    /**
     * @param date
     * @return
     * @throws SQLException
     * returns items of specific date
     */
    public ArrayList<FoodItem> getSpecificDate(String date) throws SQLException;
}
