import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CalorieCounter {

    private static Logger logger = Logger.getLogger("CalorieCounter");

    /**
     * @param args Main class declares pages and sets Objects to corresponding classes
     *             then launched first page with v.start()
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
                IModel m = null;
                try {
                    m = new Model();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                IView v = new View();
                ISecondPageView s = new SecondPageView();
                IViewModel vm = new ViewModel();
                logger.info("new view model created");
                // Set Objects to corresponding classes
                v.setViewModel(vm);
                s.setViewmodel(vm);
                vm.setModel(m);
                vm.setView(v);
                vm.setSecondPageView(s);
                v.start();
                logger.info("viewmodel, model, and both pages assigned");
            }
        });
    }
}
