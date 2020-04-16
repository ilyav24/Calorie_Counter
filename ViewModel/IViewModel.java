import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * interface for ViewModel
 */
public interface IViewModel {
    /**
     * @throws SQLException Drops Table "food" in model
     */
    public void dropTable() throws SQLException, ClassNotFoundException;
    /**
     * gets all items from table
     */
    public void getItems();

    /**
     * @param date gets sum of items from given date
     */
    public void getSum(String date);

    /**
     * @param table table name
     * @param foodname food name
     * @param calamount calorie amount
     * @param currentMeal chosen meal of the day
     * @param date chosen date
     * @throws CalorieException
     *
     * adds item into table
     */
    public void addItem(String table,String foodname, int calamount,String currentMeal,String date) throws CalorieException;

    /**
     * @param id deletes item by it's id
     * @throws CalorieException
     */
    public void deleteItem(String id) throws CalorieException;

    /**
     * @param m setter for model
     */
    public void setModel(IModel m);

    /**
     * @param v setter for view
     */
    public void setView(IView v);

    /**
     * @param s setter for second page
     */
    public void setSecondPageView(ISecondPageView s);

    /**
     * @param actionEvent Navigate to second
     */
    public void navigateToSecond(String actionEvent);

    /**
     * @param t Navigate to first
     * @param t2
     */
    public void navigateToFirst(JTextField t,JTextField t2);

    /**
     * populates combo box
     */
    public void getDates();

    /**
     * @param date returns list of items from chosen date
     */
    public void getSpecificDates(String date);
}
