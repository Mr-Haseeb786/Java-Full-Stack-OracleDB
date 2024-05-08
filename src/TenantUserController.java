import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oracle.sql.DATE;

public class TenantUserController implements Initializable{

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private TextField maintDesc;

    @FXML
    private Label propIDLabel;

    @FXML
    private Label rentLabel;

    @FXML
    private Label zipLabel;

    @FXML
    private Pane noPropRented;

    String custID;
    String custType;
    String propID;
    
  public void setCustomerID(String id, String type) {
    custID = id;
    custType = type;

    System.out.println( custType + " " + custID );

    if (custType != null) {

      if (custType.contains("TENANT")) {
        
        String queryString = "SELECT PropertyID, ZIPCode, Price FROM PROPERTY WHERE CUSTOMERID = '" + custID + "'";
        DBConnection connection = new DBConnection(queryString);
        
        try {
          ResultSet results = connection.connect();
          while (results.next()) {
            propID = results.getString(1);
            rentLabel.setText(results.getString(3));
            propIDLabel.setText(propID);
            zipLabel.setText(results.getString(2));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        noPropRented.setVisible(true);
      }

    } else {
      noPropRented.setVisible(true);
    }
  }

    @FXML
    void reqMaint(ActionEvent event) {

      String id = propID.substring(0, 3).toUpperCase() + Integer.toString((int)(Math.random() * 200030)) + custID.substring(0, 3).toUpperCase();

      LocalDate date = LocalDate.now();

      String query = "INSERT INTO MAINTENANCE_REQUESTS VALUES ('" + id + "' , TO_DATE('" + date + "', 'yyyy-mm-dd'), '" + maintDesc.getText() + "', 'PENDING', '" + propID  + "', '" + custID + "')";
      DBConnection connection = new DBConnection(query);
      try {
        connection.connect();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Maintenance Request");
        alert.setHeaderText(null);
        alert.setContentText("Request Submitted!");
        alert.setX(550);
        alert.setY(300);
        alert.showAndWait();

        maintDesc.setText("");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @FXML
    void logOut(ActionEvent event) {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("UserLoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(120);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void switchToPropertiesOwned(ActionEvent event) {

      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerUser.fxml"));
        root = loader.load();

        OwnerUserController controller = loader.getController();
        controller.setCustID(custID, custType);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      noPropRented.setVisible(false);
    }

}
