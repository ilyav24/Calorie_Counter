import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SecondPageViewTest {
    private ISecondPageView second;
    private JTextField textField;
    @BeforeEach
    void setUp() {
        second=new SecondPageView();
        textField=new JTextField();
        second.setShowDailyCal(textField);
    }

    @AfterEach
    void tearDown() {
        second=null;
    }

    @Test
    void TestsetTextField() {

        String str="";
        int[] num={2};
        str+=num;
        String expected="Chosen date's added calorie amount: "+2 +"!";
        second.setTextField(num);
        assertEquals(expected,textField.getText());
    }

    @Test
    void returnJFrame() {
        JFrame frame=new JFrame();
        JFrame frame1;
        frame1=second.getFrame();
        assertEquals(frame.getType(),frame1.getType());
    }
}