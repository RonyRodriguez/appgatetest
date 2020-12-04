package com.appgate.test.ronyrodriguez.repository.local;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.appgate.test.ronyrodriguez.datasource.local.AppDatabase;
import com.appgate.test.ronyrodriguez.datasource.local.Attempt;
import com.appgate.test.ronyrodriguez.datasource.local.AttemptDao;

import java.util.List;

public class AttemptRepository {

    private AttemptDao attemptDao;

    public AttemptRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        attemptDao = db.attemptDao();
    }

    public LiveData<List<Attempt>> getAttempts(long userId) {
        return attemptDao.getAttempts(userId);
    }

    public LiveData<List<Attempt>> getAttemptsAll() {
        return attemptDao.getAttemptsAll();
    }

    public void insert(Attempt attempt) {
        new AttemptRepository.insertAsyncTask(attemptDao).execute(attempt);
    }

    private static class insertAsyncTask extends AsyncTask<Attempt, Void, Void> {

        private AttemptDao attemptDao;

        insertAsyncTask(AttemptDao dao) {
            attemptDao = dao;
        }

        @Override
        protected Void doInBackground(final Attempt... params) {
            attemptDao.insert(params[0]);
            return null;
        }
    }

}
