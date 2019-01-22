package com.example.bfs.sorteiosapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ParticipantDataBase db;
    private CheckBox cbSaveData;
    private int type, randomnessParam, numberOfTeams;
    private boolean saveData;

    boolean updateElementFromDialog(int index, int oldId, String prevName, int prevProb, int prevSkill) {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        final EditText writeSkill = (EditText) dialog.findViewById(R.id.writeSkill);
        String name = prevName;
        int prob = prevProb;
        int skill = prevSkill;
        name = writeName.getText().toString();
        if (type == 11) {
            if(!checkRequiredNumberField(writeProb))
                return false;
            prob = Integer.parseInt(writeProb.getText().toString());
        } else if(type == 12) {
            if(!checkRequiredNumberField(writeSkill))
                return false;
            skill = Integer.parseInt(writeSkill.getText().toString());
        }

        if(saveData) {
            boolean inserted = db.updateData(oldId, name, prob, skill);
            if (!inserted)
                Toast.makeText(AddParticipants.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }

        table.set(index, new Participant(name, prob, skill));
        adapter.notifyDataSetChanged();
        return true;
    }

    boolean addElementFromDialog(String prevName, int prevProb, int prevSkill) {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        final EditText writeSkill = (EditText) dialog.findViewById(R.id.writeSkill);
        String name = prevName;
        int prob = prevProb;
        int skill = prevSkill;
        name = writeName.getText().toString();
        if (type == 11) {
            if(!checkRequiredNumberField(writeProb))
                return false;
            prob = Integer.parseInt(writeProb.getText().toString());
        } else if(type == 12) {
            if(!checkRequiredNumberField(writeSkill))
                return false;
            skill = Integer.parseInt(writeSkill.getText().toString());
        }
        table.add(new Participant(name, prob, skill));
        System.out.println("debug");
        if(saveData) {
            System.out.println("debug2");
            boolean inserted = db.insertData(name, prob, skill);
            if (!inserted)
                Toast.makeText(AddParticipants.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    void removeElementFromDialog(int idx) {
        table.remove(idx);
        adapter.notifyDataSetChanged();
    }

    void hideEditTextFromDialog() {
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        final EditText writeSkill = (EditText) dialog.findViewById(R.id.writeSkill);

        if(type < 10) {
            writeProb.setVisibility(View.GONE);
            writeSkill.setVisibility(View.GONE);
        } else if(type == 11) {
            writeSkill.setVisibility(View.GONE);
        } else if(type == 12) {
            writeProb.setVisibility(View.GONE);
        }
    }

    void createDialogAddParticipant() {
        dialog = new Dialog(AddParticipants.this);
        dialog.setTitle("Save Your Name");
        dialog.setContentView(R.layout.dialog_template);
        setCheckBox(dialog);

        hideEditTextFromDialog();

        TextView message = (TextView) dialog.findViewById(R.id.message);
        Button addData = (Button) dialog.findViewById(R.id.butAdd);
        Button butRem = (Button) dialog.findViewById(R.id.butRemove);
        butRem.setVisibility(View.GONE);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addElementFromDialog("", -1, -1))
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    void createDialogTypeOfParticipantToAdd() {
        final Dialog d = new Dialog(AddParticipants.this);
        d.setContentView(R.layout.dialog_type_of_participant_to_add);

        Button butCreateNewPart = (Button) d.findViewById(R.id.butAddNew);
        Button butAddSaved = (Button) d.findViewById(R.id.butAddSaved);
        Button butDone = (Button) d.findViewById(R.id.butDone);

        butCreateNewPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogAddParticipant();
            }
        });

        butAddSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To Add
            }
        });

        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();

    }

    void addParticipantClick() {
        Button butAddPart = (Button)findViewById(R.id.butAddPart);
        butAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTypeOfParticipantToAdd();
            }
        });
    }

    void CheckBoxSaveDataActions(Dialog dialog) {
        dialog.setTitle("Save Data");
    }

    String checkStringEmpty(String x) {
        if(x.equals("-1"))
            return "";
        return x;
    }

    void setCheckBox(Dialog d) {
        CheckBox checkBox = dialog.findViewById(R.id.cbSaveData);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    saveData = true;
                else
                    saveData = false;
            }
        });
    }

    public void actionUpdateParticipant(final int oldId, final String oldName, final String oldProb, final String oldSkill, final int index){

        dialog = new Dialog(AddParticipants.this);

        dialog.setContentView(R.layout.dialog_template);
        TextView txtMessage=(TextView)dialog.findViewById(R.id.message);
        setCheckBox(dialog);
        txtMessage.setText("Update item");

        Button butAdd = (Button)dialog.findViewById(R.id.butAdd);
        butAdd.setText("UPDATE");

        final EditText writeName = (EditText)dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText)dialog.findViewById(R.id.writeProb);
        final EditText writeSkill = (EditText)dialog.findViewById(R.id.writeSkill);

        hideEditTextFromDialog();
        writeName.setText(checkStringEmpty(oldName));
        writeProb.setText(checkStringEmpty(oldProb));
        writeSkill.setText(checkStringEmpty(oldSkill));

        Button butRemove = (Button)dialog.findViewById(R.id.butRemove);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateElementFromDialog(index, oldId, oldName, Integer.parseInt(oldProb), Integer.parseInt(oldSkill)))
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
                if(type == 1 || type == 11) {
                    intent = new Intent(AddParticipants.this, ResultIndividualDraw.class);
                    intent.putExtra("type", type);
                } else {
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

                if(type == 1 || type == 11)
                    actionUpdateParticipant(aux.getId(), aux.getName(), Integer.toString(aux.getProb()), Integer.toString(aux.getSkill()), position);
                else if(type == 2 || type == 12)
                    actionUpdateParticipant(aux.getId(), aux.getName(), Integer.toString(aux.getProb()), Integer.toString(aux.getSkill()), position);

            }
        });
    }

    void getFromLastActivity() {
        this.type = getIntVarFromLastActivity("type");
        this.randomnessParam = getIntVarFromLastActivity("randomnessParam");
        this.numberOfTeams = getIntVarFromLastActivity("numberOfTeams");
    }

    void makeAttributions() {
        db = new ParticipantDataBase(AddParticipants.this);
        lView = (ListView)findViewById(R.id.listView);
    }

    void initVariables() {
        this.saveData = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        makeAttributions();

        System.out.println("New");
        getFromLastActivity();
        System.out.println("type = " + type);

        addParticipantClick();
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
