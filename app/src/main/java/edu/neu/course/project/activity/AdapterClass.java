package edu.neu.course.project.activity;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.course.project.AdvancedQuestions;
import edu.neu.course.project.AlphabetsQuestions;
import edu.neu.course.project.BasicsQuestions;
import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<Lesson> lessons;
    Context context;
    String user;
    String lang;

    public AdapterClass(ArrayList<Lesson> lessonList, Context activity, String user, String lang) {
        this.lessons = lessonList;
        this.context = activity;
        this.user = user;
        this.lang = lang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_lesson_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {

        Lesson lesson = lessons.get(position);
        Glide.with(context).load(lessons.get(position).getImageLink()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot usersSnapshot = dataSnapshot.child("Users").child(user)
                                .child("courses").child(lang).child("Lessons").child(lesson.lessonName).child("completed");
                        String completed = usersSnapshot.getValue(String.class);
                        if (completed.equals("yes")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Progress in Course");
                            builder.setMessage("Hi " + user + "! You have completed this level already." +
                                    " Do you want to retake the lesson?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (lesson.lessonName.equals("Alphabets")) {
                                        Intent intent = new Intent(context, AlphabetsQuestions.class);
                                        intent.putExtra("sender", user);
                                        intent.putExtra("language", lang);
                                        context.startActivity(intent);
                                    }
                                    else if (lesson.lessonName.equals("Basics")) {
                                        Intent intent = new Intent(context, BasicsQuestions.class);
                                        intent.putExtra("sender", user);
                                        intent.putExtra("language", lang);
                                        context.startActivity(intent);
                                    }
                                    else if (lesson.lessonName.equals("Advanced")) {
                                        Intent intent = new Intent(context, AdvancedQuestions.class);
                                        intent.putExtra("sender", user);
                                        intent.putExtra("language", lang);
                                        context.startActivity(intent);
                                    }

                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                        else {

                            if (lesson.lessonName.equals("Alphabets")) {
                                Intent intent = new Intent(context, AlphabetsQuestions.class);
                                intent.putExtra("sender", user);
                                intent.putExtra("language", lang);
                                context.startActivity(intent);
                            }
                            else if (lesson.lessonName.equals("Basics")) {
                                Intent intent = new Intent(context, BasicsQuestions.class);
                                intent.putExtra("sender", user);
                                intent.putExtra("language", lang);
                                context.startActivity(intent);
                            }
                            else if (lesson.lessonName.equals("Advanced")) {
                                Intent intent = new Intent(context, AlphabetsQuestions.class);
                                intent.putExtra("sender", user);
                                intent.putExtra("language", lang);
                                context.startActivity(intent);
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//
//
//                    }
//                }, 5000);

            }
        });
        holder.text.setText(lesson.getLessonName());
    }


    @Override
    public int getItemCount() {

        return lessons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_id);
            text = itemView.findViewById(R.id.lessonName_id);
        }
    }

}
