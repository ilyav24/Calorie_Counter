import java.util.logging.Logger;

/**
 * Food Item is an object used to populate ArrayLists returned from Model class to populate the JTable
 * from Model, it is sent to ViewModel, then View, then to TextPanel to be deconstructed
 * by it's id, name, calamount, currentMeal, date variables and displayed on the JTable
 */
public class FoodItem {
 //** Decalrations of variables and Logger ************************//
    private int id;
    private String name;
    private int calamount;
    private String currentMeal;
    private String date;
    private static Logger logger=Logger.getLogger("FoodItem");

    /**
     * @param id unique id
     * @param name food name
     * @param calamount amount of calories in added food
     * @param currentMeal Button which was clicked on first page of app
     * @param date The date the food item was added. allows getting groups of food items by days
     */
//** Constructor *****************************************//
    public FoodItem(int id, String name, int calamount, String currentMeal, String date){
        this.id=id;
        this.calamount=calamount;
        this.name=name;
        this.currentMeal=currentMeal;
        this.date=date;
    }

//** Getters for class *********************************//
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalamount() {
        return calamount;
    }

    public String getCurrentMeal(){
        return currentMeal;
    }

    public String getDate() {
        return date;
    }
}
