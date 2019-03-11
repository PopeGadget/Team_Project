package com.example.a100456794.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //When clicking on Log out it takes you to Login page
        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                firebaseAuth.signOut();
                finish();
                Intent launchActivity1= new Intent(Home.this,MainActivity.class);
                startActivity(launchActivity1);
            }
        });

        //Creates firebaseAuth instance for the user
        firebaseAuth = FirebaseAuth.getInstance();

        //If user cannot be found then redirected to Login page
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //Variables for identifying the user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView username = (TextView) findViewById(R.id.username_input);

    }

}
