import javax.swing.*;
import java.util.ArrayList;

/**
 * interface the SecondPageView implements
 */
public interface ISecondPageView {
    /**
     * @param ob setter for viewmodel
     */
    public void setViewmodel(IViewModel ob);

    /**
     * initializes variables and sets up JFrame to be visible
     */
    public void start();

    /**
     * makes current JFrame visible
     */
    public void showFrame();

    /**
     * @param defaultmodel gets list of food items from viewmodel and passes on to TextPanel
     * @throws CalorieException
     */
    public void showItems(ArrayList<FoodItem> defaultmodel) throws CalorieException;

    /**
     * @return returns JFrame
     */
    public JFrame getFrame();

    /**
     * @param meal setter for button pressed from previous Jframe
     */
    public void setCurrentMeal(String meal);

    /**
     * @param dates populates combo box from arraylist
     */
    public void populateCombo(ArrayList<String> dates);

    /**
     * @param sum sets value in JTextField that is in charge of displaying the daily calories amount
     */
    public void setTextField(int[] sum);

    /**
     * @param showDailyCal setter for test
     */
    public void setShowDailyCal(JTextField showDailyCal);

}
