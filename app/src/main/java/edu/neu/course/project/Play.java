package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Play extends AppCompatActivity {

    private String currentUser;
    private String currentToken;
    private String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("sender");
        currentToken = intent.getStringExtra("token");
        currentLanguage = intent.getStringExtra("language");
    }

    public void toQuestion(View view) {
        Intent intent = new Intent(Play.this, Question.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        intent.putExtra("language", currentLanguage);
        startActivity(intent);
    }

    public void toAudio(View view) {
        Intent intent = new Intent(Play.this, Audio.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        intent.putExtra("language", currentLanguage);
        startActivity(intent);
    }
}