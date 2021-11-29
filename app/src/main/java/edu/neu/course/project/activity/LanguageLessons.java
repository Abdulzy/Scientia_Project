package edu.neu.course.project.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class LanguageLessons extends AppCompatActivity {

    private DatabaseReference reference;
    private Map<String, List<Lesson>> lessonsMap = new HashMap<>();
    private String user = "Meera";
    private String lang = "English";
    private ArrayList<Lesson> lessonsArray_user = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterClass rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_lessons);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        createRecyclerView();
        getData();

    }

    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Thread userThread = new Thread(() -> getUsers(databaseReference));
//        Thread stickerThread = new Thread(() -> getStickers(databaseReference));
        userThread.start();
//        stickerThread.start();
    }

    private void getUsers(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsMap = new HashMap<>();
                DataSnapshot usersSnapshot = snapshot.child("Users");
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ArrayList<Lesson> lessonsArray = new ArrayList<>();
                    Lesson db_lesson1  = userSnapshot.child("Lesson1").getValue(Lesson.class);
                    Lesson db_lesson2  = userSnapshot.child("Lesson2").getValue(Lesson.class);
                    Lesson db_lesson3  = userSnapshot.child("Lesson3").getValue(Lesson.class);
                    Lesson db_lesson4  = userSnapshot.child("Lesson4").getValue(Lesson.class);
                    Lesson db_lesson5  = userSnapshot.child("Lesson5").getValue(Lesson.class);
                    Lesson db_lesson6  = userSnapshot.child("Lesson6").getValue(Lesson.class);
                    Lesson db_lesson7  = userSnapshot.child("Lesson7").getValue(Lesson.class);
                    lessonsArray.add(db_lesson1);
                    lessonsArray.add(db_lesson2);
                    lessonsArray.add(db_lesson3);
                    lessonsArray.add(db_lesson4);
                    lessonsArray.add(db_lesson5);
                    lessonsArray.add(db_lesson6);
                    lessonsArray.add(db_lesson7);
//                    lessons.add(userDetails);
                    lessonsMap.put(userSnapshot.getKey(), lessonsArray);
                    String userName = userSnapshot.getKey();
                    if (user.equals(userSnapshot.getKey())) {
                        fetchData(userSnapshot, "Lesson1");
                        fetchData(userSnapshot, "Lesson2");
                        fetchData(userSnapshot, "Lesson3");
                        fetchData(userSnapshot, "Lesson4");
                        fetchData(userSnapshot, "Lesson5");
                        fetchData(userSnapshot, "Lesson6");
                        fetchData(userSnapshot, "Lesson7");
                    }

//                    String lesson_name = userSnapshot.child(user).get
//                    Lesson lesson = new Lesson(userLesson.getLessonName(), userLesson.getLocked(), userLesson.getImageLink());
//                    if (userLesson != null) {
//                        lessonsList.put(user, userLesson);
//                    }
                }
                Log.d(TAG, "lessons " + lessonsArray_user);

//                if (users.containsKey(null)) {
//                    users.remove(null);
//                }
//                spinnerList.addAll(users.keySet());
                rviewAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }


    private void fetchData( DataSnapshot userSnapshot, String lessonNumber) {

        String lessonName = userSnapshot.child(lessonNumber).child("lesson").getValue(String.class);
        Long lockedStatus = userSnapshot.child(lessonNumber).child("locked").getValue(Long.class);
        String imageLink = userSnapshot.child(lessonNumber).child("ImageLink").getValue(String.class);
        Lesson lesson = new Lesson(lessonName, lockedStatus, imageLink);
        if (lessonName != null && lockedStatus != null && imageLink != null) {
            lessonsArray_user.add(lesson);
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