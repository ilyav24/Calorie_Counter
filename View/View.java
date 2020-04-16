import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.logging.Logger;

/**
 * View class in charge of handling
 * everything that happens on the first page of the app
 */
public class View extends JFrame implements IView  {
//** Declaration of variables *******************************//
    private JTextField textField;
    private JFrame frame;
    private Container container;
    private IViewModel viewmodel;
    private JPanel panel;
    private JButton refresh;
    private JLabel label;
    private static Logger logger=Logger.getLogger("View");
    private Border loweredbevel;

//** initialization of variables ****************************//
    public View() {
        logger.info("before creating jframe");
        frame = new JFrame("Meal of the Day");
        logger.info("after creating jframe");
        textField = new JTextField(20);
        panel=new JPanel();
        refresh=new JButton("refresh");
        label=new JLabel("Your all-time calorie count so far:");
        loweredbevel = BorderFactory.createLineBorder(Color.black);
    }

    /**
     * makes textfield uneditable, sets container, sets layout, adds buttons,
     * adds buttom of the window, sets window size, centres window,
     * sets so app closes on X click, makes frame visible
     */
//** start up of JFrame ************************************//
    public void start(){
        textField.setEditable(false);
        container=frame.getContentPane();
        frame.setLayout(new GridLayout(7, 1));
        addAButton("Breakfast", container);
        addAButton("Lunch", container);
        addAButton("Before Workout", container);
        addAButton("After Workout", container);
        addAButton("Dinner", container);
        addAButton("Other", container);

        panel.add(label);
        panel.add(textField);
        panel.add(refresh);
        panel.setBorder(loweredbevel);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(panel);

        frame.setSize(500,500);
        centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showFrame();


        refresh.addActionListener(e -> viewmodel.getSum("all"));
    }

    /**
     * used when switching between windows
     */
    public void showFrame(){
        frame.setVisible(true);
    }

    /**
     * get sum value from viewmodel and print when button is pressed
     * adds value to string then attaches to textfield
     * @throws CalorieException
     */
    @Override
    public void showItems(int []items) throws CalorieException {
        String str = " ";
        for(int item : items) {
            str += " ... " + item +" !";
        }
        textField.setText(str);
    }

    /**
     * @param ob setter for viewmodel
     */
    public void setViewModel(IViewModel ob) {
        viewmodel = ob;
    }

    /**
     * @return returns current frame
     */
    public JFrame getFrame(){
        return frame;
    }

    /**
     * used to add another button to a container with chosen text and an
     * action listener attached
     */
    private void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        button.addActionListener(e -> viewmodel.navigateToSecond(e.getActionCommand()));
    }

    /**
     * method used to make window appear in the middle of the screen
     */
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
