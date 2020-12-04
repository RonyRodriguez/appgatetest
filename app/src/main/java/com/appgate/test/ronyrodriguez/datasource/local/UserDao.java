package com.appgate.test.ronyrodriguez.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserDao {

    @Transaction
    @Query("SELECT * FROM user WHERE user_name LIKE :userName AND " +
            "password LIKE :password LIMIT 1")
    LiveData<User> login(String userName, String password);

    @Transaction
    @Query("SELECT * FROM user WHERE user_name LIKE :userName LIMIT 1")
    LiveData<User> exists(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM User where uid = :userId")
    public List<UserWithAttempts> getUserWithAttempts(long userId);

}
