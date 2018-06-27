package com.example.tianhao.chatapporeo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText chatMessage;
    String receiveUser, currentEmail;
    ArrayList<String> receiverMessages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView chatListView;
    FirebaseUser currentUser;
    String[] currentLogINUser,receiveLogInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        receiveUser = intent.getStringExtra("username");

        chatMessage = findViewById(R.id.chatEditText);
        chatListView = findViewById(R.id.messageListView);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentEmail = currentUser.getEmail();
        currentLogINUser = currentEmail.split("\\.");
        receiveLogInUser = receiveUser.split("\\.");
        setTitle("Login as "+ currentEmail + " Chat with "+ receiveUser);



        FirebaseDatabase.getInstance().getReference().child("users").child(receiveLogInUser[0]).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot messageFireBase : dataSnapshot.getChildren()){
                    String messageReceived = String.valueOf(messageFireBase.getValue());
                    if (messageReceived != null){
                        receiverMessages.add(messageReceived);
                    }
                    Log.i("receive message" , messageReceived);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,receiverMessages);
        chatListView.setAdapter(arrayAdapter);

    }
    public void sendChat(View view){

        String[]  receiverUserEmail = receiveUser.split("\\.");

        String stringChatMessage = chatMessage.getText().toString();
        if (stringChatMessage.matches("")){
            Toast.makeText(this, "You did not enter a message",Toast.LENGTH_SHORT).show();
        }else{
            receiverMessages.add(currentEmail + " said: " + chatMessage.getText().toString());
            arrayAdapter.notifyDataSetChanged();
            FirebaseDatabase.getInstance().getReference().child("users").child(receiverUserEmail[0]).child("messages").setValue(receiverMessages);
        }
        chatMessage.getText().clear();
    }
}
