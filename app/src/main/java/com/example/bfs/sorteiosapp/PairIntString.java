package com.example.bfs.sorteiosapp;

public class PairIntString implements Comparable<PairIntString> {

    int ff;
    String ss;

    PairIntString(int ff, String ss) {
        this.ff = ff;
        this.ss = ss;
    }

    @Override
    public int compareTo(PairIntString x) {
        return ((Integer)ff).compareTo(x.ff);
    }
}
