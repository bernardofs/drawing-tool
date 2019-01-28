package com.example.bfs.DrawingTool;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ResultIndividualDraw extends UniqueResult implements Serializable {

    private ArrayList<Participant> table;
    private int type;

    void getTableFromAddPart() {

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            table = (ArrayList<Participant>)getIntent().getSerializableExtra("en");
        }

    }

    int rand(int max) {
        Random x = new Random();
        return 1 + x.nextInt(max);
    }

    // Function to build a prefix sum array of probabilities
    ArrayList<Integer> makePrefixSumOfProb() {

        table.add(0, new Participant("", 0, 0));

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
        displayResult(table.get(choose).getName());
    }

    void getFromLastActivity() {
        this.type = getIntVarFromLastActivity("type");
    }

    int chooseElement() {

        if(type == 1) {
            return rand(table.size()) - 1;
        } else if(type == 11) {
            return performDraw();
        }

        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result_draw);

        makeAttributions();

        getFromLastActivity();
        getTableFromAddPart();

        int choose = chooseElement();

        displayDraw(choose);

    }
}
