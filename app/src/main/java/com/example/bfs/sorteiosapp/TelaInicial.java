package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TelaInicial extends Screen {

    void butIndClick() {

        Button butInd = (Button)findViewById(R.id.butInd);
        butInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TelaInicial.this, AddParticipants.class);
                myIntent.putExtra("intVariableName", 11);
                startActivity(myIntent);
            }
        });

    }

    void butMultipleClick() {

        Button butMult = (Button)findViewById(R.id.butMult);
        butMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityAndKeep((Context)TelaInicial.this, MultipleTeams.class);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("TelaInicial");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


        butIndClick();
        butMultipleClick();
        
    }
}
