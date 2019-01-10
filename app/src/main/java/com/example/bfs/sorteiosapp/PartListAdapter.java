package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PartListAdapter extends ArrayAdapter<Participant> {

    private Context cont;
    private int res, type;

    public PartListAdapter(Context context, int resource, ArrayList<Participant> objects, int type) {
        super(context, resource, objects);
        cont = context;
        res = resource;
        this.type = type;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        int prob, skill;
        prob = getItem(position).getProb();
        skill = getItem(position).getSkill();

        Participant ip = new Participant(name, prob, skill);

        LayoutInflater inflater = LayoutInflater.from(cont);
        convertView = inflater.inflate(res, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvProb = (TextView) convertView.findViewById(R.id.textView2);
        if(type < 10)
            tvProb.setVisibility(convertView.GONE);

        tvName.setText(name);
        if(type == 11)
            tvProb.setText(Integer.toString(prob));
        else if(type == 12)
            tvProb.setText(Integer.toString(skill));

        return convertView;
    }
}
