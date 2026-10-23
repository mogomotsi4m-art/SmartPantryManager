package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_settings);
        SafeArea.apply(this);
        android.content.SharedPreferences p = getSharedPreferences("pantry_settings", MODE_PRIVATE);
        Spinner s = findViewById(R.id.unitSpinner);
        CheckBox c = findViewById(R.id.expiryCheck);
        String[] opts = {"Metric (g, kg, ml, l)", "Count (pieces)", "Show original units"};
        s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opts));
        s.setSelection(p.getInt("unit_choice", 0));
        c.setChecked(p.getBoolean("expiry_alerts", true));
        findViewById(R.id.saveSettings).setOnClickListener(v -> {
            p.edit().putInt("unit_choice", s.getSelectedItemPosition()).putBoolean("expiry_alerts", c.isChecked()).apply();
            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.settingsBack).setOnClickListener(v -> finish());
    }
}