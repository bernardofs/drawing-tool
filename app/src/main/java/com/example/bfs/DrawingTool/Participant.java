package com.example.bfs.DrawingTool;

import java.io.Serializable;

public class Participant implements Comparable<Participant>, Serializable {

    private int id;
    private String name;
    private int prob;
    private int skill;

    public Participant(String name, int prob, int skill) {
        this.id = -1;
        this.name = name;
        this.prob = prob;
        this.skill = skill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participant(int id, String name, int prob, int skill) {
        this.id = id;
        this.name = name;
        this.prob = prob;
        this.skill = skill;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
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
    public int compareTo(Participant o) {
        return name.compareTo(o.name);
    }
}
