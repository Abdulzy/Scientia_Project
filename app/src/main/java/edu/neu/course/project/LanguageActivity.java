package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LanguageActivity extends AppCompatActivity {

    Spinner knownLanguage;
    Spinner learnLanguage;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        knownLanguage = findViewById(R.id.knownLanguage);
        learnLanguage = findViewById(R.id.learnLanguage);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.languages));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        knownLanguage.setAdapter(adapter);
        learnLanguage.setAdapter(adapter);
    }
    public void dash(View view) {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }
}