package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyProgressDialogFragment extends DialogFragment {

    TextView progressDialog;
    DatabaseReference reference_users;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.dialog_fragment, container, false);
        progressDialog = v.findViewById(R.id.progressDialog_id);

//        getParentFragmentManager().setFragmentResultListener("dataFromHome", this, new FragmentResultListener() {
//
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                String prog = result.getString("progressValue");
//                Toast.makeText(getContext(), "value is " + prog, Toast.LENGTH_LONG).show();
//                progressDialog.setText("Progress is " + prog);
//
//            }
//        });


        String progValue = getArguments().getString("progressValue");
//        reference_users = FirebaseDatabase.getInstance().getReference("Users");
        return v;

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = new Bundle();
//        bundle = getArguments();
//        String progValue = this.getArguments().getString("progressValue");
//        if (progValue != null) {
//            Toast.makeText(getContext(), "value is " + progValue, Toast.LENGTH_LONG).show();
//
//            progressDialog.setText("Progress is " + progValue);
//
//        }
//    }

    //    private void getUserLessonData(DatabaseReference databaseReference) {
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                lessonsMap = new HashMap<>();
////                DataSnapshot usersSnapshot = snapshot.child("Users").child(user).child("courses");
//                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    fetchDataLesson(userSnapshot);
//                }
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
//    private void fetchDataLesson(DataSnapshot userSnapshot) {
//
//        Long number_totalLessons = userSnapshot.child("TotalLessons").getValue(Long.class);
//        Long completed = userSnapshot.child("completed").getValue(Long.class);
//        Long progress = userSnapshot.child("progress").getValue(Long.class);
//
//    }
}
