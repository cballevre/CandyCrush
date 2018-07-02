import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));




        stage.setTitle("Candy crush");
        stage.setWidth(800);
        stage.setHeight(800);
        stage.setScene(new Scene(root, java.awt.Window.WIDTH, java.awt.Window.HEIGHT));
        stage.show();
        stage.setOnCloseRequest(e-> Platform.exit());
    }

}
