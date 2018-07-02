package controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Board;
import view.BoardView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private HBox container;

    public MainController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BoardView boardView = initGame(8,8);

        container.getChildren().add(boardView);

    }

    @FXML
    private void newGame(ActionEvent event)
    {
        BoardView boardView = initGame(8,8);

        container.getChildren().clear();
        container.getChildren().add(boardView);
    }

    @FXML
    private void stat(ActionEvent event)
    {
        Stage stage;
        stage = new Stage();

        StackPane pane = new StackPane();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Swing in JavaFX");
        stage.setScene(new Scene(pane, 250, 150));
        stage.show();
    }

    @FXML
    private void exit(ActionEvent event)
    {
        Platform.exit();
    }

    private BoardView initGame(int nbCol, int nbRow) {

        Board board = new Board(nbCol, nbRow);
        BoardController boardController = new BoardController(board);
        BoardView boardView = new BoardView(board, boardController);
        boardView.setId("gameboard");

        return boardView;
    }
}
