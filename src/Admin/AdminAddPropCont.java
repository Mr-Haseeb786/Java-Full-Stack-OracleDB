package Admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class AdminAddPropCont implements Initializable {

    @FXML
    private Pane additionalFlat;

    @FXML
    private Pane additionalHouse;

    @FXML
    private Pane additionalLand;

    @FXML
    private TextField flatNo;

    @FXML
    private TextField flatRoomsNumb;

    @FXML
    private TextField floorNumb;

    @FXML
    private TextField houseCap;

    @FXML
    private TextField lawnSize;

    @FXML
    private TextField plotNumber;

    @FXML
    private ChoiceBox<String> propPurpose;

    @FXML
    private ChoiceBox<String> propTypeList;

    @FXML
    private TextField propZipCode;

    @FXML
    private TextField propertyPrice;

    @FXML
    private TextField roomsNumb;

    @FXML
    private Button firstSubBtn;

    @FXML
    void exitAdmin(ActionEvent event) {
      System.exit(0);
    }

    @FXML
    void finalSub (ActionEvent e){


      String propType = propTypeList.getValue();

      DBConnection connection;
      String id = "PRO" + Integer.toString((int)(Math.random() * 20380030));
      String query = "INSERT INTO PROPERTY VALUES ('" + id + "', null, " + Integer.parseInt(propZipCode.getText()) + ", " + Integer.parseInt(propertyPrice.getText()) + ", '" + propPurpose.getValue() + "' , '" + propType.toUpperCase() + "')";
      connection = new DBConnection(query);

      try {
        connection.connect();
      } catch (Exception e1) {
        e1.printStackTrace();
        return;
      }

      switch (propType) {
        case "Flat":
        query = "INSERT INTO FLAT VALUES ('" + id + "', " + Integer.parseInt(flatRoomsNumb.getText()) + ", " + Integer.parseInt(flatNo.getText()) + ") ";
        connection = new DBConnection(query);
        try {
          connection.connect();
        } catch (Exception e1) {
          e1.printStackTrace();
          return;
        }
          break;

        case "House":

        query = "INSERT INTO HOUSE VALUES ('" + id + "', " + Integer.parseInt(houseCap.getText()) + ", " + Integer.parseInt(floorNumb.getText()) + ", " + Integer.parseInt(roomsNumb.getText()) + ", " + Integer.parseInt(lawnSize.getText()) + ")";
        connection = new DBConnection(query);
        try {
          connection.connect();
        } catch (Exception e1) {
          e1.printStackTrace();
          return;
        }

          break;

        case "Land":

        query = "INSERT INTO LAND VALUES ('" + id + "' , " + Integer.parseInt(plotNumber.getText()) + ")";
        connection = new DBConnection(query);
        try {
          connection.connect();
        } catch (Exception e1) {
          e1.printStackTrace();
          return;
        }

          break;
        default:
          System.out.println("ERROR");
          break;
      }

      Alert propInserted = new Alert(AlertType.CONFIRMATION);

      propInserted.setTitle("Insertion Information");
      propInserted.setHeaderText(null);
      propInserted.setContentText("Property inserted successfully");
      propInserted.setX(550);
      propInserted.setY(300);
      propInserted.showAndWait();

      additionalFlat.setVisible(false);
      additionalHouse.setVisible(false);
      additionalLand.setVisible(false);

      firstSubBtn.setDisable(false);

      propertyPrice.clear();
      propZipCode.clear();
      propPurpose.setValue("");
      propTypeList.setValue("");

    }

    @FXML
    void firstSub(ActionEvent event) {

      if (propTypeList.getValue().isEmpty() || propertyPrice.getText().isEmpty() || propPurpose.getValue().isEmpty() || propZipCode.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill out all fields");
        alert.showAndWait();
        return;
      }

      if (propTypeList.getValue().equals("Flat")) {
        additionalFlat.setVisible(true);
      } else if (propTypeList.getValue().equals("House")) {
        additionalHouse.setVisible(true);
      } else if (propTypeList.getValue().equals("Land")) {
        additionalLand.setVisible(true);
      }
      firstSubBtn.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      additionalFlat.setVisible(false);
      additionalHouse.setVisible(false);
      additionalLand.setVisible(false);

      ObservableList<String> propType = FXCollections.observableArrayList();
      propType.add("Flat");
      propType.add("House");
      propType.add("Land");

      propTypeList.setItems(propType);
      propTypeList.setValue("Flat");

      ObservableList<String> propPurposeList = FXCollections.observableArrayList();
      propPurposeList.add("FOR_SALE");
      propPurposeList.add("FOR_RENT");

      propPurpose.setValue("FOR_SALE");
      propPurpose.setItems(propPurposeList);
    }

}
