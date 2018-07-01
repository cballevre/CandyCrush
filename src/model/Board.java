package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable implements Observer{

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
                grid[i][j] = Sweet.makeRandom(i, j);
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

    @Override
    public void update(Observable o, Object arg) {
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

}
