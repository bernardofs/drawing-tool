package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OptionsDrawANumber extends Screen {

    void butDrawClick() {
        Button butDraw = (Button) findViewById(R.id.butDraw);
        butDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etLower = (EditText)findViewById(R.id.etLower);
                EditText etUpper = (EditText)findViewById(R.id.etUpper);

                int n1 = Integer.parseInt(etLower.getText().toString());
                int n2 = Integer.parseInt(etUpper.getText().toString());

                if(!isNumber(n1) || !isNumber(n2))
                    return;

                if(n2 < n1) {
                    createErrorDialog("Upper must be greater or equal than lower");
                    return;
                }

                Intent it = new Intent(OptionsDrawANumber.this, ResultNumberDraw.class);
                it.putExtra("lower", n1);
                it.putExtra("upper", n2);
                startActivity(it);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_draw_a_number);

        butDrawClick();
    }
}
