import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PropertyListingCont implements Initializable {

  private String custID;
  private String custType;

  private Scene scene;
  private Stage stage;
  private Parent root;

    @FXML
    private VBox propList;

    public void setCust(String id, String type) {
      custID = id;
      custType = type;

  
      List<PropertyData> list = new ArrayList<PropertyData>();
      
      String queryString = "SELECT * FROM PROPERTY WHERE PROPERTYSTATUS = 'FOR_SALE' OR PROPERTYSTATUS = 'FOR_RENT'";

      DBConnection connection = new DBConnection(queryString);
      PropertyData pd = new PropertyData();

      String propStatus = "";

    try {
      ResultSet results = connection.connect();
      while (results.next()) {
          pd = new PropertyData();
          pd.setPrice(results.getString(4));
          propStatus = results.getString(5);
          pd.setDescription(propStatus);
          pd.setPropertyType(results.getString(6));
          list.add(pd);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


      for (int i = 0; i < list.size(); i++) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("singlePropInfo.fxml"));

        try {
          HBox hbox = loader.load();
          SinglePropInfo singlePropInfo = loader.getController();
          singlePropInfo.setCust(custID, custType);
          System.out.println("Cust ID: " + custID);
          // setting the property data
          singlePropInfo.setPropertyData(list.get(i));
          // adding to the VBox
          propList.getChildren().add(hbox);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

      @Override
    public void initialize(URL location, ResourceBundle resources) {

    //   List<PropertyData> list = new ArrayList<PropertyData>();
      
    //   String queryString = "SELECT * FROM PROPERTY WHERE PROPERTYSTATUS = 'FOR_SALE' OR PROPERTYSTATUS = 'FOR_RENT'";

    //   DBConnection connection = new DBConnection(queryString);
    //   PropertyData pd = new PropertyData();

    //   String propStatus = "";

    // try {
    //   ResultSet results = connection.connect();
    //   while (results.next()) {
    //       pd = new PropertyData();
    //       pd.setPrice(results.getString(4));
    //       propStatus = results.getString(5);
    //       pd.setDescription(propStatus);
    //       pd.setPropertyType(results.getString(6));
    //       list.add(pd);
    //   }
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }


    //   for (int i = 0; i < list.size(); i++) {
    //     FXMLLoader loader = new FXMLLoader();

    //     loader.setLocation(getClass().getResource("singlePropInfo.fxml"));
    //     SinglePropInfo controller = loader.getController();
    //     controller.setCust(custID, custType);

    //     try {
    //       HBox hbox = loader.load();
    //       SinglePropInfo singlePropInfo = loader.getController();
    //       // setting the property data
    //       singlePropInfo.setPropertyData(list.get(i));
    //       // adding to the VBox
    //       propList.getChildren().add(hbox);
    //     } catch (IOException e) {
    //       e.printStackTrace();
    //     }
    //   }
    }

    @FXML
    private TextField searchProp;

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
