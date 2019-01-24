package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IndividualDrawChooseType extends Screen {

    private Button butRandom, butProb;

    void setVariablesOfLayout() {
        butRandom = (Button) findViewById(R.id.butRandom);
        butProb = (Button) findViewById(R.id.butProbability);
    }

    void setButRandomClick() {

        butRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(IndividualDrawChooseType.this, AddParticipants.class);
                it.putExtra("type", 1);
                startActivity(it);
            }
        });
    }

    void setButProbClick() {

        butProb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(IndividualDrawChooseType.this, AddParticipants.class);
                it.putExtra("type", 11);
                startActivity(it);
            }
        });
    }

    void setButtonsClick() {

        setButRandomClick();
        setButProbClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_draw_choose_type);

        setVariablesOfLayout();

        setButtonsClick();
    }
}
