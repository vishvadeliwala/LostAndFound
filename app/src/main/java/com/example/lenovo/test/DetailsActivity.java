package com.example.lenovo.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.content.res.Resources;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private RadioButton slostButton,sfoundButton;
    private Button submit,cancel;
    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner2;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText sdivision,srollno;
    private String email;
   // private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_details);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        spinner =(Spinner) findViewById(R.id.year1);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,R.array.year1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if(spinner.getSelectedItem() != null) {
            if (spinner.getSelectedItem() == "1") {
                spinner.getSelectedItem().equals(spinner1.isSelected());

            }

        }
        spinner1 =(Spinner) findViewById(R.id.sem);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.sem, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        spinner2 =(Spinner) findViewById(R.id.branch);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


        sdivision=(EditText)findViewById(R.id.division);
        srollno=(EditText)findViewById(R.id.rollno);
        slostButton=(RadioButton)findViewById(R.id.lostButton);
        sfoundButton=(RadioButton)findViewById(R.id.foundButton);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submit(View v)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        email=user.getEmail();
        Resources res=getResources();
        final String[] semester = res.getStringArray(R.array.sem);

        String year = spinner.getSelectedItem().toString();
        String sem = spinner1.getSelectedItem().toString();
        String branch = spinner2.getSelectedItem().toString();
        String division = sdivision.getText().toString();
        String rollno = srollno.getText().toString();
        int pos= spinner.getSelectedItemPosition();
        int pos1= spinner1.getSelectedItemPosition();
        int pos2= spinner2.getSelectedItemPosition();

        if(pos==0)
        {
            Toast.makeText(DetailsActivity.this,"Please select year",Toast.LENGTH_LONG).show();
            return;
        }
        if(pos1==0)
        {
            Toast.makeText(DetailsActivity.this,"Please select sem",Toast.LENGTH_LONG).show();
            return;
        }
        if(pos2==0)
        {
            Toast.makeText(DetailsActivity.this,"Please select branch",Toast.LENGTH_LONG).show();
            return;
        }
        if(division.isEmpty()){
            sdivision.setError("Division is required");
            sdivision.requestFocus();
            return;
        }

        if(rollno.isEmpty()){
            srollno.setError("Roll Number is required");
            srollno.requestFocus();
            return;
        }

        Map<String, Object> detail = new HashMap<>();
        detail.put("year", year);
        detail.put("sem", sem);
        detail.put("branch", branch);
        detail.put("division", division);
        detail.put("rollno", rollno);
        detail.put("email",email);
        if (slostButton.isChecked()) {
            detail.put("status","lost");
            Intent intent = new Intent(DetailsActivity.this, LostActivity.class);
            intent.putExtra("status","lost");
            startActivity(intent);
        } else if (sfoundButton.isChecked()) {
            detail.put("status","found");
            Intent intent = new Intent(DetailsActivity.this, FoundActivity.class);
            intent.putExtra("status","found");
            startActivity(intent);
        }

        db.collection("details").document()
                .set(detail, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) { Log.d("qqqqqqqq", "db added");
                                Toast.makeText(DetailsActivity.this, "Added to DB"  , Toast.LENGTH_SHORT).show();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) { Log.w("qqqqweqwe", "Error writing document", e);
                            }
                        });
    }

    public void cancel(View v)
    {
        Intent intent= new Intent(DetailsActivity.this,MainActivity.class);
        startActivity(intent);
    }
}