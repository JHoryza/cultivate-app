package com.example.growapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.growapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNewHabit, btnManageHabits, btnAbout;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewHabit:
                startActivity(new Intent(MainActivity.this, HabitActivity.class));
                break;
            case R.id.btnManageHabits:
                startActivity(new Intent(MainActivity.this, ManageHabitsActivity.class));
                break;
            case R.id.btnAbout:
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("Info","About");
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewHabit = findViewById(R.id.btnNewHabit);
        btnManageHabits = findViewById(R.id.btnManageHabits);
        btnAbout = findViewById(R.id.btnAbout);

        btnNewHabit.setOnClickListener(this);
        btnManageHabits.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmHelp:
                break;
            case R.id.itmAdd:
                startActivity(new Intent(MainActivity.this, HabitActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}