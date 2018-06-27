package com.example.tianhao.chatapporeo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class UserListActivity : AppCompatActivity() {

    var userListView: ListView? = null
    var usersEmail = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)


        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        var currentEmail = currentFirebaseUser!!.email
        setTitle(currentEmail +"'s friends list")



        userListView = this.findViewById(R.id.userListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usersEmail)
        userListView?.adapter = adapter

        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val email = p0.child("email").value as String
                if (email != currentEmail){
                    usersEmail.add(email)
                }
                //keys.add(p0.key!!)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}

        })
        userListView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->

            var intent = Intent(this, chatActivity::class.java)
            intent.putExtra("username", usersEmail[position])

            startActivity(intent)
        }
    }


}
