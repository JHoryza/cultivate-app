package com.example.growapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Habit> habits;

    public RecyclerViewAdapter(Context context, ArrayList<Habit> habits) {
        this.context = context;
        this.habits = habits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_menu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DatabaseHandler databaseHandler = new DatabaseHandler(context);

        if (advanceState(habits.get(position))) {
            databaseHandler.update(habits.get(position));
        }
        // Set habit name
        holder.txtHabitName.setText(habits.get(position).getName());
        // Set habit plant image
        int stateImage = getStateImage(habits.get(position).getCurrentState());
        holder.imgPlant.setImageResource(stateImage);
        // Set habit state icon
        int stateIcon = getStateIcon(habits.get(position).getCurrentState());
        holder.imgState.setImageResource(stateIcon);
        // Set habit state info text
        String infoText = getInfoText(habits.get(position).getCurrentState());
        holder.txtHabitStateInfo.setText(infoText);
        // Set habit action button state and text
        String actionText = getActionText(habits.get(position).getCurrentState());
        holder.btnHabitAction.setText(actionText);
        if (habits.get(position).getCurrentState() == State.WATERED) {
            holder.btnHabitAction.setEnabled(false);
        } else {
            holder.btnHabitAction.setEnabled(true);
        }
        holder.btnHabitAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trash
                //TODO: test trash button
                if (habits.get(position).getCurrentState() == State.DEAD) {
                    if (databaseHandler.delete(habits.get(position))) {
                        removeHabit(habits.get(position));
                    }
                // Water
                } else {
                    habits.get(position).setCurrentState(State.WATERED);
                    habits.get(position).setTimestamp(new DateTime().toString());
                    databaseHandler.update(habits.get(position));
                    updateHabit(habits.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits.clear();
        this.habits.addAll(habits);
        notifyDataSetChanged();
    }

    public void removeHabit(Habit habit) {
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getId() == habit.getId()) {
                this.habits.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void updateHabit(Habit habit) {
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getId() == habit.getId()) {
                notifyItemChanged(i);
            }
        }
    }

    private boolean advanceState(Habit habit) {
        DateTime timestamp = new DateTime(habit.getTimestamp());
        DateTime currentTime = new DateTime();

        int interval = habit.getInterval();

        switch (habit.getTimeUnit()) {
            case "day(s)":
                interval *= 1;
                break;
            case "week(s)":
                interval *= 7;
                break;
            case "month(s)":
                interval *= 30;
                break;
            case "year(s)":
                interval *= 365;
                break;
            default:
                break;
        }

        int elapsedIntervals = Days.daysBetween(timestamp, currentTime).getDays() / interval;

        if (elapsedIntervals >= interval) {
            if (elapsedIntervals > 3) elapsedIntervals = 3;
            habit.setCurrentState(elapsedIntervals);
            return true;
        }
        return false;
    }

    private int getStateImage(int state) {
        switch (state) {
            case State.NEEDS_WATER:
                return R.drawable.grow_state_1;
            case State.WILTED:
                return R.drawable.grow_state_2;
            case State.DEAD:
                return R.drawable.grow_state_3;
            case State.WATERED:
            default:
                return R.drawable.grow_state_0;
        }
    }

    private int getStateIcon(int state) {
        switch (state) {
            case State.NEEDS_WATER:
            case State.WILTED:
                return R.drawable.ic_warning;
            case State.DEAD:
                return R.drawable.ic_error;
            case State.WATERED:
            default:
                return R.drawable.ic_checkmark;
        }
    }

    private String getInfoText(int state) {
        switch (state) {
            case State.WATERED:
                return "All is good! Keep it up!";
            case State.NEEDS_WATER:
                return "Your plant could use some attention!";
            case State.WILTED:
                return "Your plant is desperate for water!";
            case State.DEAD:
                return "Oh dear, Your plant has died!";
            default:
                return "";
        }
    }

    private String getActionText(int state) {
        switch (state) {
            case State.DEAD:
                return "Trash";
            case State.NEEDS_WATER:
            case State.WILTED:
            case State.WATERED:
            default:
                return "Water";
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private CardView itemHabit;
        private TextView txtHabitName, txtHabitStateInfo;
        private ImageView imgPlant, imgState;
        private Button btnHabitAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemHabit = itemView.findViewById(R.id.itemHabit);
            txtHabitName = itemView.findViewById(R.id.txtHabitName);
            txtHabitStateInfo = itemView.findViewById(R.id.txtHabitInfo);
            imgPlant = itemView.findViewById(R.id.imgPlant);
            imgState = itemView.findViewById(R.id.imgState);
            btnHabitAction = itemView.findViewById(R.id.btnHabitAction);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), view.getId(), 0, "Edit");
            contextMenu.add(this.getAdapterPosition(), view.getId(), 1, "Delete");
            contextMenu.add(this.getAdapterPosition(), view.getId(), 2, "Test");
        }
    }
}