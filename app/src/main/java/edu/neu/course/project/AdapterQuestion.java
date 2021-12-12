package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.neu.course.project.data.Lesson;

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.QuestionViewHolder> {

    Context context;
    String user;
    String lang;
    ArrayList qArray;
    TextView opt1;
    TextView opt2;
    QuestionData item;
    TextView opt3;
    String level;

    public AdapterQuestion(ArrayList<QuestionData> questionsArray, Activity questions, String user, String learningLanguage, String lessonLevel) {
        this.qArray = questionsArray;
        this.context = questions;
        this.user = user;
        this.lang = learningLanguage;
        this.level = lessonLevel;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(user).child("courses").child(lang).child("Lessons").child(level).child("progress").setValue(0);

    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.activity_question_card, parent, false);
        return new AdapterQuestion.QuestionViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        QuestionData currentItem = (QuestionData) qArray.get(position);
        holder.qQuestion.setText(currentItem.getqQuestion());
        holder.option1.setText(currentItem.getOption1());
        holder.option2.setText(currentItem.getOption2());
        holder.option3.setText(currentItem.getOption3());
        int pos = holder.getAdapterPosition();
        QuestionData item = (QuestionData) qArray.get(pos);


        holder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getAns().equals(holder.option1.getText())) {
                    holder.result.setBackgroundColor(Color.GREEN);
                    if (holder.result.getText().equals("Result") || holder.result.getText().equals("INCORRECT")) {
                        fetchProgress(databaseReference, "correct");

                    }
                    holder.result.setText("CORRECT");

                }
                else {
                    holder.result.setBackgroundColor(Color.RED);
                    if (holder.result.getText().equals("CORRECT")) {
                        fetchProgress(databaseReference, "incorrect");
                    }
                    holder.result.setText("INCORRECT");

                }
            }
        });


        holder.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getAns().equals(holder.option2.getText())) {
                    holder.result.setBackgroundColor(Color.GREEN);
                    if (holder.result.getText().equals("Result") || holder.result.getText().equals("INCORRECT")) {
                        fetchProgress(databaseReference, "correct");
                    }
                    holder.result.setText("CORRECT");
                }
                else {
                    holder.result.setBackgroundColor(Color.RED);

                    if (holder.result.getText().equals("CORRECT")) {
                        fetchProgress(databaseReference, "incorrect");
                    }
                    holder.result.setText("INCORRECT");

                }
            }
        });

        holder.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getAns().equals(holder.option3.getText())) {
                    holder.result.setBackgroundColor(Color.GREEN);
                    if (holder.result.getText().equals("Result") || holder.result.getText().equals("INCORRECT")) {
                        fetchProgress(databaseReference, "correct");
                    }
                    holder.result.setText("CORRECT");
                }
                else {
                    holder.result.setBackgroundColor(Color.RED);
                    if (holder.result.getText().equals("CORRECT")) {
                        fetchProgress(databaseReference, "incorrect");
                    }
                    holder.result.setText("INCORRECT");
                }
            }
        });

    }

    private void fetchProgress(DatabaseReference dbRef, String result) {

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses").child(lang).child("Lessons");
                for (DataSnapshot lessons : usersSnapshot.getChildren()) {

                    if(lessons.getKey().equals(level)) {
                        Long progress = lessons.child("progress").getValue(Long.class);
                        if(result.equals("correct")) {
                            progress = progress + 1;

                        }
                        else if (result.equals("incorrect") && progress != 0) {
                            progress = progress - 1;
                        }
                        if(progress >= qArray.size()) {
                            dbRef.child("Users").child(user).child("courses").child(lang)
                                    .child("Lessons").child(level).child("completed").setValue("yes");
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Progress in Course");
                            builder.setMessage("Hey " + user + "! You have completed level " + level +
                                    ". Great work!!!");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            });

                            builder.show();
                        }
                        dbRef.child("Users").child(user).child("courses").child(lang)
                                .child("Lessons").child(level).child("progress").setValue(progress);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, error.getMessage());
            }
        };

        dbRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int getItemCount() {
        return qArray.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView qTitle;
        TextView qQuestion;
        TextView option1;
        TextView option2;
        TextView option3;
        TextView result;


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
//            qTitle = itemView.findViewById(R.id.textView_title);
            qQuestion = itemView.findViewById(R.id.textView_question);
            option1 = itemView.findViewById(R.id.textView_option1);
            option2 = itemView.findViewById(R.id.textView_option2);
            option3 = itemView.findViewById(R.id.textView_option3);
            result = itemView.findViewById(R.id.textView_answer);

        }
    }
}

