package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultTeamsListView extends ArrayAdapter<IndividualParticipant> {

    private Context cont;
    private int res;

    public ResultTeamsListView(Context context, int resource, ArrayList<IndividualParticipant> objects) {
        super(context, resource, objects);
        cont = context;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        int prob = getItem(position).getProb();

        IndividualParticipant ip = new IndividualParticipant(name, prob);

        LayoutInflater inflater = LayoutInflater.from(cont);
        convertView = inflater.inflate(res, parent, false);

        ListView tvName = (ListView) convertView.findViewById(R.id.listView);

        tvName.setText(name);
        tvProb.setText(Integer.toString(prob));

        return convertView;
    }
}

