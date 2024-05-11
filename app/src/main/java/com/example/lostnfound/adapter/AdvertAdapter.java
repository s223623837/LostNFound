package com.example.lostnfound.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnfound.R;
import com.example.lostnfound.model.Advert;
import com.example.lostnfound.ItemDetailsActivity;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    private Context context;
    private List<Advert> advertList;

    public AdvertAdapter(Context context, List<Advert> advertList) {
        this.context = context;
        this.advertList = advertList;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        Advert advert = advertList.get(position);
        holder.bind(advert);

        // Set click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the clicked advert
                Advert clickedAdvert = advertList.get(holder.getAdapterPosition());

                // Start ItemDetailsActivity to display details of the clicked advert
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("advertId", String.valueOf(clickedAdvert.getId())); // Convert ID to String
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public class AdvertViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewPostType;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPostType = itemView.findViewById(R.id.textViewPostType);
        }

        public void bind(Advert advert) {
            textViewName.setText(advert.getName());
            textViewPostType.setText(advert.getPostType());
        }
    }
}
