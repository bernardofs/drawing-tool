package com.example.bfs.sorteiosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {

    private static final String TAG = "CardsAdapter";
    private final Context context;
    ArrayList< ArrayList <Participant > > allTopics;

    public CardAdapter(@NonNull Context context, ArrayList< ArrayList <Participant > > unitsAndTopics) {
        this.context = context;
        allTopics = unitsAndTopics;
    }

    @Override
    public int getCount() {
        return allTopics.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        ArrayList<String> all = new ArrayList<String>();
        for (int i = 0; i <allTopics.get(position).size() ; i++) {
            all.add(allTopics.get(position).get(i).getName());
            Log.d(TAG, "getItem:"+all);
        }

        return all;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem==null)
            listItem = LayoutInflater.from(context).inflate(R.layout.layout_card_view,parent,false);

        ArrayList<String> currentItem = (ArrayList<String>) getItem(position);

        ListView topic = (ListView) listItem.findViewById(R.id.listView);
        ListViewAdapter listNewAdapter = new ListViewAdapter(context,currentItem);
        topic.setAdapter(listNewAdapter);

        return listItem;
    }
}
