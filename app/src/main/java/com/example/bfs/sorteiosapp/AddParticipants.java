package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

    private TreeSet<IndividualParticipant> people = new TreeSet<IndividualParticipant>();
    private Dialog dialog;
    ListView lView;
    private ArrayList<IndividualParticipant> table;
    private PartListAdapter adapter;
    private int type;

    void addElementFromDialog() {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        String name = writeName.getText().toString();
        String value = writeProb.getText().toString();
        int prob = Integer.parseInt(value);
        table.add(new IndividualParticipant(name, prob));
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
                        addElementFromDialog();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void actionUpdateParticipant(String oldName, String oldProb, final int index){


        dialog = new Dialog(AddParticipants.this);

        dialog.setContentView(R.layout.dialog_template);
        TextView txtMessage=(TextView)dialog.findViewById(R.id.message);
        txtMessage.setText("Update item");

        Button butAdd = (Button)dialog.findViewById(R.id.butAdd);
        butAdd.setText("UPDATE");

        final EditText writeName = (EditText)dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText)dialog.findViewById(R.id.writeProb);
        System.out.println("debug33");
        writeName.setText(oldName);
        writeProb.setText(oldProb);

        Button butRemove = (Button)dialog.findViewById(R.id.butRemove);


        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElementFromDialog();
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
            table.add((IndividualParticipant)it.next());
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
                else if(type == 2)
                    intent = new Intent(AddParticipants.this, ResultMultipleDraw.class);
                else if(type == 12)
                    intent = new Intent(AddParticipants.this, ResultMultipleDraw.class);
                intent.putExtra("en", table); //second param is Serializable
                startActivity(intent);
            }

        });

    }

    void updateElementListView() {

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box

                IndividualParticipant aux = table.get(position);

                actionUpdateParticipant(aux.getName(), Integer.toString(aux.getProb()), position);

            }
        });
    }

    void getType() {
        Intent prevIntent = getIntent();
        // 11 if it came from Individual, 2 for rand mult, or 12 for balanced mult
        this.type = prevIntent.getIntExtra("intVariableName", 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        lView = (ListView)findViewById(R.id.listView);
        System.out.println("New");

        getType();

        System.out.println("type = " + type);

        actionAddParticipant();
        butDoDrawAction();

        IndividualParticipant p1 = new IndividualParticipant("Bernardo", 3);
        IndividualParticipant p2 = new IndividualParticipant("BFS", 4);
        IndividualParticipant p3 = new IndividualParticipant("Raimunda", 9);
        IndividualParticipant p4 = new IndividualParticipant("Florisvaldo", 1);

        table = new ArrayList<IndividualParticipant>();

        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        loadTable();

        updateElementListView();
    }


}
