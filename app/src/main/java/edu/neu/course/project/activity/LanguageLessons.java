package edu.neu.course.project.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class LanguageLessons extends AppCompatActivity {

    private DatabaseReference reference_users, reference_lessons;
    private Map<String, List<Lesson>> lessonsMap = new HashMap<>();
    private String user = "Meera";
    private String language = "Korean";
    private ArrayList<Lesson> lessonsArray_user = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterClass rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private ImageView image;
    private TextView lesson_txtview;
    private TextView course_progress;
    private TextView course_list;
    private TextView current_course;
    private ImageButton home;
    private ImageButton account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_lessons);
        image = findViewById(R.id.imageView_id);
        lesson_txtview = findViewById(R.id.lessonName_id);
        home = findViewById(R.id.homeButton_id);
        account = findViewById(R.id.profileButton_id);
        current_course = findViewById(R.id.text_course_id);
        course_progress = findViewById(R.id.text_progress_id);
        course_list = findViewById(R.id.text_course_id);
        reference_users = FirebaseDatabase.getInstance().getReference("Users");
        createRecyclerView();
        getData();
        current_course.setText(language);
    }

    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Thread userThread = new Thread(() -> getUsers(databaseReference));
        Thread stickerThread = new Thread(() -> getUserLessonData(databaseReference));
        userThread.start();
//        stickerThread.start();
    }

    private void getUserLessonData(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsMap = new HashMap<>();
                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses");
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    fetchDataLesson(userSnapshot);
                }
                Log.d(TAG, "lessons " + lessonsArray_user);
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
        databaseReference.addValueEventListener(valueEventListener);
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
    
    private void fetchDataLesson(DataSnapshot userSnapshot) {

        Long number_totalLessons = userSnapshot.child("TotalLessons").getValue(Long.class);
        Long completed = userSnapshot.child("completed").getValue(Long.class);
        Long progress = userSnapshot.child("progress").getValue(Long.class);
        if (progress == 100) {

        }
        else {

        }
    }

    private void createRecyclerView() {

        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rcv_user);
        recyclerView.setHasFixedSize(true);
        rviewAdapter = new AdapterClass(lessonsArray_user, LanguageLessons.this);
        //attributions bond to the item has been changed
//        ItemClickListener itemClickListener = position -> {
//            //attributions bond to the item has been changed
//            itemList.get(position).onItemClick(position);
//            rviewAdapter.notifyItemChanged(position);
//        };
//        rviewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }
}