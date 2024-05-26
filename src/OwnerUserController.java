import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OwnerUserController implements Initializable {
    @FXML
    public Button propOwned;

    @FXML
    public Pane noPropOwned;
  
    @FXML
    private Label assestsNoLabel;

    @FXML
    private Label expensesLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label onRentLabel;

    @FXML
    private Label totalIncomeLabel;

  String custID;
  String custType;
  Integer income;
  Integer expenses;
  Integer totalIncome;

  public void setCustID(String id, String type) {
    custID = id;
    custType = type;

    if (custType == null || custType.contains("TENANT")) {

      incomeLabel.setText("0");
      assestsNoLabel.setText("0");
      expensesLabel.setText("0");
      onRentLabel.setText("0");
      totalIncomeLabel.setText("0");
      noPropOwned.setVisible(true);

    } else {
      // Query to get Income 

      String queryString = "SELECT AMOUNT FROM INCOME WHERE PROPERTYID = (SELECT PROPERTYID FROM PROPERTY WHERE CUSTOMERID = '" + custID + "')";      
      DBConnection connection = new DBConnection(queryString);
      try {
        ResultSet results = connection.connect();
        income = 0;
        while (results.next()) {
          income += results.getInt(1);
        }
        incomeLabel.setText(Integer.toString(income));
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Query to get Number of Properties Owned
      String query = "SELECT COUNT(PROPERTYID) FROM PROPERTY WHERE CUSTOMERID = '" + custID + "'";
      DBConnection connection2 = new DBConnection(query);
      try {
        ResultSet results = connection2.connect();
        while (results.next()) {
          assestsNoLabel.setText(results.getString(1));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      String expQuery = "SELECT AMOUNT FROM EXPENSE WHERE PROPERTYID = (SELECT PROPERTYID FROM PROPERTY WHERE CUSTOMERID = '" + custID + "')";
      DBConnection connection3 = new DBConnection(expQuery);
      try {
        ResultSet results = connection3.connect();
        expenses = 0;
        while (results.next()) {
          expenses += results.getInt(1);
        }
        expensesLabel.setText(Integer.toString(expenses));
      
      totalIncome = income - expenses;
      totalIncomeLabel.setText(Integer.toString(totalIncome));
      } catch (Exception e) {
        e.printStackTrace();
      }

      String rentQuery = "SELECT SUM(PROPERTYID) FROM PROPERTY WHERE CUSTOMERID = '" + custID + "' AND PROPERTYSTATUS = 'FOR_RENT'";
      DBConnection connection4 = new DBConnection(rentQuery);
      try {
        ResultSet results = connection4.connect();

        while (results.next()) {
          if (results.getString(1) == null) {
            onRentLabel.setText("0");
          } else {
            onRentLabel.setText(results.getString(1));
          }
          
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    } 
  }

  @Override
    public void initialize(URL location, ResourceBundle resources) {
      noPropOwned.setVisible(false);
    }

  private Scene scene;
  private Stage stage;
  private Parent root;

    @FXML
    void logOut(ActionEvent event) {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("UserLoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setX(300);
        stage.setY(120);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void switchToPropertiesOwned(ActionEvent event) {
      
    }

    @FXML
    void switchToPropertyList(ActionEvent event) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PropertyListings.fxml"));
        root = loader.load();

        PropertyListingCont controller = loader.getController();
        controller.setCust(custID, custType);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void switchToRentalInfo(ActionEvent event) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TenantUser.fxml"));
        root = loader.load();

        TenantUserController controller = loader.getController();
        controller.setCustomerID(custID, custType);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } 

    @FXML
    void switchToUserInfo(ActionEvent event) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInfo.fxml"));
        root = loader.load();

        UserInfoController controller = loader.getController();
        controller.setCustID(custID, custType);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
