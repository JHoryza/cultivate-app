package com.example.growapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.growapp.DatabaseHandler;
import com.example.growapp.Habit;
import com.example.growapp.R;
import com.example.growapp.State;

import org.joda.time.DateTime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HabitActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtHabitHeader;
    private EditText edtTxtHabitName, edtTxtInterval;
    private Button btnSubmitHabit, btnCancel;
    private Spinner sprInterval;
    private Habit habit;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmitHabit:
                //TODO: handle invalid inputs
                DatabaseHandler databaseHandler = new DatabaseHandler(HabitActivity.this);
                if (habit == null) {
                    Habit newHabit = new Habit(
                            edtTxtHabitName.getText().toString(),
                            Integer.parseInt(edtTxtInterval.getText().toString()),
                            sprInterval.getSelectedItem().toString(),
                            State.WATERED,
                            new DateTime().toString()
                    );
                    databaseHandler.create(newHabit);
                } else {
                    //TODO: implement proper updating
                    habit.setName(edtTxtHabitName.getText().toString());
                    habit.setInterval(Integer.parseInt(edtTxtInterval.getText().toString()));
                    habit.setTimeUnit(sprInterval.getSelectedItem().toString());
                    habit.setCurrentState(State.WATERED);
                    databaseHandler.update(habit);
                    startActivity(new Intent(HabitActivity.this, ManageHabitsActivity.class));
                }
                break;
            case R.id.btnCancel:
                startActivity(new Intent(HabitActivity.this, MainActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        txtHabitHeader = findViewById(R.id.txtHabitHeader);
        edtTxtHabitName = findViewById(R.id.edtTxtHabitName);
        edtTxtInterval = findViewById(R.id.edtTxtInterval);
        sprInterval = findViewById(R.id.sprInterval);
        btnSubmitHabit = findViewById(R.id.btnSubmitHabit);
        btnCancel = findViewById(R.id.btnCancel);

        ArrayAdapter<String> intervalAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.intervals)
        );
        sprInterval.setAdapter(intervalAdapter);

        btnSubmitHabit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        habit = (Habit) getIntent().getSerializableExtra("Habit");
        if (habit != null) {
            txtHabitHeader.setText("Edit Habit");
            edtTxtHabitName.setText(habit.getName());
            edtTxtInterval.setText(String.valueOf(habit.getInterval()).toString());
            btnSubmitHabit.setText("Save");
            btnCancel.setVisibility(View.GONE);
        }
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
                startActivity(new Intent(HabitActivity.this, HabitActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
