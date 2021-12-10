package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LanguageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner knownLanguage;
    Spinner learnLanguage;
    ArrayAdapter<String> adapter;
    private String selectedKnownLanguage;
    private String selectedLearnLanguage;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        spinner1 = findViewById(R.id.knownLanguage);
        spinner2 = findViewById(R.id.learnLanguage);
        adapter = new ArrayAdapter<String>(LanguageActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.languages));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
    }

    public void dash(View view) {
        this.finish();
//        Intent intent = new Intent(this, DashBoard.class);
//        intent.putExtra("user", "Meera");
//        intent.putExtra("chosenKnownLanguage", selectedKnownLanguage);
//        intent.putExtra("chosenLearnLanguage", selectedKnownLanguage);
//        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.knownLanguage) {
             selectedKnownLanguage = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "&&&& " + selectedKnownLanguage, Toast.LENGTH_SHORT).show();
        }

        if (adapterView.getId() == R.id.learnLanguage) {
             selectedLearnLanguage = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "&&&& " + selectedLearnLanguage, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}