package edu.neu.course.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;

import edu.neu.course.project.R;
import edu.neu.course.project.data.Lesson;

public class LessonRecyclerView extends AppCompatActivity {

    private ArrayList<Lesson> lessonList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_recycler_view);
    }
}