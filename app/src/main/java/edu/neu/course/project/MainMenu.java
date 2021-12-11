package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void toPlay(View view) {
        Intent intent = new Intent(MainMenu.this, Play.class);
        startActivity(intent);
    }

    public void toLeaderboard(View view) {
        Intent intent = new Intent(MainMenu.this, LeaderBoard.class);
        startActivity(intent);
    }

    public void toLanguage(View view) {
        Intent intent = new Intent(MainMenu.this, LanguageActivity.class);
        startActivity(intent);
    }

    public void toAboutUs(View view) {
        Intent intent = new Intent(MainMenu.this, AboutUs.class);
        startActivity(intent);
    }
}