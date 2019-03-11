package com.example.a100456794.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView signup=(TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                Intent launchActivity1= new Intent(MainActivity.this,SignUp.class);
                startActivity(launchActivity1);
            }
        });


    }
}
