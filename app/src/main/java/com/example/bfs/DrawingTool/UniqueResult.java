package com.example.bfs.DrawingTool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UniqueResult extends Screen {

    TextView textDisplayName;

    void makeAttributions() {
        setContentView(R.layout.activity_unique_result);
        textDisplayName = (TextView) findViewById(R.id.textDisplayName);
        butDoneClick();
    }

    void displayResult(String x) {
        textDisplayName.setText(x);
    }

    void displayResult(int x) {
        textDisplayName.setText(Integer.toString(x));
    }

    void butDoneClick() {
        Button butDone = (Button) findViewById(R.id.butDone);
        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToInitialScreenAndCloseAllActivities();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
