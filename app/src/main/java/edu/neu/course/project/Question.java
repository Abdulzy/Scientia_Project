package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.course.project.activity.AdapterClass;
import edu.neu.course.project.data.Lesson;

public class Question extends AppCompatActivity {

    RecyclerView rview;
    private DatabaseReference reference_users, reference_lessons;
    private Map<String, List<Lesson>> lessonsMap = new HashMap<>();
    private String user;
    private String token;
    private String language;
    private ArrayList<Lesson> lessonsArray_user = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterClass rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private Map<String, String> languageCompleted_map;
    private TextView foreignLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        rLayoutManger = new LinearLayoutManager(this);
        languageCompleted_map = new HashMap<>();
        rview = findViewById(R.id.rcv_user);
        rview.setHasFixedSize(true);
        Intent intent = getIntent();
        user = intent.getStringExtra("sender");
        language = intent.getStringExtra("language");
        token = intent.getStringExtra("token");
        rview.setLayoutManager(rLayoutManger);
        rviewAdapter = new AdapterClass(lessonsArray_user, this, user, language);
        rview.setAdapter(rviewAdapter);
        rview.setLayoutManager(rLayoutManger);
        reference_users = FirebaseDatabase.getInstance().getReference("Users");
        foreignLanguage = findViewById(R.id.language_id);
        foreignLanguage.setText(language);
        getData();
    }

    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Thread userThread = new Thread(() -> getUsers(databaseReference));
        Thread lessonDataThread = new Thread(() -> getUserLessonData(databaseReference));
        userThread.start();
        lessonDataThread.start();
    }


    private void getUserLessonData(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsMap = new HashMap<>();
                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses").child(language).child("Lessons");
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    fetchDataLesson(userSnapshot);
                }
                String completed = languageCompleted_map.get("completed");
                rviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void getUsers(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsMap = new HashMap<>();
                DataSnapshot usersSnapshot = snapshot.child("Lessons").child(language);
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ArrayList<Lesson> lessonsArray = new ArrayList<>();
                    String lessonNum = userSnapshot.getKey();
                    fetchData(userSnapshot);
                }
                Log.d(TAG, "lessons " + lessonsArray_user);
                rviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    private void fetchData( DataSnapshot userSnapshot) {

        String lessonName = userSnapshot.child("lesson").getValue(String.class);
        Long lockStatus = userSnapshot.child("locked").getValue(Long.class);
        String imageLink = userSnapshot.child("ImageLink").getValue(String.class);
        Lesson lesson = new Lesson(lessonName, lockStatus, imageLink);
        if (lessonName != null && lockStatus != null && imageLink != null) {
            lessonsArray_user.add(lesson);
        }
    }
//
//    private void fetchDataLesson(DataSnapshot userSnapshot) {
//
//        Progress progress = userSnapshot.getValue(Progress.class);
//        languageCompleted_map.put("completed", progress.getCompleted());
//
//    }
}