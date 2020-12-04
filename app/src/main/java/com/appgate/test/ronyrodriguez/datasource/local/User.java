package com.appgate.test.ronyrodriguez.datasource.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password")
    public String password;

    public User(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;
    }

}
