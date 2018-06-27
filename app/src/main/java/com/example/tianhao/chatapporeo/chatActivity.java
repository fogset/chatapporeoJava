package com.example.tianhao.chatapporeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText chatMessage;
    String receiveUser = "";
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView chatListView;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        receiveUser = intent.getStringExtra("username");
        setTitle("Chat with "+ receiveUser);
        chatMessage = (EditText)findViewById(R.id.chatEditText);
        chatListView = (ListView)findViewById(R.id.messageListView);
        mAuth = FirebaseAuth.getInstance();
         currentUser = mAuth.getCurrentUser();


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,messages);
        chatListView.setAdapter(arrayAdapter);



    }
    public void sendChat(View view){
        String currentEmail = currentUser.getEmail();
        String[] username = currentEmail.split("\\.");
        String[]  receiverUserEmail = receiveUser.split("\\.");

        messages.add(currentEmail + " said: " + chatMessage.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance().getReference().child("users").child(username[0]).child("messages").setValue(messages);
        FirebaseDatabase.getInstance().getReference().child("users").child(receiverUserEmail[0]).child("messages").setValue(messages);
        chatMessage.getText().clear();
    }
}
