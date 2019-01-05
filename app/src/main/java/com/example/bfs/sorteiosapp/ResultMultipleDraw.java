package com.example.bfs.sorteiosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ResultMultipleDraw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_multiple_draw);
    }

    int rand(int max) {
        Random x = new Random();
        return x.nextInt(max);
    }

    // heuristics to generate a solution
    void generateSolution(int n, int m, ArrayList<PairIntString> arr) {

        // array which indicates the team of each participant
        ArrayList<Integer> participantTeam = new ArrayList<Integer>();

        // Segment tree to get the i'th index in O(log(n))
        SegmentTree tree = new SegmentTree(4*n);

        // participants at beginning don't have a team
        for(int i = 0; i < n; i++)
            participantTeam.add(-1);

        // pick the m + sqrt(m) participants with higher skill
        int cand = m + (int)Math.sqrt(m);

        int sz = 0;

        for(int i = 0; i < Math.min(n, cand); i++) {
            tree.update(0, n-1, i, 1, 0);
            sz++;
        }

        int[] skill = new int[m];
        // array to manage skill of each team
        for(int i = 0; i < m; i++)
            skill[i] = 0;


        for(int i = 0; i < m; i++) {
            int r = rand(sz);
            // get id in O(log(n)) of the r'th element excluding deleted elements
            int id = tree.getID(0, n-1, 0, r, 0);
            participantTeam.set(id, i%m);
            skill[i % m] += arr.get(id).ff;
            // remove this element from the tree
            tree.update(0, n-1, id, 0, 0);
            sz--;
        }

        int sq = (int)Math.sqrt(m);
        for(int i = m; i < n; i += m) {

            if(i + m >= n)
                break;

            ArrayList<Integer> ind = new ArrayList<Integer>();
            for(int j = 0; j < m; j++)
                ind.add(j);

            Collections.shuffle(ind);

            int j = 0;
            // pick the not chosen ones from the last "round"
            while(sz > 0) {
                int r = rand(sz);
                // get id in O(log(n)) of the r'th element excluding deleted elements
                int id = tree.getID(0, n-1, 0, r, 0);
                participantTeam.set(id, ind.get(j % m));
                skill[ind.get(j%m)] += arr.get(id).ff;
                // remove this element from the tree
                tree.update(0, n-1, id, 0, 0);
                sz--; j++;
            }

            // add the next m elements to the list to be chosen
            for(int l = i + sq; l < Math.min(n, i + m + sq); l++) {
                tree.update(0, n-1, l, 1, 0);
                sz++;
            }

            for(; j < m; j++) {
                if(i + j >= n || sz == 0)
                    break;
                int r = rand(sz);
                int id = tree.getID(0, n-1, 0, r, 0);
                participantTeam.set(id, ind.get(j % m));
                skill[ind.get(j%m)] += arr.get(id).ff;
                tree.update(0, n-1, id, 0, 0);
                sz--;
            }
        }

        ArrayList<PairIntInt> teams = new ArrayList<>();
        ArrayList<PairIntInt> remaining = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            teams.add(new PairIntInt(skill[i], i));
        }

        for(int i = 0; i < n; i++) {
            if(participantTeam.get(i) == -1) {
                remaining.add(new PairIntInt(arr.get(i).ff, i));
            }
        }

        Collections.sort(teams);

        // try to balance the remaining elements in the teams
        for(int i = 0; i < remaining.size(); i++) {
            skill[teams.get(i%m).ss] += remaining.get(i).ff;
            participantTeam.set(remaining.get(i).ss, teams.get(i%m).ss);
        }

        int menor = 1000000;
        int maior = -1;

        for(int i = 0; i < m; i++) {
            maior = max(skill[i], maior);
            menor = min(skill[i], menor);
        }

        System.out.println(maior - menor);

        for(int i = 0; i < n; i++) {
            System.out.println(arr.get(i).ss + "   " + arr.get(i).ff);
            System.out.println("participant " + i + " = " + participantTeam.get(i));
        }

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
