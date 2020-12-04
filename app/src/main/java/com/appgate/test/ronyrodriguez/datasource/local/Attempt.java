package com.appgate.test.ronyrodriguez.datasource.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attempt {
    @PrimaryKey(autoGenerate = true)
    public long attemptsId;
    public long userCreatorId;
    public String date;
    public String isValid;

    public Attempt(long userCreatorId, String date, String isValid) {
        this.userCreatorId = userCreatorId;
        this.date = date;
        this.isValid = isValid;
    }

    public String getDate() {
        return date;
    }

    public String getIsValid() {
        return isValid;
    }
}