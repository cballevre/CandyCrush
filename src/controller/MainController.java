package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.Board;
import view.BoardView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private VBox container;

    public MainController() {



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Board board = new Board(8, 8);
        BoardController boardController = new BoardController(board);
        BoardView boardView = new BoardView(board, boardController);

        container.getChildren().add(boardView);

    }
}
