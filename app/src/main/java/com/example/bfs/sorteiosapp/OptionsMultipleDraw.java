package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionsMultipleDraw extends AppCompatActivity {

    private SeekBar sb;
    private TextView tv;
    private EditText et;

    void setVariablesInLayout() {
        sb = (SeekBar) findViewById(R.id.seekBar);
        tv = (TextView) findViewById(R.id.textView);
        et = (EditText) findViewById(R.id.etNumTeams);
    }

    void seekBarActions() {

        tv.setText("Balanced");
        sb.setProgress(10);
        sb.setMax(20);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {


                if(progress == 0) {
                    tv.setText("Completely Random");
                } else if(progress <= 5) {
                    tv.setText("Very Randomized");
                } else if(progress <= 14) {
                    tv.setText("Balanced");
                } else if (progress <= 19) {
                    tv.setText("Very Balanced");
                } else {
                    tv.setText("Try Most Balanced Solution");
                }

                int pos = sb.getThumb().getBounds().left - 30;
                tv.setX(pos);
            }
        });
    }

    void setAdvanceButtonText() {

        Button but = (Button) findViewById(R.id.butAdvance);
        but.setText("Proceed");
    }

    void butAdvanceClick() {

        Button butAdvance = (Button)findViewById(R.id.butAdvance);
        butAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(OptionsMultipleDraw.this, AddParticipants.class);
                int type, randomnessParam, numberOfTeams;
                if(sb.getProgress() == 0) {
                    type = 2;
                } else {
                    type = 12;
                }
                numberOfTeams = Integer.parseInt(et.getText().toString());
                randomnessParam = sb.getProgress();
                myIntent.putExtra("type", type);
                myIntent.putExtra("randomnessParam", randomnessParam);
                myIntent.putExtra("numberOfTeams", numberOfTeams);
                startActivity(myIntent);
            }
        });
    }

    void numberTeamsEditTextActions() {
        et.setText("1");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_multiple_draw);

        setVariablesInLayout();

        seekBarActions();

        numberTeamsEditTextActions();

        setAdvanceButtonText();

        butAdvanceClick();




    }
}
