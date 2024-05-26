import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class SinglePropInfo implements Initializable {

  String custID;
  String custType;
  private String propID;
  void setCust(String id, String type) {
    custID = id;
    custType = type;
  }

    @FXML
    private Label propDesc;

    @FXML
    private Label propPrice;

    @FXML
    private Label propType;
    
    @FXML
    private Button buyBtn;

    @FXML
    private Button rentBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }

    void reValidation(String custType) throws IOException {
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("PropertyListings.fxml"));
      Parent root = loader.load();
      PropertyListingCont controller = loader.getController();
      controller.setCustType(custType);
    }

    void notiDialog(AlertType type, String title, String header ,String content) {
      Alert alert = new Alert(type);
      alert.setTitle(title);
      alert.setHeaderText(header);
      alert.setContentText(content);
      alert.setX(550);
      alert.setY(300);
      alert.showAndWait();
    }

    @FXML
    void buyProperty(ActionEvent event) {

      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Buy Property");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to buy this property?");
      alert.setX(550);
      alert.setY(300);
      alert.showAndWait();
      System.out.println("PropID " + propID);
      ButtonType result = alert.getResult();
      if (result == ButtonType.OK) {
        
        if (custType == null) {
          // query to update property Info for new customer
          String queryString = "UPDATE PROPERTY SET PROPERTYSTATUS = 'OWNED',  CUSTOMERID = '" + custID + "' WHERE PROPERTYID = '" + propID + "'";
          DBConnection connection = new DBConnection(queryString);
          try {
            connection.connect();
          } catch (Exception e) {
            e.printStackTrace();
          }
          
          // query to update customer Info
          String updateQuery = "UPDATE CUSTOMER SET CUSTOMERTYPE = 'OWNER' WHERE CUSTOMERID = '" + custID + "'";
          connection = new DBConnection(updateQuery);
          try {
            connection.connect();
            custType = "OWNER";
            reValidation("OWNER");
          } catch (Exception e) {
            e.printStackTrace();
          }

          // insertion into subtable
          String subtableInsertion = "INSERT INTO OWNER_T VALUES ('" + custID + "', 09007860123409007860)";
          connection = new DBConnection(subtableInsertion);

          Alert confiProp = new Alert(AlertType.CONFIRMATION);
          confiProp.setTitle("Property Information");
          confiProp.setContentText("Congratulations on buying the Property. Please Sign in again to view your updated list of Properties");
          try {
            connection.connect();
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else {
          // update property info for exisitng customer
          String queryString = "UPDATE PROPERTY SET PROPERTYSTATUS = 'OWNED',  CUSTOMERID = '" + custID + "' WHERE PROPERTYID = '" + propID + "'";
          DBConnection connection = new DBConnection(queryString);

          notiDialog(AlertType.CONFIRMATION, "Property Information", null, "Congratulations on buying the Property. Please Sign in again to view your updated list of Properties");
          try {
            connection.connect();
          } catch (Exception e) {
            e.printStackTrace();
            notiDialog(AlertType.ERROR, "Property Information", "Error Message", "There was an error. Please try again");
          }
        }
      }
    }

    @FXML
    void rentProperty(ActionEvent event) {
      
      if (custType != null) {
        if (custType.contains("TENANT")) {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("You cannot rent two properties at once.");
          alert.setX(550);
          alert.setY(300);
          alert.showAndWait();
          return;
        }
      }

      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Rent Property");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to Rent this property?");
      alert.setX(550);
      alert.setY(300);
      alert.showAndWait();
      System.out.println("PropID " + propID);
      ButtonType result = alert.getResult();
      if (result == ButtonType.OK) {

        // query to update property Info
          String queryString = "UPDATE PROPERTY SET PROPERTYSTATUS = 'ON_RENT',  CUSTOMERID = '" + custID + "' WHERE PROPERTYID = '" + propID + "'";
          DBConnection connection = new DBConnection(queryString);
          try {
            connection.connect();
          } catch (Exception e) {
            e.printStackTrace();
          }
        

        // query to update customer Info
        if (custType == null) {
          // if customer is new
        String updateQuery = "UPDATE CUSTOMER SET CUSTOMERTYPE = 'TENANT' WHERE CUSTOMERID = '" + custID + "'";
        DBConnection connection2 = new DBConnection(updateQuery);
        try {
          connection2.connect();
          custType = "TENANT";
          reValidation(custType);
        } catch (Exception e) {
          e.printStackTrace();
        }
        // subtable insertion
        String insertTenant = "INSERT INTO TENANT VALUES ('" + custID + "', 09007860)";
        DBConnection newTenant = new DBConnection(insertTenant);
        try {
          newTenant.connect();
          custType = "TENANT";
          reValidation(custType);
          notiDialog(AlertType.CONFIRMATION, "Property Information", null, "Congratulations on Renting the Property. Please Sign in again to view your updated list of Properties");
        } catch (Exception e) {
          e.printStackTrace();
        }

      } else {
        // if customer is already an owner
        String insertTenant = "INSERT INTO TENANT VALUES ('" + custID + "', 09007860)";
        DBConnection connection2 = new DBConnection(insertTenant);
        try {
          connection2.connect();
          custType = "OWNER";
          notiDialog(AlertType.CONFIRMATION, "Property Information", null, "Congratulations on Renting the Property. Please Sign in again to view your updated list of Properties");
          reValidation(custType);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

    public void setPropertyData(PropertyData pData) {
      propType.setText(pData.getPropertyType());
      propPrice.setText(pData.getPrice());
      propDesc.setText(pData.getDescription());
      propID = pData.getPropID();

      if (pData.getDescription().equals("FOR_RENT")) {
        buyBtn.setDisable(true);
      } else if (pData.getDescription().equals("FOR_SALE")) {
        rentBtn.setDisable(true);
      }
    }

}
