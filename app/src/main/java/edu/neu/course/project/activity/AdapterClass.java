package edu.neu.course.project.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<Lesson> lessons;
    Context context;
//    private ItemClickListener listener;

    public AdapterClass(ArrayList<Lesson> lessonList, LanguageLessons activity) {
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
        holder.text.setText(lesson.getLessonName());

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        EditText text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_id);
            text = itemView.findViewById(R.id.lessonName_id);
        }
    }
}
