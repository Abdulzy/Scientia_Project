package edu.neu.course.project.activity;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.neu.course.project.Home;
import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<Lesson> lessons;
    Context context;
//    private ItemClickListener listener;

//    public AdapterClass(ArrayList<Lesson> lessonList, LanguageLessons activity) {
//        this.lessons = lessonList;
//        this.context = activity;
//    }

    public AdapterClass(ArrayList<Lesson> lessonList, Context activity) {
        this.lessons = lessonList;
        this.context = activity;
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
//        Picasso.with(context).load(lesson.getImageLink()).into(holder.image);
        Glide.with(context).load(lessons.get(position).getImageLink()).into(holder.image);
        Log.d("TAG", "image link is " + lesson.getImageLink());
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
