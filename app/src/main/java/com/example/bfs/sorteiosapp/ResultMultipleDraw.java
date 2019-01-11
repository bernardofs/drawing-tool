package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ResultMultipleDraw extends Screen {

    private ArrayList<Participant> table;
    private ArrayList< ArrayList <Participant> > teams;
    private int type, randomnessParam, numberOfTeams;

    void getTableFromAddPart() {

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            this.table = (ArrayList<Participant>)getIntent().getSerializableExtra("en");
        }

    }

    void getFromLastActivity() {

        this.type = getIntVarFromLastActivity("type");
        this.randomnessParam = getIntVarFromLastActivity("randomnessParam");
        this.numberOfTeams = getIntVarFromLastActivity("numberOfTeams");
        getTableFromAddPart();
    }

    void buildTeams() {

        if(randomnessParam <= 5) {
            // random solution
            teams = generateBestRandomSolution(table.size(), numberOfTeams, randomnessParam, table);
        } else if(randomnessParam <= 14) {
            teams = generateBestSolution(table.size(), numberOfTeams, table, (randomnessParam - 5)*3);
        } else {
            teams = generateBestSolution(table.size(), numberOfTeams, table, 1000);
        }

    }

    void makeListView() {
        ListView listView = (ListView) findViewById(R.id.lView);
        CardAdapter cardsAdapter = new CardAdapter(this, teams);
        listView.setAdapter(cardsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_multiple_draw);

        getFromLastActivity();

        buildTeams();
        makeListView();
    }



    ArrayList<ArrayList<Participant>> generateBestRandomSolution(int nPeople, int nTeams, int randomnessParam, ArrayList<Participant> arr) {

        ArrayList<ArrayList<Participant>> sol = new ArrayList<ArrayList<Participant>>();
        for(int i = 0; i < nTeams; i++)
            sol.add(new ArrayList<Participant>());

        int best = generateRandomSolution(nPeople, nTeams, (1 << 30), arr, sol);

        int iter = randomnessParam;

        while(iter-- > 0) {
            best = generateRandomSolution(nPeople, nTeams, best, arr, sol);
        }

        return sol;
    }

    int generateRandomSolution(int nPeople, int nTeams, int best, ArrayList<Participant> arr, ArrayList<ArrayList<Participant>> sol) {

        ArrayList<Integer> participantTeam = new ArrayList<Integer>();
        for(int i = 0; i < nPeople; i++) {
            participantTeam.add(i%nTeams);
        }

        Collections.shuffle(participantTeam);

        int skill[] = new int [nTeams];

        for(int i = 0; i < nTeams; i++)
            skill[i] = 0;

        for(int i = 0; i < participantTeam.size(); i++)
            skill[participantTeam.get(i)] += arr.get(i).getSkill();

        int menor = 1000000;
        int maior = -1;

        for(int i = 0; i < nTeams; i++) {
            maior = max(skill[i], maior);
            menor = min(skill[i], menor);
        }

        if(maior - menor < best) {

            best = maior - menor;

            for(int i = 0; i < nTeams; i++) {
                sol.get(i).clear();
            }

            for(int i = 0; i < nPeople; i++) {
                sol.get(participantTeam.get(i)).add(arr.get(i));
            }
        }

        return best;
    }

    int rand(int mod) {
        Random x = new Random();
        return x.nextInt(mod);
    }

    ArrayList<ArrayList<Participant>> generateBestSolution(int nPeople, int nTeams, ArrayList<Participant> arr, int iter) {

        // generate $iter$ possible solutions and pick the best one

        ArrayList<ArrayList<Participant>> sol = new ArrayList<ArrayList<Participant>>();
        for(int i = 0; i < nTeams; i++)
            sol.add(new ArrayList<Participant>());

        // sort array by greatest skill
        Collections.sort(arr, new Comparator<Participant>() {
            @Override
            public int compare(Participant o1, Participant o2) {
                if(o1.getSkill() > o2.getSkill())
                    return -1;
                else if(o1.getSkill() < o2.getSkill())
                    return 1;
                else
                    return (rand(2) == 1 ? 1 : -1);
            }
        });

        int best = generateBalancedSolution(nPeople, nTeams, arr, (1 << 30), sol);

        while(iter-- > 0) {
            best = generateBalancedSolution(nPeople, nTeams, arr, best, sol);
        }

        // Sorting in descending order of arraylist size
        Collections.sort(sol, new Comparator<ArrayList<Participant>>() {
            @Override
            public int compare(ArrayList<Participant> a, ArrayList<Participant> b) {
                return  (a.size() >= b.size() ? -1 : 1);
            }
        });

        return sol;

    }


    // heuristics to generate a solution
    int generateBalancedSolution(int nPeople, int nTeams, ArrayList<Participant> arr, int best, ArrayList<ArrayList<Participant>> sol) {

        // array which indicates the team of each participant
        ArrayList<Integer> participantTeam = new ArrayList<Integer>();

        // Segment tree to get the i'th index in O(log(n))
        SegmentTree tree = new SegmentTree(4*nPeople);

        // participants at beginning don't have a team
        for(int i = 0; i < nPeople; i++)
            participantTeam.add(-1);

        // pick the nTeams + sqrt(nTeams) participants with higher skill
        int cand = nTeams + (int)Math.sqrt(nTeams);

        int sz = 0;

        for(int i = 0; i < Math.min(nPeople, cand); i++) {
            tree.update(0, nPeople-1, i, 1, 0);
            sz++;
        }

        int[] skill = new int[nTeams];
        // array to manage skill of each team
        for(int i = 0; i < nTeams; i++)
            skill[i] = 0;


        for(int i = 0; i < nTeams; i++) {
            int r = rand(sz);
            // get id in O(log(n)) of the r'th element excluding deleted elements
            int id = tree.getID(0, nPeople-1, 0, r, 0);
            participantTeam.set(id, i%nTeams);
            skill[i % nTeams] += arr.get(id).getSkill();
            // remove this element from the tree
            tree.update(0, nPeople-1, id, 0, 0);
            sz--;
        }

        int sq = (int)Math.sqrt(nTeams);
        for(int i = nTeams; i < nPeople; i += nTeams) {

            if(i + nTeams >= nPeople)
                break;

            ArrayList<Integer> ind = new ArrayList<Integer>();
            for(int j = 0; j < nTeams; j++)
                ind.add(j);

            Collections.shuffle(ind);

            int j = 0;
            // pick the not chosen ones from the last "round"
            while(sz > 0) {
                int r = rand(sz);
                // get id in O(log(n)) of the r'th element excluding deleted elements
                int id = tree.getID(0, nPeople-1, 0, r, 0);
                participantTeam.set(id, ind.get(j % nTeams));
                skill[ind.get(j%nTeams)] += arr.get(id).getSkill();
                // remove this element from the tree
                tree.update(0, nPeople-1, id, 0, 0);
                sz--; j++;
            }

            // add the next nTeams elements to the list to be chosen
            for(int l = i + sq; l < Math.min(nPeople, i + nTeams + sq); l++) {
                tree.update(0, nPeople-1, l, 1, 0);
                sz++;
            }

            for(; j < nTeams; j++) {
                if(i + j >= nPeople || sz == 0)
                    break;
                int r = rand(sz);
                int id = tree.getID(0, nPeople-1, 0, r, 0);
                participantTeam.set(id, ind.get(j % nTeams));
                skill[ind.get(j%nTeams)] += arr.get(id).getSkill();
                tree.update(0, nPeople-1, id, 0, 0);
                sz--;
            }
        }

        ArrayList<PairIntInt> teams = new ArrayList<>();
        ArrayList<PairIntInt> remaining = new ArrayList<>();
        for(int i = 0; i < nTeams; i++) {
            teams.add(new PairIntInt(skill[i], i));
        }

        for(int i = 0; i < nPeople; i++) {
            if(participantTeam.get(i) == -1) {
                remaining.add(new PairIntInt(arr.get(i).getSkill(), i));
            }
        }

        Collections.sort(teams);

        // try to balance the remaining elements in the teams
        for(int i = 0; i < remaining.size(); i++) {
            skill[teams.get(i%nTeams).ss] += remaining.get(i).ff;
            participantTeam.set(remaining.get(i).ss, teams.get(i%nTeams).ss);
        }

        int menor = 1000000;
        int maior = -1;

        for(int i = 0; i < nTeams; i++) {
            maior = max(skill[i], maior);
            menor = min(skill[i], menor);
        }

        if(maior - menor < best) {

            best = maior - menor;

            for(int i = 0; i < nTeams; i++) {
                sol.get(i).clear();
            }

            for(int i = 0; i < nPeople; i++) {
                sol.get(participantTeam.get(i)).add(arr.get(i));
            }
        }

        return best;

    }

    int max(int a, int b) {
        if(a > b)
            return a;
        return b;
    }

    int min(int a, int b) {
        if(b > a)
            return a;
        return b;
    }


}
