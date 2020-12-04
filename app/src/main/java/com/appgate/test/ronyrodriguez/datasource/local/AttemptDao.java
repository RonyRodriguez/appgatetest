package com.appgate.test.ronyrodriguez.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AttemptDao {

    @Insert
    long insert(Attempt attempts);

    @Transaction
    @Query("SELECT * FROM attempt WHERE userCreatorId = :userId ")
    LiveData<List<Attempt>> getAttempts(long userId);

    @Transaction
    @Query("SELECT * FROM attempt")
    LiveData<List<Attempt>> getAttemptsAll();

    @Query("DELETE FROM attempt")
    void deleteAll();

}
