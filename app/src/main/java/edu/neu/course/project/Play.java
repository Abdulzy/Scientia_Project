package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Play extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void toQuestion(View view) {
        Intent intent = new Intent(Play.this, Question.class);
        startActivity(intent);
    }

    public void toAudio(View view) {
        Intent intent = new Intent(Play.this, Audio.class);
        startActivity(intent);
    }
}