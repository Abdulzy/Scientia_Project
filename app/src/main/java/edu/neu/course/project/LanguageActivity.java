package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LanguageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String currentUser;
    private String currentToken;
    ArrayAdapter<String> adapter;
    private String selectedLearnLanguage;
    private Spinner spinner2;
    private Map<String, Long> ProgressMap;
    private Map<String, String> completedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        spinner2 = findViewById(R.id.learnLanguage);
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("sender");
        currentToken = intent.getStringExtra("token");
        adapter = new ArrayAdapter<>(LanguageActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.languages));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
    }

    public void toGame(View view) {
        Intent intent = new Intent(LanguageActivity.this, Play.class);
        intent.putExtra("sender", currentUser);
        intent.putExtra("token", currentToken);
        intent.putExtra("language", selectedLearnLanguage);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.learnLanguage) {
             selectedLearnLanguage = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "You have selected " + selectedLearnLanguage, Toast.LENGTH_SHORT).show();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i(TAG, "inside the listener");
                    if (!snapshot.hasChild("courses")){
                        Progress prog = new Progress(0L, "no");
                        reference.child(currentUser)
                                .child("courses").child(selectedLearnLanguage).child("Lessons").child("Alphabets").setValue(prog);
                        reference.child(currentUser)
                                .child("courses").child(selectedLearnLanguage).child("Lessons").child("Advanced").setValue(prog);
                        reference.child(currentUser)
                                .child("courses").child(selectedLearnLanguage).child("Lessons").child("Basics").setValue(prog);

                    }else{
                        Toast.makeText(getApplicationContext(), "Username is incorrect! ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

//    private Long getProgress(FirebaseDatabase db) {
//        String progress;
//        db.getReference("Users");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                DataSnapshot usersSnapshot = snapshot.child("Users").child(currentUser).
//                        child("courses").child(selectedLearnLanguage).child("Lessons");
//                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    if (userSnapshot != null) {
//                        fetchProgress(userSnapshot);
//                    }
//                }
//            }
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i(TAG, error.getMessage());
//            }
//        };
//        return null;
//    }

//    private void fetchProgress(DataSnapshot userSnapshot) {
//        if (userSnapshot.getKey().equals("Alphabet")) {
//            Long progress = userSnapshot.child("Alphabets").child("progress").getValue(Long.class);
//            ProgressMap.put("Alphabets", progress);
//            Log.d("Tag", "progress" + progress);
//        }
//        if (userSnapshot.getKey().equals("Advanced")) {
//            Long progress = userSnapshot.child("Advanced").child("progress").getValue(Long.class);
//            ProgressMap.put("Alphabets", progress);
//
//        }
//        if (userSnapshot.getKey().equals("Basics")) {
//            Long progress = userSnapshot.child("Basics").child("progress").getValue(Long.class);
//            ProgressMap.put("Alphabets", progress);
//
//        }
//    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}