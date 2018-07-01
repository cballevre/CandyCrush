package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.*;
import view.SweetView;

import java.util.ArrayList;
import java.util.Collections;

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

                board.swapSweet(selected, clicked);

                detectHorizontalChain(selected);

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

    private boolean gameEngine(Sweet selected, Sweet clicked) {

        boolean result = false;

        ArrayList<Sweet> deleted = new ArrayList<>();
        deleted.addAll(scan(selected));
        deleted.addAll(scan(clicked));

        if(deleted.size() > 0) {
            reorder(deleted);
            makeCombo(deleted);
            result = true;
        }

        return result;
    }

    private boolean makeCombo(ArrayList<Sweet> deleted) {

        boolean result = true;
        Sweet[][] grid = board.getGrid();
        ArrayList<Sweet> deleted2 = new ArrayList<>();

        for (Sweet sweet : deleted) {
            deleted2.addAll(scan(grid[sweet.getCol()][sweet.getRow()]));
        }

        reorder(deleted2);

        board.setGrid(grid);

        return result;

    }

    private void reorder(ArrayList<Sweet> delected) {

        boolean test;
        Sweet[][] grid = board.getGrid();

        do {
            test = true;

            for (Sweet sweet : delected) {
                if(sweet.isDeleted()) {
                    try {
                        Sweet upper = grid[sweet.getCol()][sweet.getRow()-1];
                        if(!upper.isDeleted()) {
                            grid = swapSweet(grid, sweet, upper);
                            test = false;
                        }
                    } catch (Exception e) {
                        grid = generateSweet(grid, sweet.getCol(), sweet.getRow());
                    }

                }
            }
        } while (!test);

        board.setGrid(grid);

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
        clicked.setMoves(Direction.valueOf(x1, y1));

        return grid;

    }

    public Sweet[][] generateSweet(Sweet[][] grid, int i, int j) {
        Sweet sweet = Sweet.makeRandom(i, j);
        grid[i][j] = sweet;

        return grid;
    }

    private ArrayList<Chain> detectChains(Sweet sweet) {

        boolean result = false;

        Chain horizontalChain = new Chain(ChainType.Horizontal);
        Chain verticalChain = new Chain(ChainType.Vertical);



        listRow.addAll(test(sweet, Direction.LEFT));
        listRow.addAll(test(sweet, Direction.RIGHT));

        listCol.addAll(test(sweet, Direction.UP));
        listCol.addAll(test(sweet, Direction.DOWN));

        ArrayList<Sweet> delected = new ArrayList<>();

        if((listCol.size() + 1) > 2) {
            if((listCol.size() + 1) > 3) {
                System.out.println(listCol.size());
                Sweet sweetSpecial = listCol.remove(listCol.size()/2);
                sweetSpecial.setType(TypeSweet.COL);
            }
            delected.addAll(listCol);
            result = true;
        }

        if((listRow.size() + 1) >= 3) {
            if((listRow.size() + 1) >= 4) {
                Sweet sweetSpecial = listRow.remove(listRow.size()/2);
                sweetSpecial.setType(TypeSweet.ROW);
            }
            delected.addAll(listRow);
            result = true;
        }

        if(result) {
            delected.add(sweet);
            board.setGrid(grid);
        }

        board.removeListSweet(delected);

        return delected;

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

        if(direction.getY() < 0) {
            Collections.reverse(list);
        }

        return list;

    }



}