import oracle.jdbc.driver.OracleDriver;

import java.sql.*;

/**
 * Created by Ruslan on 3/10/2015.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Driver driver = new OracleDriver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:lab3@//192.168.56.101:1521/orcl", "lab3", "pass");


        String query = "INSERT INTO departments (name) VALUES (?)";

        PreparedStatement statement  = connection.prepareStatement(query);

        statement.setString(1, "123142");

        statement.executeUpdate();




        if (statement != null){
            statement.close();
        }

        if(connection != null) {
            connection.close();
        }

        connection.close();
    }
}
