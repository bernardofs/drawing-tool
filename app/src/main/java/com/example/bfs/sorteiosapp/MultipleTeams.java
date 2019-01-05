package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultipleTeams extends Screen {

    void butRandTeamsClick() {

        Button butRandTeams = (Button)findViewById(R.id.butRandTeams);
        butRandTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MultipleTeams.this, AddParticipants.class);
                myIntent.putExtra("intVariableName", 2);
                startActivity(myIntent);
            }
        });

    }

    void butDrawBalancedClick() {

        Button butDrawBalanced = (Button)findViewById(R.id.butDrawBalanced);
        butDrawBalanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MultipleTeams.this, AddParticipants.class);
                myIntent.putExtra("intVariableName", 12);
                startActivity(myIntent);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_teams);

        butRandTeamsClick();
        butDrawBalancedClick();

    }


}
