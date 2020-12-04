package com.appgate.test.ronyrodriguez;

import android.app.Application;

import com.appgate.test.ronyrodriguez.datasource.local.User;
import com.appgate.test.ronyrodriguez.viewmodel.LoginViewModel;

import org.junit.Test;

public class ViewModelTest {

    Application application;

    @Test
    public void test() {
        LoginViewModel loginViewModel = new LoginViewModel(application);
        User user = new User("appgate@gmail.com", "Prueba1");
        loginViewModel.insert(user);

    }

}
