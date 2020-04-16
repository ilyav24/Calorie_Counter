import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class SecondPageView is in charge of all operations on second page
 * including layout, buttons, listeners and functions
 */
public class SecondPageView implements ISecondPageView {

//** Declaration of variables and Logger ***************************//
    private int currentSum;
    private JFrame frame;
    private JPanel panel;
    private JButton btn, backBtn, showDaily,delete;
    private JLabel foodname, calamount;
    private JTextField insertFood, insertCal,showDailyCal;
    private IViewModel viewmodel;
    private TextPanel textPanel;
    private String currentMeal;
    private ComboBoxDates listOfDays;
    private static Logger logger=Logger.getLogger("SecondPageView");
    Border loweredbevel;

//** Initialization of variables ***********************************//
    public SecondPageView() {
        currentSum=0;
        logger.info("before creating second jframe");
        frame = new JFrame("Edit Meals");
        logger.info("after creating second jframe");
        panel = new JPanel();
        btn = new JButton("Add");
        backBtn = new JButton("Back");
        foodname = new JLabel("Enter food name:");
        calamount = new JLabel("Enter calorie amount:");
        insertFood = new JTextField(10);
        insertCal = new JTextField(10);
        textPanel = new TextPanel();
        showDaily = new JButton("Show the whole table");
        listOfDays = new ComboBoxDates();
        delete =new JButton("Delete selected value from table");
        showDailyCal=new JTextField(25);
        loweredbevel = BorderFactory.createLoweredBevelBorder();
    }


    public void showFrame() {
        frame.setVisible(true);
    }

    /**
     * get dimensions to decide the gridbag layout constraints
     * and the size of each column and row setting layout as gridbag layout
     * proceed to add all elements to the gridbag layout using gridX and gridY
     * then setting size of window, setting close app on X click
     * finally making window visible
     */
    @Override
    public void start() {



        Dimension dim = frame.getPreferredSize();
        dim.width = 400;
        frame.setPreferredSize(dim);
        frame.add(panel);


            Border innerBorder=BorderFactory.createTitledBorder("Choose day");
            Border outerBorder= BorderFactory.createEmptyBorder(1,1,1,1);
            panel.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));


