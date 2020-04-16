import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ComboBoxDatesTest {

    private JComboBox listOfDays;
    private DefaultComboBoxModel model;
    private ComboBoxDates c;

    @BeforeEach
    void setUp() {
        c=new ComboBoxDates();
        listOfDays=new JComboBox();
        model=new DefaultComboBoxModel();
        c.setModel(model);
        c.setListOfDays(listOfDays);
    }

    @AfterEach
    void tearDown() {
        listOfDays=null;
        model=null;
        c=null;
    }

    @Test
    void populateBox() {
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("2020-04-05");
        arrayList.add("2020-04-06");
        arrayList.add("2020-04-07");
        c.populateBox(arrayList);
        assertEquals(arrayList.size(),listOfDays.getItemCount());
    }
}