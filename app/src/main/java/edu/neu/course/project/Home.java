//package edu.neu.course.project;
//
//import static android.content.ContentValues.TAG;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import edu.neu.course.project.activity.AdapterClass;
//import edu.neu.course.project.data.Lesson;
//
//public class Home extends Fragment {
//
//
//    View v;
//    RecyclerView rview;
//    private DatabaseReference reference_users, reference_lessons;
//    private Map<String, List<Lesson>> lessonsMap = new HashMap<>();
//    private String user = "Meera";
//    private String language = "Korean";
//    private ArrayList<Lesson> lessonsArray_user = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private AdapterClass rviewAdapter;
//    private RecyclerView.LayoutManager rLayoutManger;
//    private ImageView image;
//    private TextView lesson_txtview;
//    private TextView course_list;
//    private TextView current_course;
//    private ImageButton home;
//    private ImageButton account;
//    private Map<String, String> languageProgress_map;
//
//
//    public Home() {
//        // Required empty public constructor
//    }
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        v = inflater.inflate(R.layout.fragment_home, container, false);
//        rLayoutManger = new LinearLayoutManager(this.getContext());
//        languageProgress_map = new HashMap<>();
//        rview = v.findViewById(R.id.rcv_user);
//        rview.setHasFixedSize(true);
//        rview.setLayoutManager(rLayoutManger);
//        rviewAdapter = new AdapterClass(lessonsArray_user, getContext());
//        rview.setAdapter(rviewAdapter);
//        rview.setLayoutManager(rLayoutManger);
//        return v;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        reference_users = FirebaseDatabase.getInstance().getReference("Users");
//        getData();
//    }
//
//    private void getData() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        Thread userThread = new Thread(() -> getUsers(databaseReference));
//        Thread lessonDataThread = new Thread(() -> getUserLessonData(databaseReference));
//        userThread.start();
//        lessonDataThread.start();
//    }
//
//    private void getUserLessonData(DatabaseReference databaseReference) {
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                lessonsMap = new HashMap<>();
//                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses").child(language);
//                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    fetchDataLesson(userSnapshot);
//
//                }
//                String progressValue = languageProgress_map.get("progress");
//                MyProgressDialogFragment frag = new MyProgressDialogFragment();
////                Intent intent1 = new Intent(getContext(), MyProgressDialogFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("progressValue", progressValue);
//                bundle.putString("username", user);
//                frag.setArguments(bundle);
////                getParentFragmentManager().setFragmentResult("dataFromHome", bundle);
//                Log.d(TAG, "lessons " + lessonsArray_user);
//                rviewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i(TAG, error.getMessage());
//            }
//        };
//        databaseReference.addValueEventListener(valueEventListener);
//    }
//
//    private void getUsers(DatabaseReference databaseReference) {
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                lessonsMap = new HashMap<>();
//                DataSnapshot usersSnapshot = snapshot.child("Lessons").child(language);
//                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    ArrayList<Lesson> lessonsArray = new ArrayList<>();
//                    String lessonNum = userSnapshot.getKey();
//                    fetchData(userSnapshot);
//                }
//                Log.d(TAG, "lessons " + lessonsArray_user);
//                rviewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i(TAG, error.getMessage());
//            }
//        };
//        databaseReference.addValueEventListener(valueEventListener);
//    }
//
//    private void fetchData( DataSnapshot userSnapshot) {
//
//        String lessonName = userSnapshot.child("lesson").getValue(String.class);
//        Long lockStatus = userSnapshot.child("locked").getValue(Long.class);
//        String imageLink = userSnapshot.child("ImageLink").getValue(String.class);
//        Lesson lesson = new Lesson(lessonName, lockStatus, imageLink);
//        if (lessonName != null && lockStatus != null && imageLink != null) {
//            lessonsArray_user.add(lesson);
//        }
//    }
//
//    private void fetchDataLesson(DataSnapshot userSnapshot) {
//
//        String keyValue = userSnapshot.getKey();
//        Long value = userSnapshot.getValue(Long.class);
////        Toast.makeText(getContext(), "key is " + keyValue + " " + value, Toast.LENGTH_LONG).show();
//        languageProgress_map.put(keyValue, String.valueOf(value));
//
//    }
//}