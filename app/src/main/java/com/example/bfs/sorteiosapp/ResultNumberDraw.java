package com.example.bfs.sorteiosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class ResultNumberDraw extends UniqueResult {

    int lower, upper;

    void getFromLastActivity() {
        this.lower = getIntVarFromLastActivity("lower");
        this.upper = getIntVarFromLastActivity("upper");
    }

    int rand(int ini, int sz) {
        Random x = new Random();
        return ini + x.nextInt(sz);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeAttributions();

        getFromLastActivity();

        displayResult(rand(lower, upper - lower + 1));
    }
}
