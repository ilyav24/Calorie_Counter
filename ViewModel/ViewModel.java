import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * ViewModel, controller type class. communicates between view and model
 */
public class ViewModel implements IViewModel {
    //** variables **********************************************/
    private IModel model;
    private IView view;
    private ISecondPageView second;
    private boolean wasStartCalled = false;
    private static Logger logger = Logger.getLogger("ViewModel");

    //** setters for ViewModel ***********************************/

    public void setView(IView view) {
        this.view = view;
    }

    public void setSecondPageView(ISecondPageView second) {
        this.second = second;
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * @param actionEvent uses action event from the button pressed from first page
     *                    to identify which meal was clicked ("Breakfast","Lunch", ...)
     *                    uses boolean "wasStartCalled" to know whether second pages start()
     *                    funtion was called already so it wont duplicate the page by calling
     *                    it again
     *                    if it was close then second page just calls showFrame method
     *                    which makes JFrame visible
     */
    public void navigateToSecond(String actionEvent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
                logger.info(actionEvent);
                second.setCurrentMeal(actionEvent);
                if (wasStartCalled == false) {
                    second.start();
                    wasStartCalled = true;
                    logger.info("Second start() was called");
                } else {
                    second.showFrame();
                }

                view.getFrame().dispose();
            }
        });
    }

    /**
     * @param t  used to set text fields to null
     * @param t2 when navigating to first page
     *           makes second jframe invisible and first page jframe visible again
     */
    @Override
    public void navigateToFirst(JTextField t, JTextField t2) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
                t.setText("");
                t2.setText("");
                second.getFrame().setVisible(false);
                view.showFrame();

            }
        });
    }

    /**
     * get all items from model to then populate the table
     * when "show all table contents" is pressed
     */
    @Override
    public void getItems() {
        logger.info("Creating new thread to access database");
        new Thread(new Runnable() {

            @Override
            public void run() {

                logger.info("model.getItems was called");
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            ArrayList defaultmodel = model.getItems();
                            second.showItems(defaultmodel);
                            logger.info("second.showItems was called");
                            defaultmodel = null;
                        } catch (SQLException e) {
                            logger.info("getItems not passed on to model");
                        }


                    }
                });

            }
        }).start();
    }

    /**
     * requests all existing food added dates from model
     * then send to SecondPageView to populate the combo box
     */
    @Override
    public void getDates() {
        logger.info("Creating new thread to access database");
        new Thread(new Runnable() {

            @Override
            public void run() {


                logger.info("model.getItems was called");
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            ArrayList<String> arrayList = model.getDates();
                            second.populateCombo(arrayList);
                            logger.info("second.showItems was called");
                            arrayList = null;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * @param date brings date to send to model.
     *             model then return arraylist full of fooditems for that specific date
     *             which then show up on the daily calorie sum textfield and table
     */
    @Override
    public void getSpecificDates(String date) {
        logger.info("Creating new thread to access database");
        new Thread(new Runnable() {
            int[] sum = {0};

            @Override
            public void run() {

                logger.info("model.getItems was called");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<FoodItem> arrayList = model.getSpecificDate(date);
                            sum[0] = model.getCalories(date);
                            second.setTextField(sum);
                            second.showItems(arrayList);
                            arrayList = null;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        logger.info("second.showItems was called");
                    }
                });
            }
        }).start();
    }

    /**
     * @param date send date of which to collect the sum of. can be "all" for all dates
     */
    public void getSum(String date) {
        logger.info("Creating new thread to access database");
        int[] sum = {0};
        new Thread(new Runnable() {
            @Override
            public void run() {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        logger.info("fetching sum from model");
                        try {
                            sum[0] = model.getCalories(date);

                            logger.info("sum fetched");
                            view.showItems(sum);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }).start();
        logger.info("returning sum");
    }

    /**
     * @param table       send table ot model
     * @param foodname    send foodname ot model
     * @param calamount   send calorie amount ot model
     * @param currentMeal send the curent meal to model
     *                    proceeds to send current date parameter to getSpecific date
     *                    gives the list value to the arraylist
     *                    in order to send the arraylist to the second page and display the newly added item
     *                    then proceeds to repopulate the JComboBox if today is a day that was added
     */
    @Override
    public void addItem(String table, String foodname, int calamount, String currentMeal, String date) {

        logger.info("addItem called... starting new thread");
        new Thread(new Runnable() {
            ArrayList<FoodItem> arrayList = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<String>();

            @Override
            public void run() {


                try {
                    model.addItem("food", foodname, calamount, currentMeal, date);
                    arrayList = model.getSpecificDate(date);
                    dates = model.getDates();
                    second.showItems(arrayList);
                    second.populateCombo(dates);
                    arrayList = null;
                    dates = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    /**
     * @param id calls model class to delete item from database
     *           then repopulated the combo box in case no items from specific date remain
     */
    @Override
    public void deleteItem(String id) {
        logger.info("Creating new thread to access database");
        new Thread(new Runnable() {
            ArrayList<String> dates = new ArrayList<String>();

            @Override
            public void run() {


                try {
                    model.deleteItem(id);

                    dates = model.getDates();
                    second.populateCombo(dates);
                    dates = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void dropTable() throws SQLException, ClassNotFoundException {
        IModel model=new Model();
        model.dropTable();
        setModel(model);
    }
}
