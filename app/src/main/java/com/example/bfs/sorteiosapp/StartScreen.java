package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends Screen {

    void butIndClick() {

        Button butInd = (Button)findViewById(R.id.butInd);
        butInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartScreen.this, IndividualDrawChooseType.class);
                myIntent.putExtra("type", 11);
                startActivity(myIntent);
            }
        });

    }

    void butMultipleClick() {

        Button butMult = (Button)findViewById(R.id.butMult);
        butMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityAndKeep((Context) StartScreen.this, OptionsMultipleDraw.class);
            }
        });
    }

    void butSavedParticipantsClick() {
        Button butSaved = (Button)findViewById(R.id.butSavedParticipants);
        butSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityAndKeep((Context) StartScreen.this, ShowSavedParticipants.class);
            }
        });
    }

    void butDrawNumberClick() {
        Button butDrawNumber = (Button)findViewById(R.id.buttonDrawNumber);
        butDrawNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityAndKeep(StartScreen.this, OptionsDrawANumber.class );
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("StartScreen");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        butDrawNumberClick();
        butSavedParticipantsClick();
        butIndClick();
        butMultipleClick();
        
    }
}
