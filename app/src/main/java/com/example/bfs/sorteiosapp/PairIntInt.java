package com.example.bfs.sorteiosapp;

public class PairIntInt implements Comparable<PairIntInt> {

    int ff, ss;

    PairIntInt(int ff, int ss) {
        this.ff = ff;
        this.ss = ss;
    }

    @Override
    public int compareTo(PairIntInt x) {
        return ((Integer)ff).compareTo(x.ff);
    }
}
