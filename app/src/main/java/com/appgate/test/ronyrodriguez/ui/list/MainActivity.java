package com.appgate.test.ronyrodriguez.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appgate.test.ronyrodriguez.R;
import com.appgate.test.ronyrodriguez.datasource.local.Attempt;
import com.appgate.test.ronyrodriguez.ui.login.LoginActivity;
import com.appgate.test.ronyrodriguez.viewmodel.AttemptViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AttemptViewModel attemptViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        long userId = intent.getLongExtra(LoginActivity.USER_ID, 0);

        attemptViewModel = new ViewModelProvider(this).get(AttemptViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        AttemptListAdapter adapter = new AttemptListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        attemptViewModel.getAttempts(userId).observe(this, new Observer<List<Attempt>>() {
            @Override
            public void onChanged(@Nullable final List<Attempt> attemptList) {
                adapter.setAttempts(attemptList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}