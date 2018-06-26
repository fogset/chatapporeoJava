package com.example.tianhao.chatapporeo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {
    EditText emailEdit, passwordEdit;
    String email, password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        emailEdit = findViewById(R.id.emailEditText);
        passwordEdit = findViewById(R.id.passwordEditText);

        if(currentUser != null){

        }

//        String test = "fogset@b.com";
//        String[] output = test.split("\\.");
//
        Log.i("after split is " , "dfsdfsdfds");

//        FirebaseDatabase.getInstance().getReference().child("users").child("fogset@b").child("messages").setValue("eat");
    }
    public  void loginButton( View view){
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(loginActivity.this, "Fields are empty.", Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(loginActivity.this, "signInWithEmail:success",Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(loginActivity.this, "Authentication failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
    public void SignUp(View view){
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(loginActivity.this, "Fields are empty.", Toast.LENGTH_LONG).show();
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                String[] username = email.split("\\.");
                                FirebaseDatabase.getInstance().getReference().child("users").child(username[0]).setValue(email);
                                Toast.makeText(loginActivity.this, "createWithEmail:success",Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(loginActivity.this, "Authentication failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


    }


    public void logIn(){
        //Intent intent = new Intent(this, SecondActivity.class);
        //intent.putExtra("age", 28);
        //startActivity(intent);
    }
}
