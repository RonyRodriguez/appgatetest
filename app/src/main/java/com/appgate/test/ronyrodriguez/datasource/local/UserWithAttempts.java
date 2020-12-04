package com.appgate.test.ronyrodriguez.datasource.local;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithAttempts {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "userCreatorId"
    )
    public List<Attempt> attemptsList;
}
