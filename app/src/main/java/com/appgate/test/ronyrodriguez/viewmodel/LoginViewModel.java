package com.appgate.test.ronyrodriguez.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appgate.test.ronyrodriguez.datasource.local.User;
import com.appgate.test.ronyrodriguez.repository.local.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> login(String username, String password) {
        return userRepository.login(username, password);
    }

    public LiveData<User> exists(String username) {
        return userRepository.exists(username);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

}
