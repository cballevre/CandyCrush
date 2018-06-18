import controller.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Board;
import model.Sweet;
import view.BoardView;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        FlowPane root = new FlowPane();

        Board board = new Board(8, 8);
        BoardController boardController = new BoardController(board);
        BoardView boardView = new BoardView(board, boardController);

        //board.setHeight(8);

        root.getChildren().add(boardView);

        stage.setTitle("Candy crush");
        stage.setWidth(800);
        stage.setHeight(800);
        stage.setScene(new Scene(root, java.awt.Window.WIDTH, java.awt.Window.HEIGHT));
        stage.show();
        stage.setOnCloseRequest(e-> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
