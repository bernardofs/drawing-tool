package com.example.bfs.DrawingTool;

import android.content.Intent;
import android.database.Cursor;
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
import java.util.TreeSet;

public class AddParticipants extends Screen implements Serializable {

    private TreeSet<Integer> idsSavedPart;
    ArrayList<Participant> savedPart;
    private Dialog dialog;
    ListView lView;
    private ArrayList<Participant> table;
    private PartListAdapter adapter;
    private ParticipantDataBase db;
    private CheckBox cbSaveData;
    private int type, randomnessParam, numberOfTeams;
    private boolean saveData;

    boolean validForThisType(Participant p) {

        if(type < 10)
            return (p.getName().equals("") == false);

        if(type == 11)
            return (p.getName().equals("") == false && p.getProb() != -1);

        if(type == 12)
            return (p.getName().equals("") == false && p.getSkill() != -1);

        return false;
    }

    boolean updateElementFromDialog(int index, int oldId, String prevName, int prevProb, int prevSkill) {
        final EditText writeName = (EditText) dialog.findViewById(R.id.writeName);
        final EditText writeProb = (EditText) dialog.findViewById(R.id.writeProb);
        final EditText writeSkill = (EditText) dialog.findViewById(R.id.writeSkill);
        String name = prevName;
        int prob = prevProb;
        int skill = prevSkill;
        name = writeName.getText().toString();
        if (type == 11) {
            if(!checkETisValidNonNegativeNumber(writeProb))
                return false;
            prob = Integer.parseInt(writeProb.getText().toString());
        } else if(type == 12) {
            if(!checkETisValidNonNegativeNumber(writeSkill))
                return false;
            skill = Integer.parseInt(writeSkill.getText().toString());
        }

        int id = oldId;
        if(saveData) {
            if(idsSavedPart.contains(oldId) && oldId != -1) {
                boolean inserted = db.updateData(oldId, name, prob, skill);
                if (!inserted)
                    Toast.makeText(AddParticipants.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            } else {
                id = db.insertData(name, prob, skill);
                if (id == -1)
                    Toast.makeText(AddParticipants.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                else {
                    idsSavedPart.add(id);
                }
            }
        } else {
            id = table.get(index).getId();
            if(idsSavedPart.contains(id))
                idsSavedPart.remove(id);
        }

        table.set(index, new Participant(id, name, prob, skill));
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
            if(!checkETisValidNonNegativeNumber(writeProb))
                return false;
            prob = Integer.parseInt(writeProb.getText().toString());
        } else if(type == 12) {
            if(!checkETisValidNonNegativeNumber(writeSkill))
                return false;
            skill = Integer.parseInt(writeSkill.getText().toString());
        }
        table.add(new Participant(name, prob, skill));
        if(saveData) {
            int id = db.insertData(name, prob, skill);
            if (id == -1)
                Toast.makeText(AddParticipants.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            else {
                idsSavedPart.add(id);
            }

        }
        adapter.notifyDataSetChanged();
        return true;
    }

    void removeElementFromDialog(int idx) {
        int id = table.get(idx).getId();
        if(idsSavedPart.contains(id))
            idsSavedPart.remove(id);

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
        setCheckBox(dialog, false, -1);

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

    void createDialogAddSaved() {

        final Dialog d = new Dialog(AddParticipants.this);
        d.setContentView(R.layout.dialog_show_saved_participants);

        ListView liView = (ListView) d.findViewById(R.id.listView);

        savedPart = new ArrayList<Participant>();

        Cursor ptr = db.getAllData();

        String name;
        int prob, skill, id;
        while(ptr.moveToNext()) {
            id = Integer.parseInt(ptr.getString(0));
            if(idsSavedPart.contains(id) && id != -1)
                continue;
            name = ptr.getString(1);
            prob = Integer.parseInt(ptr.getString(2));
            skill = Integer.parseInt(ptr.getString(3));

            Participant p = new Participant(id, name, prob, skill);

            if(validForThisType(p))
                savedPart.add(p);
        }

        if(savedPart.size() == 0) {
            createErrorDialog("No Saved Participant is Valid");
            return;
        }

        liView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                table.add(savedPart.get(position));
                idsSavedPart.add(savedPart.get(position).getId());
                adapter.notifyDataSetChanged();
                d.dismiss();
            }
        });

        PartListAdapter adapt = new PartListAdapter(this, R.layout.participants_list_view, savedPart, type);
        liView.setAdapter(adapt);

        d.show();
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
                createDialogAddSaved();
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

    String checkStringEmpty(String x) {
        if(x.equals("-1"))
            return "";
        return x;
    }

    void setCheckBox(Dialog d, boolean update, int id) {

        CheckBox checkBox = d.findViewById(R.id.cbSaveData);
        if(update && id != -1) {
            checkBox.setText("Update Saved Data");
        } else {
            checkBox.setText("Save Data");
        }

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
        setCheckBox(dialog, true, oldId);
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

        table = new ArrayList<Participant>();

//        Participant p1 = new Participant("Adolf", 1, 1);
//        Participant p2 = new Participant("Bernard", 3, 3);
//        Participant p3 = new Participant("John", 4, 4);
//        Participant p4 = new Participant("Mary", 15, 15);
//        Participant p5 = new Participant("Aaron", 9, 9);
//        Participant p6 = new Participant("Robert", 1, 1);
//        Participant p7 = new Participant("Simone", 2, 2);
//        Participant p8 = new Participant("Maggie", 5, 5);
//
//        table.add(p1);
//        table.add(p2);
//        table.add(p3);
//        table.add(p4);
//        table.add(p5);
//        table.add(p6);
//        table.add(p7);
//        table.add(p8);

        adapter = new PartListAdapter(this, R.layout.participants_list_view, table, type);
        lView.setAdapter(adapter);
    }

    void butDoDrawAction() {

        Button butDraw = (Button)findViewById(R.id.butDoDraw);
        butDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(table.size() == 0) {
                    createErrorDialog("You must add at least one participant");
                    return;
                } else if(table.size() > 100) {
                    createErrorDialog("Maximum number of participants is 100");
                    return;
                }

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
                        if(numberOfTeams > table.size()) {
                            createErrorDialog("Number of teams must be less or equal than number of participants");
                            return;
                        }
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
        idsSavedPart = new TreeSet<Integer>();

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText("Name");

        TextView tvP_or_S = (TextView) findViewById(R.id.tvP_or_S);

        if(type < 10) {
            tvP_or_S.setVisibility(View.GONE);
        } else if(type == 11)
            tvP_or_S.setText("Probability");
        else if(type == 12)
            tvP_or_S.setText("Skill");
    }

    void initVariables() {
        this.saveData = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        getFromLastActivity();

        makeAttributions();
        initVariables();


        addParticipantClick();
        butDoDrawAction();

        loadTable();

        updateElementListView();
    }


}
