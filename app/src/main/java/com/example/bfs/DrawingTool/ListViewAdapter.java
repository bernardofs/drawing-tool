package com.example.bfs.DrawingTool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private static final String TAG = "ListViewAdapter";
    private final ArrayList<String> topics;
    private final Context context;

    public ListViewAdapter(@NonNull Context context, ArrayList<String> topics) {
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount:" + topics.size());
        return topics.size();
    }

    @Override
    public Object getItem(int i) {
        return topics.get(i);
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
            listItem = LayoutInflater.from(context).inflate(R.layout.activity_card_adapter,parent,false);

        final String currentItem = (String) getItem(position);

        TextView topic = (TextView) listItem.findViewById(R.id.textView);
        topic.setText(currentItem);


        return listItem;
    }
}
