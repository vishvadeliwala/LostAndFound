package com.example.lenovo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class show_found extends AppCompatActivity {
    private ItemAdapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview3;
//    private FirebaseAuth mAuth;
//    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_found);

        mRecyclerview3=findViewById(R.id.mRecyclerView3);
        mRecyclerview3.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        email=user.getEmail();

        Query query=FirebaseFirestore.getInstance()
                .collection("items")
                .limit(50);

        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query,Items.class)
                .build();
        adapter = new ItemAdapter(options);
        mRecyclerview3.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
