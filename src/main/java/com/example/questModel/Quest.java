package com.example.questModel;

import java.io.Serializable;

public class Quest implements Serializable {

    private long id;
    private String shortName;
    private String description;
    private int progressNumber;

    public Quest(long l, String s) {}

    public Quest(long id, String shortName, String description, int progressNumber) {
        this.id = id;
        this.shortName = shortName;
        this.description = description;
        this.progressNumber = progressNumber;
    }

    public long getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public int getProgressNumber() {
        return progressNumber;
    }

}
