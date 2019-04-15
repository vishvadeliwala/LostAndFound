package com.example.lenovo.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;
    private EditText email;
    private EditText password;
    private Button b3;
    private Button b2;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    public void signup(View v)
    {
            Intent intent= new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
    }

    public void login(View v)
    {
        b2 = (Button) findViewById(R.id.button);
        email=(EditText) findViewById(R.id.editText3);
        password=(EditText) findViewById(R.id.editText5);
        final String emailid,pass;
        emailid = email.getText().toString();
        pass = password.getText().toString();
        if(TextUtils.isEmpty(emailid)|| TextUtils.isEmpty(pass))
        {
            Toast.makeText(MainActivity.this,"Enter Email ID and Password",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Invalid Email Or Password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void cancel(View v)
    {
        Intent intent= new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void onClick(View v)
    {

    }
}