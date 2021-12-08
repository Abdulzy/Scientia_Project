package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity {

    private TextView language;
    private Map<String, String> languageProgress_map;
    private String user = "Meera";
    private String lang = "Korean";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        language = findViewById(R.id.language_id);
        languageProgress_map = new HashMap<>();
        language.setText("Korean");
        //NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        //NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeScreen, R.id.social, R.id.settings)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    public void clickProgress(View v) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        getUsersLessonData(databaseReference);

    }


    private void getUsersLessonData(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses").child(lang);
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    fetchDataLesson(userSnapshot);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                builder.setTitle("Progress in Course");
                String progressValue = languageProgress_map.get("progress");


                builder.setMessage("Progress is " + progressValue);
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });
                builder.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }


    private void fetchDataLesson(DataSnapshot userSnapshot) {

        String keyValue = userSnapshot.getKey();
        Long value = userSnapshot.getValue(Long.class);
        Toast.makeText(this, "key *****is " + keyValue + " " + value, Toast.LENGTH_LONG).show();
        languageProgress_map.put(keyValue, String.valueOf(value));
    }

    public void clickLogout(View v) {

        logout(this);
    }

    private void logout(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

}














































