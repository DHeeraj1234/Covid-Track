package com.drm.vtracker;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Parse_Adapter extends RecyclerView.Adapter<Parse_Adapter.ViewHolder> {

    private ArrayList<ParseItem> parseItems;
    private Context context;

    public Parse_Adapter(ArrayList<ParseItem> parseItems, Context context) {
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Parse_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        //TextView textView;
        //textView = view.findViewById(R.id.textView);
        ViewHolder rowTWO = new ViewHolder(view);

        return rowTWO;
    }

    @Override
    public void onBindViewHolder(@NonNull Parse_Adapter.ViewHolder holder, int position) {
        if(position%2 == 1){
            holder.textV.setBackgroundColor(Color.parseColor("#d0edf2"));
        } else {
            holder.textV.setBackgroundColor(Color.parseColor("#f0dff7"));

        }


        try {
            ParseItem parseItem = parseItems.get(position);

            Log.d("h1h1h...tesok", parseItem.getTitle());
            holder.textV.setText(String.valueOf(parseItem.getTitle()));
            holder.textV1.setText(String.valueOf(parseItem.getDetailUrl()));
            holder.textV2.setText(String.valueOf(parseItem.getNewCases()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //Picasso.get().load(parseItem.getImgUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //ImageView imageView;
        TextView textV;
        TextView textV1;
        TextView textV2;

        public ViewHolder(@NonNull View view) {
            super(view);
            //imageView = view.findViewById(R.id.imageView);
            textV = view.findViewById(R.id.item_title);
            textV1 = view.findViewById(R.id.item_title1);
            textV2 = view.findViewById(R.id.item_title2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ParseItem parseItem = parseItems.get(position);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", parseItem.getTitle());
            //intent.putExtra("image", parseItem.getImgUrl());
            intent.putExtra("detailUrl", parseItem.getDetailUrl());
            context.startActivity(intent);
        }
    }

    public void setFilter (ArrayList<ParseItem> newList) {
        parseItems = new ArrayList<>();
        parseItems.addAll(newList);
        notifyDataSetChanged();
    }
}

