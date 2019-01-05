package com.example.bfs.sorteiosapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class IndividualParticipant implements Comparable<IndividualParticipant>, Serializable {

    private String name;
    private int prob;

    public IndividualParticipant(String name, int prob) {
        this.name = name;
        this.prob = prob;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProb() {
        return prob;
    }

    public void setProb(int prob) {
        this.prob = prob;
    }

    @Override
    public int compareTo(IndividualParticipant o) {
        return name.compareTo(o.name);
    }
}
