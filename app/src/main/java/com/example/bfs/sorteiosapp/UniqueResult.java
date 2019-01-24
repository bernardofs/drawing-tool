package com.example.bfs.sorteiosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UniqueResult extends Screen {

    TextView textDisplayName;

    void makeAttributions() {
        setContentView(R.layout.activity_unique_result);
        textDisplayName = (TextView) findViewById(R.id.textDisplayName);
    }

    void displayResult(String x) {
        textDisplayName.setText(x);
    }

    void displayResult(int x) {
        textDisplayName.setText(Integer.toString(x));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
