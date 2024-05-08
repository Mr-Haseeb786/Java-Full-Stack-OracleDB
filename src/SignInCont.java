import java.io.IOException;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignInCont {
  private Scene scene;
  private Stage stage;
  private Parent root;
  private String customerID;
  private String customerName;
  private String customerType; 
  private boolean loginSuccess = false;

    @FXML
    private TextField signInEmail;

    @FXML
    private PasswordField signInPass;

    @FXML
    private Label toastNoti;

    @FXML
    void gotoSignUp(ActionEvent  event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void signIn(ActionEvent event) {

      String query = "SELECT CustomerID, CustomerName, CustomerType FROM CUSTOMER WHERE EMAIL = '" + signInEmail.getText() + "' AND PASSWORD = '" + signInPass.getText() + "'";
      DBConnection connection = new DBConnection(query);

      try {
        ResultSet results = connection.connect();
        
        while (results.next()) {
          customerID = results.getString(1);
          customerName = results.getString(2);
          customerType = results.getString(3);
          loginSuccess = true;
        }

      } catch (Exception e1) {
        e1.printStackTrace();
      }

      if (loginSuccess) {
      
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerUser.fxml"));
        root = loader.load();

        OwnerUserController controller = loader.getController();
        controller.setCustID(customerID, customerType);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(130);
        stage.setY(50);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      toastNoti.setTextFill(Color.RED);
      toastNoti.setText("Incorrect email or password");
    }
  }
}
