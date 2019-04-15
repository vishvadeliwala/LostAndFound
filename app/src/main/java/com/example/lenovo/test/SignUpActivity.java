package com.example.lenovo.test;

import android.content.Intent;
import android.renderscript.ScriptIntrinsicHistogram;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText sname;
    private EditText semail;
    private EditText spassword;
    private EditText sconfirm_password;
    private EditText smobile_no;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        sname=(EditText)findViewById(R.id.editText);
        semail=(EditText)findViewById(R.id.editText4);
        spassword=(EditText)findViewById(R.id.editText2);
        sconfirm_password=(EditText)findViewById(R.id.editText6);
        smobile_no=(EditText)findViewById(R.id.editText8);
    }


    public void details(View v)
    {
                String name = sname.getText().toString();
                String email = semail.getText().toString();
                String password = spassword.getText().toString();
                String confirm_pass = sconfirm_password.getText().toString();
                String mobile_no = smobile_no.getText().toString();

                if (name.isEmpty()) {
                    sname.setError("Name is required");
                    sname.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    semail.setError("Email is required");
                    semail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    spassword.setError("Password is required");
                    spassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    spassword.setError("Length of password should be atleast 6 characters");
                    spassword.requestFocus();
                    return;
                }
                if (!confirm_pass.matches(password)) {
                    sconfirm_password.setError("Not matching the password");
                    sconfirm_password.requestFocus();
                    return;
                }
                if (mobile_no.isEmpty()) {
                    smobile_no.setError("Mobile Number is required");
                    smobile_no.requestFocus();
                    return;
                }
                Map<String, Object> detail = new HashMap<>();
                detail.put("name", name);
                detail.put("email", email);
                detail.put("password", password);
                detail.put("mobile number", mobile_no);

                DocumentReference newRef = db.collection("details").document(email);
                newRef.set(detail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("aaaa", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("abvff", "Error writing document", e);
                            }
                        });


                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
    }

    public void cancel(View v)
    {

                Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
    }
    public void onClick(View v)
    {}
}
