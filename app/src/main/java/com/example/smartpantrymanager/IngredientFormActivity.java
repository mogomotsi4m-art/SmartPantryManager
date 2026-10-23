package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class IngredientFormActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText name, quantity, unit, expiry;
    long id;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_ingredient_form);
        SafeArea.apply(this);
        db = new DatabaseHelper(this);
        name = findViewById(R.id.nameInput);
        quantity = findViewById(R.id.quantityInput);
        unit = findViewById(R.id.unitInput);
        expiry = findViewById(R.id.expiryInput);
        id = getIntent().getLongExtra("id", 0);
        if (id > 0) {
            ((TextView) findViewById(R.id.formTitle)).setText("Edit ingredient");
            name.setText(getIntent().getStringExtra("name"));
            quantity.setText("" + getIntent().getDoubleExtra("quantity", 0));
            unit.setText(getIntent().getStringExtra("unit"));
            expiry.setText(getIntent().getStringExtra("expiry"));
        }
        findViewById(R.id.saveButton).setOnClickListener(v -> save());
        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
    }

    void save() {
        String n = name.getText().toString().trim(), q = quantity.getText().toString().trim(), u = unit.getText().toString().trim();
        if (n.isEmpty()) {
            name.setError("Enter a name");
            return;
        }
        if (q.isEmpty()) {
            quantity.setError("Enter a quantity");
            return;
        }
        if (u.isEmpty()) {
            unit.setError("Enter a unit");
            return;
        }
        double x;
        try {
            x = Double.parseDouble(q);
        } catch (Exception e) {
            quantity.setError("Enter a valid number");
            return;
        }
        if (!Double.isFinite(x) || x <= 0) {
            quantity.setError("Quantity must be greater than zero");
            return;
        }
        if (db.saveIngredient(new PantryIngredient(id, n, x, u, expiry.getText().toString().trim())) >= 0) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(this, "Save failed", Toast.LENGTH_LONG).show();
    }
}