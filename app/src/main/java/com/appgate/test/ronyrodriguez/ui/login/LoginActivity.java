package com.appgate.test.ronyrodriguez.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appgate.test.ronyrodriguez.R;
import com.appgate.test.ronyrodriguez.datasource.local.Attempt;
import com.appgate.test.ronyrodriguez.datasource.local.User;
import com.appgate.test.ronyrodriguez.datasource.remote.LocationService;
import com.appgate.test.ronyrodriguez.datasource.remote.Timezone;
import com.appgate.test.ronyrodriguez.tools.UITools;
import com.appgate.test.ronyrodriguez.ui.list.MainActivity;
import com.appgate.test.ronyrodriguez.viewmodel.AttemptViewModel;
import com.appgate.test.ronyrodriguez.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements LocationListener, LocationService.RemoteListener {

    public final Activity activityCompat = this;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String USER_ID = "userId";
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;
    ProgressBar loadingProgressBar;

    Timezone timezone;
    LoginViewModel loginViewModel;
    AttemptViewModel attemptViewModel;
    LocationService locationService;

    boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        attemptViewModel = new ViewModelProvider(this).get(AttemptViewModel.class);
        locationService = new LocationService();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);

        loadingProgressBar = findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.GONE);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        if (checkLocationPermission()) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
            requestLocation();
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                    requestLocation();
                }
                return;
            }
        }
    }

    private void requestLocation() {
        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationService.callService(this, location.getLatitude(), location.getLongitude());
    }

    final Observer<User> loginObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            registerButton.setEnabled(true);
            if (user != null) {
                if (isLogin) {
                    Attempt attempt = new Attempt(user.uid, timezone.getTime(), "is valid");
                    addAttempt(attempt);
                    gotoActivityList(user.uid);
                } else {
                    Toast.makeText(activityCompat, "User inserted", Toast.LENGTH_LONG).show();
                }
            } else {
                if (isLogin) {
                    Toast.makeText(activityCompat, "The user does not exist or invalid password", Toast.LENGTH_LONG).show();
                    exists(usernameEditText.getText().toString());
                }
            }
        }
    };


    final Observer<User> userExistsObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            if (user != null) {
                Attempt attempt = new Attempt(user.uid, timezone.getTime(), "is not valid");
                addAttempt(attempt);
            } else {
                loadingProgressBar.setVisibility(View.GONE);
            }
        }
    };

    public void login() {
        isLogin = true;
        loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString()).observe(this, loginObserver);
    }

    public void register() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        isLogin = false;
        boolean isValid = false;

        if (UITools.isValidEmail(username)) {
            isValid = true;
        }
        if (isValid && password.length() >= 8 && UITools.isValidPassword(password)) {
            isValid = true;
        }
        if (isValid) {
            User user = new User(username, password);
            loginViewModel.insert(user);
            usernameEditText.setText("");
            passwordEditText.setText("");
        } else {
            Toast.makeText(activityCompat, "Email or Password are invalid", Toast.LENGTH_LONG).show();
        }
    }

    public void exists(String userName) {
        loginViewModel.exists(userName).observe(this, userExistsObserver);
    }

    public void addAttempt(Attempt attempt) {
        attemptViewModel.insert(attempt);
    }

    @Override
    public void onGetLocation(Timezone timezone) {
        loadingProgressBar.setVisibility(View.GONE);
        this.timezone = timezone;
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
    }

    private void gotoActivityList(Long id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_ID, id);
        startActivity(intent);
    }

}