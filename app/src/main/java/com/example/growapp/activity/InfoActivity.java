package com.example.growapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growapp.R;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtInfoHeader;
    private Button btnBack;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txtInfoHeader = findViewById(R.id.txtInfoHeader);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);

        String info = getIntent().getExtras().get("Info").toString();

        switch (info) {
            case "About":
                txtInfoHeader.setText("About");
                break;
            case "Help":
                txtInfoHeader.setText("Help");
                break;
                default:
            break;
        }
    }
}
