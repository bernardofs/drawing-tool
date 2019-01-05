package com.example.bfs.sorteiosapp;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ResultIndividualDraw extends Screen implements Serializable {

    private ArrayList<IndividualParticipant> table;

    void getTableFromAddPart() {

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            table = (ArrayList<IndividualParticipant>)getIntent().getSerializableExtra("en");
        }

    }

    int rand(int max) {
        Random x = new Random();
        return 1 + x.nextInt(max);
    }

    // Funcition to build a prefix sum array of probabilities
    ArrayList<Integer> makePrefixSumOfProb() {

        table.add(0, new IndividualParticipant("", 0));

        ArrayList<Integer> ps = new ArrayList<Integer>();
        ps.add(0);
        for(int i = 1; i < table.size(); i++) {

            int p = table.get(i).getProb();
            ps.add(ps.get(i-1) + p);
        }

        return ps;

    }

    // Binary Search to find the index of the choose element
    int lessOrEqual(int v, ArrayList<Integer> ps) {

        int l = 1, r = ps.size() - 1;

        int ret = -1;
        while(l <= r) {
            int mid = (l+r)/2;
            if(v <= ps.get(mid)) {
                ret = mid;
                r = mid - 1;
            } else
                l = mid + 1;
        }
        return ret;
    }

    int performDraw() {

        ArrayList<Integer> ps = makePrefixSumOfProb();

        int random = rand(ps.get(ps.size()-1));

        return lessOrEqual(random, ps);

    }

    void displayDraw(int choose) {

        TextView tv = (TextView)findViewById(R.id.textDisplayName);
        tv.setText(table.get(choose).getName());
    }

    void drawAgain() {

        Button butDraw = (Button)findViewById(R.id.butDoAgain);
        butDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Result");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_draw);

        getTableFromAddPart();

        int choose = performDraw();

        displayDraw(choose);

        drawAgain();

    }
}
