import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * ComboBoxDates handles the combo box which contains the dates of all added food from the database
 */
public class ComboBoxDates extends JPanel {
//** Declaration of variables, Logger ********************************//
    private JComboBox listOfDays;
    private DefaultComboBoxModel model;
    private static Logger logger=Logger.getLogger("ComboBoxDates");

//** initialization of variables ************************************//
    public ComboBoxDates() {
        listOfDays = new JComboBox();
        model = new DefaultComboBoxModel();
        listOfDays.setModel(model);
        setLayout(new BorderLayout());
        add(listOfDays, BorderLayout.CENTER);
    }

    /**
     * @param list To make the Dates in combo box distinct,
     *             At the first part, I got rid of appended " - Today" segment of today's
     *             date if it exists. Then I checked if the string already contains in the
     *             combo box, if it does, I deleted from the list before I add all the dates.
     *             If it doesn't exist, it means I deleted all the items with that date,
     *             so it is deleted from the combo box.
     *
     *             After that I just populate the combo box from the list.
     *             if the date from the list matches today's date, I append the 8 characters
     *             " - Today" to the date before adding it into the combo box.
     *              regular dates I simply add to combo box.
     */
    public void populateBox(ArrayList<String> list) {
        String s = null;

        for(int i=0;i<listOfDays.getItemCount();i++) {
            s=listOfDays.getItemAt(i).toString();
            String lastEightDigits = s.substring(s.length() - 8);
            if(lastEightDigits.equals(" - Today")) {
                s=s.substring(0, s.length() - 8);
            }
            logger.info("s is"+s);
            if (list.contains(s))
                list.remove(s);
        }
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals(String.valueOf(LocalDate.now()))) {
                model.addElement(list.get(i).concat(" - Today"));
            } else
                model.addElement(list.get(i));



        }

        listOfDays.setModel(model);
    }

//** Getters and Setters *********************************************************//

    public JComboBox getListOfDays(){
        return listOfDays;
    }
    public DefaultComboBoxModel getModel(){
        return model;
    }

    public void setListOfDays(JComboBox listOfDays) {
        this.listOfDays = listOfDays;
    }

    public void setModel(DefaultComboBoxModel model) {
        this.model = model;
    }
}
