package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {


    private String currentUser;
    private String currentToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("sender");
        currentToken = intent.getStringExtra("token");
    }


    public void toPlay(View view) {
        Intent intent = new Intent(MainMenu.this, LanguageActivity.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        startActivity(intent);
    }

    public void toLeaderboard(View view) {
        Intent intent = new Intent(MainMenu.this, LeaderBoard.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        startActivity(intent);
    }



    public void toAboutUs(View view) {
        Intent intent = new Intent(MainMenu.this, AboutUs.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        startActivity(intent);
    }
}