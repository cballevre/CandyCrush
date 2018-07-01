package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Board;
import model.Color;
import model.Direction;
import model.Sweet;
import view.SweetView;

import java.util.ArrayList;

public class BoardController implements EventHandler{

    private Board board;
    private Sweet selected;

    public BoardController(Board board) {
        this.board = board;
    }

    @Override
    public void handle(Event event) {

        Sweet clicked = ((SweetView) event.getSource()).getSweet();

        if(selected != null) {

            if(selected.isNeighbor(clicked)) {

                board.setGrid(swapSweet(board.getGrid(), selected, clicked));

                if(!scan(selected) && !scan(clicked)) {
                    board.setGrid(swapSweet(board.getGrid(),clicked, selected));
                }

                selected.setSelected(false);
                selected = null;

            } else {
                selected.setSelected(false);
                selected = clicked;
                selected.setSelected(true);
            }
        } else {
            selected = clicked;
            selected.setSelected(true);
        }
    }

    private boolean makeCombo() {

        boolean result = true;
        Sweet[][] grid = board.getGrid();

        for (int j = 0; j < board.getHeight(); j++) {
            for (int i = 0; i < board.getWidth(); i++) {
                Sweet sweet = grid[i][j];
                if(sweet != null) {
                    ArrayList<Sweet> listRow = test(sweet, Direction.RIGHT);

                    if((listRow.size() + 1) >= 3) {
                        grid = removeListSweet(grid, listRow);
                        result = false;
                    }

                    if(!result) {
                        grid = removeSweet(grid, sweet);
                    }

                }
            }
        }

        board.setGrid(grid);

        return result;

    }

    private boolean reorder() {

        boolean result = true;
        Sweet[][] grid = board.getGrid();

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if(grid[i][j] == null) {
                    try {
                        Sweet sweet = grid[i][j-1];
                        if(grid[i][j-1] != null) {
                            grid = replaceSweet(grid, grid[i][j-1], i, j);
                        }
                    } catch (Exception e) {
                        Sweet sweet = Sweet.makeRandom(i,j);
                        grid[i][j] = sweet;
                    }
                    result = false;
                }
            }
        }

        board.setGrid(grid);

        return result;

    }

    private boolean scan(Sweet sweet) {

        boolean result = false;
        Sweet[][] grid = board.getGrid();

        ArrayList<Sweet> listCol = new ArrayList<>();
        ArrayList<Sweet> listRow = new ArrayList<>();

        listRow.addAll(test(sweet, Direction.LEFT));
        listRow.addAll(test(sweet, Direction.RIGHT));

        listCol.addAll(test(sweet, Direction.UP));
        listCol.addAll(test(sweet, Direction.DOWN));

        if((listCol.size() + 1) >= 3) {
            grid = removeListSweet(grid, listCol);
            result = true;
        }

        if((listRow.size() + 1) >= 3) {
            grid = removeListSweet(grid, listRow);
            result = true;
        }

        if(result) {
            grid = removeSweet(grid, sweet);
            board.setGrid(grid);
        }

        return result;

    }

    private ArrayList<Sweet> test(Sweet sweet, Direction direction) {

        Color sweetColor = sweet.getColor();
        ArrayList<Sweet> list = new ArrayList<>();
        Sweet[][] grid = board.getGrid();
        boolean test = true;
        int i = 1;

        do {

            Sweet selected = null;

            try {
                if(direction.getX() != 0) {
                    selected = grid[sweet.getCol() + (i * direction.getX())][sweet.getRow()];
                } else {
                    selected = grid[sweet.getCol()][sweet.getRow() + (i * direction.getY())];
                }

                if(selected.getColor() == sweetColor) {
                    list.add(selected);
                } else{
                    test = false;
                }

            } catch (Exception e) {
                test = false;
            }


            i++;

        } while(test);

        return list;

    }

    public Sweet[][] removeListSweet(Sweet[][] grid, ArrayList<Sweet> list) {
        for (Sweet sweet : list) {
            grid[sweet.getCol()][sweet.getRow()] = null;
        }
        return grid;
    }

    public Sweet[][] removeSweet(Sweet[][] grid, Sweet sweet) {
        grid[sweet.getCol()][sweet.getRow()] = null;
        return grid;
    }

    public Sweet[][] swapSweet(Sweet[][] grid, Sweet selected, Sweet clicked) {

        int tmpCol = selected.getCol();
        int tmpRow = selected.getRow();

        int x1 = clicked.getCol() - selected.getCol();
        int y1 = clicked.getRow() - selected.getRow();

        int x2 = selected.getCol() - clicked.getCol();
        int y2 = selected.getRow() - clicked.getRow();

        grid[clicked.getCol()][clicked.getRow()] = selected;
        selected.setCol(clicked.getCol());
        selected.setRow(clicked.getRow());
        selected.setMoves(Direction.valueOf(x2, y2));

        grid[tmpCol][tmpRow] = clicked;
        clicked.setCol(tmpCol);
        clicked.setRow(tmpRow);
        System.out.println(Direction.valueOf(x1, y1));
        clicked.setMoves(Direction.valueOf(x1, y1));

        return grid;

    }

    public Sweet[][] replaceSweet(Sweet[][] grid, Sweet sweet, int i, int j) {

        int tmpCol = sweet.getCol();
        int tmpRow = sweet.getRow();

        int x = sweet.getCol() - i;
        int y = sweet.getRow() - j;

        grid[i][j] = sweet;
        sweet.setCol(i);
        sweet.setRow(j);
        sweet.setMoves(Direction.valueOf(x, y));

        grid[tmpCol][tmpRow] = null;

        return grid;

    }

}