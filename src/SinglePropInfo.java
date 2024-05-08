import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class SinglePropInfo implements Initializable {

  String custID;
  String custType;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }

    @FXML
    void buyProperty(ActionEvent event) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Buy Property");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to buy this property?");
      alert.showAndWait();
      ButtonType result = alert.getResult();
      System.out.println(result);
    }

    @FXML
    void rentProperty(ActionEvent event) {

    }

    public void setPropertyData(PropertyData pData) {
      propType.setText(pData.getPropertyType());
      propPrice.setText(pData.getPrice());
      propDesc.setText(pData.getDescription());
    }

}
