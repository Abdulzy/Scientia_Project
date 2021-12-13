package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvancedQuestions extends AppCompatActivity {

    private String user;
    private String learningLanguage;
    private String level = "Advanced";
    private ArrayList<QuestionData> questionsArray = new ArrayList<>();
    private Map<String, QuestionData> questionsMap;
    private RecyclerView rview;
    private AdapterQuestion rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private TextView title;
    private TextView question;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets_questions);
        rLayoutManger = new LinearLayoutManager(this);
        question = findViewById(R.id.question_id);
        Intent intent = getIntent();
        user = intent.getStringExtra("sender");
        learningLanguage = intent.getStringExtra("language");
        rview = findViewById(R.id.rcv_qns);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(rLayoutManger);
        rviewAdapter = new AdapterQuestion(questionsArray, this, user, learningLanguage, level);
        rview.setAdapter(rviewAdapter);
        rview.setLayoutManager(rLayoutManger);
        question.setText("Click the right option for the word");
        getData();

    }

    private void getData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Thread questionDataThread = new Thread(() -> getQuestionData(databaseReference));
        questionDataThread.start();
    }

    private void getQuestionData(DatabaseReference databaseReference) {

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsMap = new HashMap<>();

                DataSnapshot usersSnapshot = snapshot.child("Lessons").child(learningLanguage);
                for (DataSnapshot lessons : usersSnapshot.getChildren()) {

                    if (lessons.child("lesson").getValue(String.class).equals("Advanced")) {
                        for (DataSnapshot question : lessons.child("Questions").getChildren()) {
                            fetchData(question);
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
}
