import java.io.IOException;
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

public class SignUpCont {
  private Scene scene;
  private Stage stage;
  private Parent root;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField phoneNumb;

    @FXML
    private PasswordField ConfrimPass;

    @FXML
    private TextField signUpEmail;

    @FXML
    private PasswordField signUpPass;

    @FXML
    private Label toastNoti;

    @FXML
    void gotoSignIn(ActionEvent event) {
      try {
        root = FXMLLoader.load(getClass().getResource("UserLoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void signUp(ActionEvent event) {
      System.out.println(signUpEmail.getText().length());
      if (signUpName.getText().isEmpty() || signUpEmail.getText().isEmpty() || phoneNumb.getText().isEmpty() || signUpPass.getText().isEmpty() || ConfrimPass.getText().isEmpty()) {
        toastNoti.setTextFill(Color.RED);
        toastNoti.setText("Please fill out all fields");

        return;
      }

      if (signUpPass.getText().equals(ConfrimPass.getText())) {

      // Generating Customer ID
      Integer randomizer = (int) (Math.random() * 10020038);
      String id = signUpName.getText().substring(0, 3).toUpperCase() + randomizer.toString() + signUpEmail.getText().substring(0, 3).toUpperCase();

      System.out.println(id);

      // Inserting into database
      String query = "INSERT INTO CUSTOMER VALUES ('" + id + "', '" + signUpName.getText() + "', '" + signUpEmail.getText() + "', " + Integer.parseInt(phoneNumb.getText()) + ", null, '" + signUpPass.getText() + "')";
      DBConnection connection = new DBConnection(query);
      try {
        connection.connect();
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        root = FXMLLoader.load(getClass().getResource("UserLoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      toastNoti.setTextFill(Color.RED);
      toastNoti.setText("Passwords do not match");
    }
    }

}
