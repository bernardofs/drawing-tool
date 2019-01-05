package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PartListAdapter extends ArrayAdapter<IndividualParticipant> {

    private Context cont;
    private int res, type;

    public PartListAdapter(Context context, int resource, ArrayList<IndividualParticipant> objects, int type) {
        super(context, resource, objects);
        cont = context;
        res = resource;
        this.type = type;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        int prob = getItem(position).getProb();

        IndividualParticipant ip = new IndividualParticipant(name, prob);

        LayoutInflater inflater = LayoutInflater.from(cont);
        convertView = inflater.inflate(res, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvProb = (TextView) convertView.findViewById(R.id.textView2);
        if(type < 10)
            tvProb.setVisibility(convertView.GONE);

        tvName.setText(name);
        tvProb.setText(Integer.toString(prob));

        return convertView;
    }
}
