package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

    void changeWeight(TextView tv, float weight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                tv.getLayoutParams();
        params.weight = 80f;
        tv.setLayoutParams(params);
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
        TextView tvSkill = (TextView) convertView.findViewById(R.id.textView3);
        if(type < 10) {
            tvProb.setVisibility(convertView.GONE);
            tvSkill.setVisibility(convertView.GONE);
        }

        tvName.setText(name);

        if(prob != -1) {
            tvProb.setText(Integer.toString(prob));
        } else {
            tvProb.setText("—");
        }

        if(skill != -1)
            tvSkill.setText(Integer.toString(skill));
        else
            tvSkill.setText("—");

        if(type == 11) {
            tvSkill.setVisibility(convertView.GONE);
//            changeWeight(tvName, 33.3f);
            changeWeight(tvProb, 33.3f);
        } else if(type == 12) {
            tvProb.setVisibility(convertView.GONE);
//            changeWeight(tvName, 33.3f);
            changeWeight(tvSkill, 33.3f);
        }
        return convertView;
    }
}
