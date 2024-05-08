import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {

  private String queryString;

  DBConnection(String queryString) {
    this.queryString = queryString;
  }


  public ResultSet connect() throws Exception {
        String url = "jdbc:oracle:thin:@localhost:1521/xe";
        String username = "system"; // Replace with your actual username
        String password = "Oracle_1"; // Replace with your actual password
        ResultSet resultSet = null;

        try {
            // Load the Oracle JDBC driver
            // Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a connection
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Execute a query
            resultSet = statement.executeQuery(queryString);

            // Process the ResultSet
            // while (resultSet.next()) {
            //   for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            //     System.out.print(resultSet.getString(i)+ " ");
            //   }
            // }       
        } catch (Exception e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        }
    return resultSet;
  }
}
