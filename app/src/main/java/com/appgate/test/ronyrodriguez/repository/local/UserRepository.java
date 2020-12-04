package com.appgate.test.ronyrodriguez.repository.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.appgate.test.ronyrodriguez.datasource.local.AppDatabase;
import com.appgate.test.ronyrodriguez.datasource.local.User;
import com.appgate.test.ronyrodriguez.datasource.local.UserDao;

public class UserRepository {

    AppDatabase db;
    private UserDao userDao;
    private LiveData<User> userLiveData;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<User> login(String username, String password) {
        return userDao.login(username, password);
    }

    public LiveData<User> exists(String username) {
        return userDao.exists(username);
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long id = userDao.insert(user);
        });
    }
/*
    public void insertOLD(User user) {
        new insertAsyncTask(userDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Long> {

        private UserDao userDao;

        insertAsyncTask(UserDao dao) {
            userDao = dao;
        }

        @Override
        protected Long doInBackground(final User... params) {
            return userDao.insert(params[0]);
        }
      }
 */


}
