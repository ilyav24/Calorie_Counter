import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    IModel model;
    Connection connection=null;
    Statement statement=null;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        model=new Model();
        connection= DriverManager.getConnection("jdbc:derby:ilyadb;create=true");
        statement=connection.createStatement();
    }

    @AfterEach
    void tearDown() throws SQLException {
        model.shutDownConnection();
        model=null;
    }

    @Test
    void getItems() throws SQLException {
        int count=0;
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(id) FROM food");
        while(rs.next())
            count=(rs.getInt(1));;
        assertEquals(count,model.getItems().size());
    }

    @Test
    void getSpecificDate() throws SQLException {
        int count=0;
        String chosenDate="2020-04-06";
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(id) FROM food WHERE date in('"+chosenDate+"') ");
        while(rs.next())
            count=(rs.getInt(1));;
        assertEquals(count,model.getSpecificDate(chosenDate).size());
    }

    @Test
    void getCalories() throws SQLException {
        int sum=0;
        String chosenDate="all";
        ResultSet rs = statement.executeQuery(
                "SELECT SUM(calorie_amount) from food");
        while(rs.next())
            sum+=(rs.getInt(1));;
        assertEquals(sum,model.getCalories(chosenDate));
    }

    @Test
    void getDates() throws SQLException {
        int count=0;
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(DISTINCT date) FROM food");
        while(rs.next())
            count=(rs.getInt(1));;
        assertEquals(count,model.getDates().size());
    }

    @Test
    void firstTime() {
        boolean isTableCreationNeeded=false;
        assertEquals(isTableCreationNeeded,model.firstTime());
    }
}