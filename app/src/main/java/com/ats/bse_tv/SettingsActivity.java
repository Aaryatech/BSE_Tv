package com.ats.bse_tv;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn5, btn6, btn7, btn8, btn9, btn10, btnBack, btn11, btn12, btn13, btn14, btn15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn10 = findViewById(R.id.btn_10);
        btn11 = findViewById(R.id.btn_11);
        btn12 = findViewById(R.id.btn_12);
        btn13 = findViewById(R.id.btn_13);
        btn14 = findViewById(R.id.btn_14);
        btn15 = findViewById(R.id.btn_15);
        btnBack = findViewById(R.id.btn_Back);

        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btnBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        SharedPreferences pref = getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (view.getId() == R.id.btn_5) {
            editor.putInt("RowCount", 5);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_6) {
            editor.putInt("RowCount", 6);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_7) {
            editor.putInt("RowCount", 7);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_8) {
            editor.putInt("RowCount", 8);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_9) {
            editor.putInt("RowCount", 9);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_10) {
            editor.putInt("RowCount", 10);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_11) {
            editor.putInt("RowCount", 11);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_12) {
            editor.putInt("RowCount", 12);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_13) {
            editor.putInt("RowCount", 12);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_14) {
            editor.putInt("RowCount", 14);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_15) {
            editor.putInt("RowCount", 15);
            editor.apply();
            finish();
        } else if (view.getId() == R.id.btn_Back) {
            finish();
        }
    }
}
