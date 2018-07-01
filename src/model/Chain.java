package model;

import java.util.ArrayList;

public class Chain {

    private ArrayList<Sweet> list;
    private ChainType chainType;

    public Chain(ChainType chainType) {
        this.chainType = chainType;
    }

    public void add(Sweet sweet) {
        list.add(sweet);
    }

    public Sweet firstSweet() {
        return list.get(0);
    }

    public Sweet lastSweet() {
        return list.get(list.size()-1);
    }

    public int length (){
        return list.size();
    }

    public ArrayList<Sweet> getList() {
        return list;
    }

    public void setList(ArrayList<Sweet> list) {
        this.list = list;
    }
}

