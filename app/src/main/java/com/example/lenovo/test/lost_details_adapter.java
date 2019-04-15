package com.example.lenovo.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class lost_details_adapter extends FirestoreRecyclerAdapter<Items, lost_details_adapter.lostholder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public lost_details_adapter(@NonNull FirestoreRecyclerOptions<Items> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull lostholder holder, int position, @NonNull Items model) {
        holder.category.setText(model.getCategory());
        Picasso.get().load(model.getImageurl())
                .centerCrop().fit().into(holder.imageView);
        holder.lostthing.setText(model.getLost_thing());
        //holder.button.setOnClickListener();
    }

    @NonNull
    @Override
    public lostholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_lost_details,viewGroup,false);
        return new lostholder(v);
    }

    public class lostholder extends RecyclerView.ViewHolder{
        public TextView category;
        public TextView lostthing;
        public ImageView imageView;
        public Button button;
        public lostholder(View view)
        {
            super(view);
            category=view.findViewById(R.id.category);
            imageView=view.findViewById(R.id.imageView);
            lostthing=view.findViewById(R.id.nameOfLostThing);
            button=view.findViewById(R.id.delete_button);
        }
    }
}
