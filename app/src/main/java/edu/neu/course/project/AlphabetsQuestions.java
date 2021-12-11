package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AlphabetsQuestions extends AppCompatActivity {

    private String user = "Meera";
    private String learningLanguage = "Russian";
    private String level = "Alphabets";
    private ArrayList<QuestionData> questionsArray = new ArrayList<>();
    private HashMap<String, QuestionData> questionsMap;
    private String progressValue;
    private RecyclerView rview;
    private AdapterQuestion rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private TextView option1;
    private TextView option2;
    private TextView option3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets_questions);
        rLayoutManger = new LinearLayoutManager(this);
        rview = findViewById(R.id.rcv_qns);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(rLayoutManger);
        rviewAdapter = new AdapterQuestion(questionsArray, this, user, learningLanguage, level);
        rview.setAdapter(rviewAdapter);
        rview.setLayoutManager(rLayoutManger);
        getData();

    }



    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        Thread userThread = new Thread(() -> getQuestions(databaseReference));
        Thread questionDataThread = new Thread(() -> getQuestionData(databaseReference));
//        userThread.start();
        questionDataThread.start();
    }

    private void getQuestionData(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsMap = new HashMap<>();

                DataSnapshot usersSnapshot = snapshot.child("Lessons").child(learningLanguage);
                for (DataSnapshot lessons : usersSnapshot.getChildren()) {

                    if (lessons.child("lesson").getValue(String.class).equals("Alphabets")) {

                        for (DataSnapshot question : lessons.child("Questions").getChildren()) {
                            fetchData(question);
                            Log.d("Tag", "Questions "+ questionsArray);
                        }
                    }
                }
                rviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }


    private void fetchData(DataSnapshot question) {

        String q = question.child("image").getValue(String.class);
        String ans = question.child("answer").getValue(String.class);
        String option1 = question.child("option1").getValue(String.class);
        String option2 = question.child("option2").getValue(String.class);
        String option3 = question.child("option3").getValue(String.class);
        QuestionData qd = new QuestionData(q, ans, option1, option2, option3);
        questionsArray.add(qd);

    }

    private void getQuestions(DatabaseReference databaseReference) {

    }
}