//** GridBagLayout components *************************************//
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        //gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        panel.add(foodname, gc);

        gc.gridx = 1; //+1
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(insertFood, gc);

        gc.gridx = 0; //-1
        gc.gridy = 1; //+1
        gc.anchor = GridBagConstraints.LINE_END;
        panel.add(calamount, gc);

        gc.gridx = 1; //+1
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(insertCal, gc);

        gc.gridx = 1;
        gc.gridy = 2; //+1
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(btn, gc);

        gc.gridx = 0; //-2
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        panel.add(backBtn, gc);

        gc.gridx = 0;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridheight=2;
        gc.gridwidth=4;
        gc.fill=GridBagConstraints.HORIZONTAL;
        panel.add(textPanel, gc);

        gc.gridx = 3;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridheight=1;
        gc.gridwidth=1;
        gc.fill = GridBagConstraints.NONE;
        panel.add(showDaily, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(showDailyCal, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_END;
        panel.add(delete, gc);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(listOfDays, gc);
        viewmodel.getDates();

        frame.setSize(800, 600);
        centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // ** Button action listeners *************************************//

        btn.addActionListener(new ActionListener() {
            /**
             * @param e button used to add an item to the database. get calories from
             * textfield and uses parseInt to turn string into integer. catches if it's
             *          what's inserted is not a number. gets food from insertFood
             *          textfield. sends the above mentioned two, and the current meal
             *          ("BreakFast", "Lunch", ...) to ViewModel to send to model
             *          and add to the database.
             *
             *          Also resets the textfields so they can be wrote in again and updates daily calorie textfield
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("button action called");
                String s =null;
                if(listOfDays.getListOfDays().getItemCount()!=0) {
                    s= listOfDays.getListOfDays().getSelectedItem().toString();
                    String lastEightDigits = s.substring(s.length() - 8);
                    if (lastEightDigits.equals(" - Today"))
                        s = s.substring(0, s.length() - 8);
                }
                int num = 0;
                String text;
                try {
                    num = Integer.parseInt(insertCal.getText());
                } catch (NumberFormatException ex) {
                    logger.info("Not a number");
                }
                text = insertFood.getText();
                logger.info("string is: " + text);
                logger.info("calorie number: " + num);
                logger.info("current meal is: " + currentMeal);
                try {
                    viewmodel.addItem("food", text, num, currentMeal,s);
                    insertCal.setText("");
                    insertFood.setText("");
                    int[] n={0};
                    n[0]=currentSum+num;
                    setTextField(n);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            /**
             * @param actionEvent calls viewmodel to navigate to first with textfield
             *                    parameters so they can be empty when returning back to
             *                    second page
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewmodel.navigateToFirst(insertCal, insertFood);


            }
        });

        showDaily.addActionListener(new ActionListener() {
            /**
             * @param e gets all items from table to JTable at button click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Calling viewmodel.GetItems");
                viewmodel.getItems();
            }
        });

        listOfDays.getListOfDays().addActionListener(new ActionListener() {
            /**
             * @param e I wanted to add " - Today" to today's date in the combo box
             *          to search for today's date in the database, i had to cut the
             *          " - Today" part which meant that I searched for 8 last characters
             *          of the string and check if they are " - Today"
             *          if they are, i cut them out and sent the string with the pure date
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if it's today's date, because it has " - Today" concated
                String s=listOfDays.getListOfDays().getSelectedItem().toString();
                String lastEightDigits = s.substring(s.length() - 8);
                if(lastEightDigits.equals(" - Today")) {
                    s=s.substring(0, s.length() - 8);
                    logger.info(s);
                }
                viewmodel.getSpecificDates(s);

            }
        });

        delete.addActionListener(new ActionListener() {
            /**
             * @param e listen to delete button and gets row and id value at row
             *          proceeds to delete row and sends id to ViewModel to it can be deleted
             *          from database
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = textPanel.getTable().getSelectedRow();
                String id = textPanel.getTable().getValueAt(row, 0).toString();
                int num = 0;
                try {
                    num = Integer.parseInt(textPanel.getTable().getValueAt(row, 2).toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                textPanel.deleteRow(row);
                try {
                    viewmodel.deleteItem(id);
                    int[] n = {0};
                    n[0] = currentSum - num;
                    setTextField(n);
                } catch (CalorieException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }


    /**
     * @param defaultmodel gets arraylist of FoodItem objects and
     * @throws CalorieException transfers them to object of type TextPanel to
     * they will be populated upon table
     */
    public void showItems(ArrayList<FoodItem> defaultmodel) throws CalorieException {
        textPanel.CreateTable(defaultmodel);
        logger.info("table added to scrollpane");
    }

    /**
     * @param sum sets the daily textfield field with daily calorie
     *            amount recieved from ViewModel
     */
    public void setTextField(int[] sum){
        currentSum=sum[0];
        String str = " ";
        for(int item : sum) {
            str +=item;
        }
        showDailyCal.setText("Chosen date's added calorie amount:"+str+"!");
    }

    /**
     * @param dates used to populate Jcombobox(drop down menu)
     *              passes on arraylist of dates ComboBoxDates type object
     */
    public void populateCombo(ArrayList<String> dates) {
        listOfDays.populateBox(dates);
        logger.info("Combo Box Populated with dates");
    }

    /**
     * @param viewmodel setter for ViewModel used from main class
     */
    @Override
    public void setViewmodel(IViewModel viewmodel) {
        this.viewmodel = viewmodel;
    }


    /**
     * @return getter for the current frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * @param meal setter
     */
    public void setCurrentMeal(String meal) {
        currentMeal = meal;
    }

    /**
     * @param frame method used for making the window appear in the middle of the screen
     */
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * @param showDailyCal setter for JTextField used for testing
     */
    public void setShowDailyCal(JTextField showDailyCal) {
        this.showDailyCal = showDailyCal;
    }
}
