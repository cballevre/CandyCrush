package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable{

    private Sweet[][] grid;
    private int width;
    private int height;

    public Board(int with, int height) {
        this.width = with;
        this.height = height;

        init();
    }

    private void init() {

        grid = new Sweet[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Sweet sweet;
                do {
                    sweet = Sweet.makeRandom(i, j);
                } while ((i >= 2 && grid[i - 1][j].getColor() == sweet.getColor() && grid[i - 2][j].getColor() == sweet.getColor())
                || (j >= 2 && grid[i][j - 1].getColor() == sweet.getColor() && grid[i][j - 2].getColor() == sweet.getColor()));
                grid[i][j] = sweet;
            }
        }
    }

    public Sweet[][] getGrid() {
        return grid;
    }

    public void setGrid(Sweet[][] grid) {
        this.grid = grid;
        notifVues();
    }

    public void addVue(Observer vue) {
        addObserver(vue);
    }

    public void removeVue(Observer vue) {
        deleteObserver(vue);
    }

    public void notifVues() {
        setChanged();
        notifyObservers();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        notifVues();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        notifVues();
    }

    public void removeListSweet(ArrayList<Sweet> list) {
        for (Sweet sweet : list) {
            grid[sweet.getCol()][sweet.getRow()].setDeleted(true);
        }
        notifVues();
    }

    public void removeSweet(Sweet sweet) {
        grid[sweet.getCol()][sweet.getRow()].setDeleted(true);
        notifVues();
    }

    public void swapSweet(Sweet selected, Sweet clicked) {

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

        notifVues();

    }

    public void replaceSweet(Sweet sweet, int i, int j) {

        int tmpCol = sweet.getCol();
        int tmpRow = sweet.getRow();

        int x = sweet.getCol() - i;
        int y = sweet.getRow() - j;

        grid[i][j] = sweet;
        sweet.setCol(i);
        sweet.setRow(j);
        sweet.setMoves(Direction.valueOf(x, y));

        grid[tmpCol][tmpRow] = null;
        notifVues();

    }

    public void generateSweet(int i, int j) {
        Sweet sweet = Sweet.makeRandom(i, j);
        grid[i][j] = sweet;
        notifVues();
    }

}
