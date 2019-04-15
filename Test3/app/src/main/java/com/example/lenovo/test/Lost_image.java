package com.example.lenovo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Lost_image extends AppCompatActivity {
    lost_details_adapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_image);

        mRecyclerview4=findViewById(R.id.mRecyclerView4);
        mRecyclerview4.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=FirebaseFirestore.getInstance()
                .collection("items")
                .whereEqualTo("status","lost")
                .limit(50);

        FirestoreRecyclerOptions<Items> options = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query,Items.class)
                .build();
        adapter = new lost_details_adapter(options);
        mRecyclerview4.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    //View.OnClickListener()
}
