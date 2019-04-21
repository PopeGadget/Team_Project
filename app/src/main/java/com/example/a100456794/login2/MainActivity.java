package com.example.a100456794.login2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables for logging in
    private Button btnLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private static final String TAG = "Home";

    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This piece is for clicking 'Sign Up' and being taken to Sign Up page
        final TextView signup=(TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                Intent launchActivity1= new Intent(MainActivity.this,SignUp.class);
                startActivity(launchActivity1);
            }
        });

        //This piece is for clicking 'Forgot Password?' and being taken to Forgot Password page
        final TextView forgotPassword=(TextView)findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                Intent launchActivity1= new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(launchActivity1);
            }
        });

        //This piece is for clicking 'Continue as guest' and being taken to Home page
        final TextView continueGuest=(TextView)findViewById(R.id.continueGuest);
        continueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                Intent launchActivity1= new Intent(MainActivity.this,Home.class);
                startActivity(launchActivity1);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        //checks if user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        //Taking values from XML and storing in the variables
        editTextUsername = (EditText) findViewById(R.id.username_input);
        editTextPassword = (EditText) findViewById(R.id.password_input);
        btnLogin = (Button) findViewById(R.id.btn_login);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);


    }

    private void userLogin(){
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            //email empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.hide();
                            if (task.isSuccessful()) {
                                //start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }
                        }
                    });
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "We can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            userLogin();
        }

    }
}
