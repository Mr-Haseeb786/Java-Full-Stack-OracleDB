package Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminApp extends Application{

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminAddProp.fxml"));
    Parent root = loader.load();

    Scene sc = new Scene(root);
    // sc.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    primaryStage.setScene(sc);
    primaryStage.setX(300);
    primaryStage.setY(120);
    primaryStage.show();

  }

    public static void main(String[] args) throws Exception {
      launch(args);
    }

}
