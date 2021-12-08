package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.neu.course.project.LanguageActivity;
import edu.neu.course.project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void language(View view) {
        Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
        startActivity(intent);
    }

}