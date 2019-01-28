package com.example.bfs.DrawingTool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        // get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // Change Height of ListView
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight + (myListView.getDividerHeight() * (adapterCount)));
        myListView.setLayoutParams(params);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem==null)
            listItem = LayoutInflater.from(context).inflate(R.layout.layout_card_view,parent,false);

        ArrayList<String> currentItem = (ArrayList<String>) getItem(position);

        TextView tvTeamNumber= (TextView) listItem.findViewById(R.id.tvTeamNumber);
        tvTeamNumber.setText("TEAM " + Integer.toString(position+1));
        ListView topic = (ListView) listItem.findViewById(R.id.listView);
        ListViewAdapter listNewAdapter = new ListViewAdapter(context,currentItem);
        topic.setAdapter(listNewAdapter);
        updateListViewHeight(topic);

        return listItem;
    }
}
