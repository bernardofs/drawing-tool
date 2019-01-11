package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class AddParticipants extends Screen implements Serializable {

    private TreeSet<Participant> people = new TreeSet<Participant>();
    private Dialog dialog;
    ListView lView;
    private ArrayList<Participant> table;
    private PartListAdapter adapter;
    private int type, randomnessParam, numberOfTeams;

    void updateElementFromDialog(int index, String prevName, int prevProb, int prevSkill) {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        String name = prevName;
        int prob = prevProb;
        int skill = prevSkill;
        name = writeName.getText().toString();
        String value;
        value = writeProb.getText().toString();
        if(type == 11) {
            prob = Integer.parseInt(value);
        }
        else if(type == 12) {
            skill = Integer.parseInt(value);
        }
        table.set(index, new Participant(name, prob, skill));
        adapter.notifyDataSetChanged();
    }

    void addElementFromDialog(String prevName, int prevProb, int prevSkill) {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        String name = prevName;
        int prob = prevProb;
        int skill = prevSkill;
        name = writeName.getText().toString();
        String value;
        value = writeProb.getText().toString();
        if(type == 11) {
            prob = Integer.parseInt(value);
        }
        else if(type == 12) {
            skill = Integer.parseInt(value);
        }
        table.add(new Participant(name, prob, skill));
        adapter.notifyDataSetChanged();
    }

    void removeElementFromDialog(int idx) {
        table.remove(idx);
        adapter.notifyDataSetChanged();
    }

    void actionAddParticipant() {
        Button butAddPart = (Button)findViewById(R.id.butAddPart);
        butAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AddParticipants.this);
                dialog.setTitle("Save Your Name");
                dialog.setContentView(R.layout.dialog_template);

                TextView message = (TextView) dialog.findViewById(R.id.message);
                Button addData = (Button) dialog.findViewById(R.id.butAdd);
                Button butRem = (Button) dialog.findViewById(R.id.butRemove);
                butRem.setVisibility(View.GONE);

                addData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addElementFromDialog("", -1, -1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void actionUpdateParticipant(final String oldName, final String oldProb, final String oldSkill, final int index){


        dialog = new Dialog(AddParticipants.this);

        dialog.setContentView(R.layout.dialog_template);
        TextView txtMessage=(TextView)dialog.findViewById(R.id.message);
        txtMessage.setText("Update item");

        Button butAdd = (Button)dialog.findViewById(R.id.butAdd);
        butAdd.setText("UPDATE");

        final EditText writeName = (EditText)dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText)dialog.findViewById(R.id.writeProb);

        writeName.setText(oldName);
        writeProb.setText(oldProb);

        Button butRemove = (Button)dialog.findViewById(R.id.butRemove);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateElementFromDialog(index, oldName, Integer.parseInt(oldProb), Integer.parseInt(oldSkill));
                dialog.dismiss();
            }
        });

        butRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeElementFromDialog(index);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void loadTable() {

       Iterator it = people.iterator();

       table.clear();

        while(it.hasNext()) {
            table.add((Participant)it.next());
        }

        adapter = new PartListAdapter(this, R.layout.participants_list_view, table, type);
        lView.setAdapter(adapter);
    }

    void butDoDrawAction() {

        Button butDraw = (Button)findViewById(R.id.butDoDraw);
        butDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(type == 11)
                    intent = new Intent(AddParticipants.this, ResultIndividualDraw.class);
                else {
                    if(type == 2) {
                        intent = new Intent(AddParticipants.this, ResultMultipleDraw.class);
                        intent.putExtra("type", 2);
                    }
                    else if(type == 12) {
                        intent = new Intent(AddParticipants.this, ResultMultipleDraw.class);
                        intent.putExtra("type", 12);
                    }
                    intent.putExtra("randomnessParam", randomnessParam);
                    intent.putExtra("numberOfTeams", numberOfTeams);
                }
                intent.putExtra("en", table); // second param is Serializable
                startActivity(intent);
            }

        });

    }

    void updateElementListView() {

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box

                Participant aux = table.get(position);

                if(type == 11)
                    actionUpdateParticipant(aux.getName(), Integer.toString(aux.getProb()), Integer.toString(aux.getSkill()), position);
                else if(type == 2 || type == 12)
                    actionUpdateParticipant(aux.getName(), Integer.toString(aux.getProb()), Integer.toString(aux.getSkill()), position);

            }
        });
    }

    void getFromLastActivity() {
        this.type = getIntVarFromLastActivity("type");
        this.randomnessParam = getIntVarFromLastActivity("randomnessParam");
        this.numberOfTeams = getIntVarFromLastActivity("numberOfTeams");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        lView = (ListView)findViewById(R.id.listView);
        System.out.println("New");

        getFromLastActivity();

        System.out.println("type = " + type);

        actionAddParticipant();
        butDoDrawAction();

        Participant p1 = new Participant("Adalberto", 1, 1);
        Participant p2 = new Participant("Bernardo", 3, 3);
        Participant p3 = new Participant("BFS", 4, 4);
        Participant p4 = new Participant("Cintia", 15, 15);
        Participant p5 = new Participant("Raimunda", 9, 9);
        Participant p6 = new Participant("Florisvaldo", 1, 1);
        Participant p7 = new Participant("Waleska", 2, 2);
        Participant p8 = new Participant("Neide", 5, 5);

        table = new ArrayList<Participant>();

        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);
        people.add(p6);
        people.add(p7);
        people.add(p8);

        loadTable();

        updateElementListView();
    }


}
