package com.example.lenovo.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends FirestoreRecyclerAdapter<Items,ItemAdapter.itemsholder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull FirestoreRecyclerOptions<Items> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull itemsholder holder, int position, @NonNull Items model) {

        holder.category.setText(model.getCategory());
        Picasso.get().load(model.getImageurl())
                .centerCrop().fit().into(holder.imageView);
        holder.foundthing.setText(model.getLost_thing());
}

    @NonNull
    @Override
    public itemsholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_details,viewGroup,false);
        return new itemsholder(v);
        //return null;
    }

    public class itemsholder extends RecyclerView.ViewHolder{
        public TextView category;
        public TextView foundthing;
        public ImageView imageView;
        public itemsholder(View view)
        {
            super(view);
            category=view.findViewById(R.id.category);
            imageView=view.findViewById(R.id.imageView);
            foundthing=view.findViewById(R.id.nameOfFoundThing);

        }
    }
}
