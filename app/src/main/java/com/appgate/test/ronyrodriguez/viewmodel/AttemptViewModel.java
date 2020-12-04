package com.appgate.test.ronyrodriguez.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appgate.test.ronyrodriguez.datasource.local.Attempt;
import com.appgate.test.ronyrodriguez.repository.local.AttemptRepository;

import java.util.List;

public class AttemptViewModel extends AndroidViewModel {

    private AttemptRepository attemptRepository;

    public AttemptViewModel(Application application) {
        super(application);
        attemptRepository = new AttemptRepository(application);
    }

    public LiveData<List<Attempt>> getAttempts(long userId) {
        return attemptRepository.getAttempts(userId);
    }

    public LiveData<List<Attempt>> getAttemptsAll() {
        return attemptRepository.getAttemptsAll();
    }

    public void insert(Attempt attempt) {
        attemptRepository.insert(attempt);
    }

}
