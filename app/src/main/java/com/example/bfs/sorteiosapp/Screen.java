package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
    }

    // function
    void changeActivityAndKeep(Context packageContext, Class cls) {
        Intent it = new Intent(packageContext, cls);
        startActivity(it);
    }

}
