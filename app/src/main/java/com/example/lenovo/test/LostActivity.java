package com.example.lenovo.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class LostActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText slost_thing,sCompany,sModel,sTime,sDate,sDepartment,sClass,sLab,sDescription;
    private Button b3,b,b1,bupload;
    private static int PICK_IMAGE_REQUEST=71;
    private ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private Uri filePath;
    private String email;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        slost_thing=(EditText)findViewById(R.id.plaintextNameofthing);
        sCompany=(EditText)findViewById(R.id.EditcompanyText);
        sModel=(EditText)findViewById(R.id.modelText);
        sTime=(EditText)findViewById(R.id.editTexttime);
        sDate=(EditText)findViewById(R.id.editTextdate);
        sDepartment=(EditText)findViewById(R.id.editTextdept);
        sClass=(EditText)findViewById(R.id.editTextclassRoom);
        sLab=(EditText)findViewById(R.id.editTextlab);
        sDescription=(EditText)findViewById(R.id.descriptionText);


        imageView = (ImageView)findViewById(R.id.imageView);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        b3=(Button) findViewById(R.id.submitlost);
        b1=(Button) findViewById(R.id.cancellost);
        bupload=(Button)findViewById(R.id.uploadbtn);

        b=(Button)findViewById(R.id.browse);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        bupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        spinner =(Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spinnerCategory, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void popUp(View v)
    {
        Bundle extras = getIntent().getExtras();
        final String status = extras.getString("status");
        FirebaseUser user = mAuth.getCurrentUser();
        email=user.getEmail();

                String category = spinner.getSelectedItem().toString();
                String lost_thing = slost_thing.getText().toString();
                String company=sCompany.getText().toString();
                String model=sModel.getText().toString();
                final String time= sTime.getText().toString();
                String date = sDate.getText().toString();
                String department = sDepartment.getText().toString();
                String classroom = sClass.getText().toString();
                String labno = sLab.getText().toString();
                String description = sDescription.getText().toString();

                int pos= spinner.getSelectedItemPosition();

                if(pos==0)
                {
                    Toast.makeText(LostActivity.this,"Please select Category",Toast.LENGTH_LONG).show();
                    return;
                }

                if(lost_thing.isEmpty())
                {
                    slost_thing.setError("Name Of thing is required");
                    slost_thing.requestFocus();
                    return;
                }

                if(company.isEmpty())
                {
                    sCompany.setError("Name Of thing is required");
                    sCompany.requestFocus();
                    return;
                }

                if(time.isEmpty())
                {
                    sTime.setError("Time is required");
                    sTime.requestFocus();
                    return;
                }
                if(date.isEmpty())
                {
                    sDate.setError("Date is required");
                    sDate.requestFocus();
                    return;
                }
                if(department.isEmpty()) {
                    sDepartment.setError("Department is required");
                    sDepartment.requestFocus();
                    return;
                }

                final Map<String, Object> detail = new HashMap<>();
                detail.put("category", category);
                detail.put("lost thing", lost_thing);
                detail.put("company", company);
                detail.put("model", model);
                detail.put("time", time);
                detail.put("date", date);
                detail.put("department", department);
                detail.put("class room no", classroom);
                detail.put("lab no", labno);
                detail.put("description", description);
                detail.put("status", status);
                detail.put("email",email);
                mStorageRef = FirebaseStorage.getInstance().getReference();

//                db.collection("details").document(email).collection("lost").document(time)
//                        .set(detail, SetOptions.merge())
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("qqqqqqqq", "db added");
//                                Toast.makeText(LostActivity.this, "Added to DB"  , Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("qqqqweqwe", "Error writing document", e);
//                            }
//                        });

        if (filePath != null) {
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(LostActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = mStorageRef.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //HashMap<String, Object> detail = new HashMap<>();
                                    detail.put("imageurl", uri.toString());
                                    //firebaseDatabase.add(detail);
                                    db.collection("details").document(email).collection("lost").document(time)
                                            .set(detail, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("qqqqqqqq", "db added");
                                                    Toast.makeText(LostActivity.this, "Added to DB", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("qqqqweqwe", "Error writing document", e);
                                                }
                                            });

                                    db.collection("items").document(time)
                                            .set(detail, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("qqqqqqqq", "db added");
                                                    Toast.makeText(LostActivity.this, "Added to DB", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("qqqqweqwe", "Error writing document", e);
                                                }
                                            });
                                    //final Integer[] counter = new Integer[1];
                                    // double counter;
                                    DocumentReference docRef = db.collection("counters").document("counter_lost");
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                //counter[0] =Integer.parseInt(document.get("counter").toString());
                                                //double counter=document.getDouble("counter");
                                                if (document.exists()) {
                                                    //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                    //counter=Integer.parseInt(document.get("counter").toString());
                                                    Double counter=document.getDouble("counter");
                                                    counter = counter + 1;
                                                    HashMap<String,Object> hd=new HashMap<>();
                                                    hd.put("counter",counter);
                                                    db.collection("counters").document("counter_lost")
                                                            .set(hd, SetOptions.merge())
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d("qqqqqqqq", "db added");
                                                                    Toast.makeText(LostActivity.this, "Updated Counter", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w("qqqqweqwe", "Error writing document", e);
                                                                }
                                                            });
                                                } else {
                                                    Log.d(TAG, "No such document");
                                                }
                                            } else {
                                                Log.d(TAG, "get failed with ", task.getException());
                                            }
                                        }
                                    });

                                    //counter[0]= counter[0].intValue()+1;

                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(LostActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LostActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            final double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
            //if(progres==100)
            Intent intent = new Intent(LostActivity.this, Lost_image.class);
            startActivity(intent);
        }


        Intent intent= new Intent(LostActivity.this,Lost_image.class);
                startActivity(intent);
    }

    public void cancel(View v)
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LostActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
