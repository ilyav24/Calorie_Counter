import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TextPanelTest {
    private DefaultTableModel model;
    private JTable table;
    private TextPanel textPanel;

    @BeforeEach
    void setUp() {
        textPanel=new TextPanel();
        model=new DefaultTableModel();
        table=new JTable();
        textPanel.setModel(model);
        textPanel.setTable(table);
    }

    @AfterEach
    void tearDown() {
        textPanel=null;
        model=null;
        table=null;
    }

    @Test
    void createTable() {
        ArrayList<FoodItem> arrayList=new ArrayList<>();
        FoodItem a=new FoodItem(1,"eggs",127,"Breakfast","2020-05-05");
        FoodItem b=new FoodItem(1,"eggs",127,"Breakfast","2020-05-05");
        FoodItem c=new FoodItem(1,"eggs",127,"Breakfast","2020-05-05");
        arrayList.add(a);
        arrayList.add(b);
        arrayList.add(c);
        textPanel.CreateTable(arrayList);
        assertEquals(arrayList.size(),model.getRowCount());
    }
}