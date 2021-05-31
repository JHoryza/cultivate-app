package com.example.growapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.growapp.DatabaseHandler;
import com.example.growapp.Habit;
import com.example.growapp.R;
import com.example.growapp.RecyclerViewAdapter;
import com.example.growapp.State;

import org.joda.time.DateTime;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ManageHabitsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvHabit;
    private Button btnBack;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Habit> habits;
    private DatabaseHandler databaseHandler;
    private MenuItem itmAdd;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            startActivity(new Intent(ManageHabitsActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_habits);

        rvHabit = findViewById(R.id.rvHabit);
        btnBack = findViewById(R.id.btnBack);
        itmAdd = findViewById(R.id.itmAdd);

        databaseHandler = new DatabaseHandler(this);

        //TODO: remove test list
        ArrayList<Habit> test = new ArrayList<>();
        test.add(new Habit("Test 1: 1 day", 1, "day(s)", State.WATERED, new DateTime().toString()));
        test.add(new Habit("Test 2: 1 day -1", 1, "day(s)", State.WATERED, new DateTime().minusDays(1).toString()));
        test.add(new Habit("Test 3: 1 day -2", 1, "day(s)", State.WATERED, new DateTime().minusDays(2).toString()));
        test.add(new Habit("Test 4: 1 day -3", 1, "day(s)", State.WATERED, new DateTime().minusDays(3).toString()));
        test.add(new Habit("Test 5: 1 day -4", 1, "day(s)", State.WATERED, new DateTime().minusDays(4).toString()));
        test.add(new Habit("Test 6: 1 day -5", 1, "day(s)", State.WATERED, new DateTime().minusDays(5).toString()));

        //habits = test;
        habits = databaseHandler.getAllHabits();
        recyclerViewAdapter = new RecyclerViewAdapter(this, habits);
        rvHabit.setAdapter(recyclerViewAdapter);
        rvHabit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        btnBack.setOnClickListener(this);
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
                startActivity(new Intent(ManageHabitsActivity.this, HabitActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Habit habit = recyclerViewAdapter.getHabits().get(item.getGroupId());

        switch (item.getTitle().toString()) {
            case "Test":
                //TODO: remove this case
                Toast.makeText(this, "" + habit.getTimestamp().toString(), Toast.LENGTH_SHORT).show();
                return true;
            case "Edit":
                Intent intent = new Intent(ManageHabitsActivity.this, HabitActivity.class);
                intent.putExtra("Habit", habit);
                startActivity(intent);
                return true;
            case "Delete":
                if (databaseHandler.delete(habit)) {
                    recyclerViewAdapter.removeHabit(habit);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
