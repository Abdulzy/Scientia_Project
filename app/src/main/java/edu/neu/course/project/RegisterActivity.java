package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etLoginName;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etLoginName = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this::createUser);

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void createUser(View view) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        String userName = etLoginName.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            etLoginName.setError("Email cannot be empty");
            etLoginName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        }
        else{
            ValueEventListener ve = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i(TAG, "inside the listener");
                    if (!snapshot.hasChild(userName)){
                        createAccount(view);
                        Intent intent = new Intent(RegisterActivity.this, MainMenu.class);
                        intent.putExtra("sender", userName);
                        startActivity(intent);
                        Log.i(TAG, "has the child");
                    }else{
                        Toast.makeText(getApplicationContext(), "Username already exist! ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            };
            reference.addListenerForSingleValueEvent(ve);
        }
    }

    private void createAccount(View view) {
        String userName = etLoginName.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        User user = new User(userName, password);

        // TODO: Functionality add on: check if user with such name already exists or we override it

        reference.child(userName)
                .setValue(user)
                .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show())
                .addOnSuccessListener(unused -> Toast.makeText(RegisterActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show());
    }

}