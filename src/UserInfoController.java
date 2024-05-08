import java.io.IOException;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserInfoController {
  private Scene scene;
  private Stage stage;
  private Parent root;

  String custID;
  String custType;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userIDLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label phoneNumber;

    public void setCustID (String custID, String custType) {
      this.custID = custID;
      this.custType = custType;

      String query = "SELECT CustomerName, Email, Phone FROM CUSTOMER WHERE CUSTOMERID = '" + custID + "'";
      DBConnection connection = new DBConnection(query);

      try {
        ResultSet results = connection.connect();
        while (results.next()) { 
          userIDLabel.setText(custID);
          userNameLabel.setText(results.getString(1));
          userEmailLabel.setText(results.getString(2));
          phoneNumber.setText(results.getString(3));
        }
      } catch (Exception e) {
      }
    }

    @FXML
    void logOut(ActionEvent event) {
    try {
        root = FXMLLoader.load(getClass().getResource("UserLoginPage.fxml"));
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

    }

}
