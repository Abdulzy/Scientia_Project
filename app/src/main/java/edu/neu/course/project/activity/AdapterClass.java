package edu.neu.course.project.activity;

import android.content.Context;

import android.content.Intent;
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

import edu.neu.course.project.AlphabetsQuestions;
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
                        DataSnapshot usersSnapshot = dataSnapshot.child("Users").child(user).child("courses").child("English").child("Lessons").child(lesson.lessonName);
                        Long progress = usersSnapshot.getValue(Long.class);
                        Toast.makeText(context, "*****" + String.valueOf(progress), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                Intent intent = new Intent(context, AlphabetsQuestions.class);
                context.startActivity(intent);
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
