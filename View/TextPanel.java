import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * TextPanel class in charge of populating and managing the food table
 */
public class TextPanel extends JPanel {
    //** declaring variables **************************//
    private DefaultTableModel model;
    private JTable table;
    private static Logger logger=Logger.getLogger("TextPanel");

    /**
     * Constructor for TextPanel class
     * defined variables, then then model's columns to fit with given list
     * changes font to be bigger and bold
     * adds table to JScrollPane so lots of values will be scrollable
     */
    public TextPanel() {
        model = new DefaultTableModel();
        table = new JTable();
        Object[] columns = {"Id", "Food Name", "Calorie Amount", "Chosen Meal","Date"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        Font font = new Font("", 1, 16);
        table.setFont(font);
        table.setRowHeight(30);

        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * @param list obtains list of food items from database and proceeds to add every item
     *             to its own row using DefaultTableModel.addRow method
     */
    //** Creates Table at the press of a button *************************//
    public void CreateTable(ArrayList<FoodItem> list) {
        Object[] row = new Object[5];

        if (list == null || list.isEmpty()) {
            logger.warning("Database Empty");
        }
        else {
            model.setRowCount(0);
            for (FoodItem i : list) {
                row[0] = i.getId();
                row[1] = i.getName();
                row[2] = i.getCalamount();
                row[3]=i.getCurrentMeal();
                row[4]=i.getDate();
                model.addRow(row);
            }
        }
    }

    /**
     * @param row removes given row from table for quick deletion of row
     *            without needing to update all values from databse
     */
    public void deleteRow(int row){
        model.removeRow(row);
    }
//** getters and setters *****************************//
    public JTable getTable(){
        return table;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
