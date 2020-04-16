import javax.swing.*;

/**
 * IView is the interface that View implements
 */
public interface IView {
    public void showItems(int []items) throws CalorieException;

    /**
     * @param ob setter for viewmodel
     */
    public void setViewModel(IViewModel ob);

    /**
     * initializes frame components
     */
    public void start();

    /**
     * Makes JFrame visible
     */
    public void showFrame();

    /**
     * @return returns current view JFrame
     */
    public JFrame getFrame();

}
