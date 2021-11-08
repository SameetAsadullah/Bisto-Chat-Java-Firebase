package com.sameetasadullah.i180479_180531;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class screen8RVAdapter extends RecyclerView.Adapter<screen8RVAdapter.screen8ViewHolder> {
    List<Recent_Contact> ls;
    Context c;
    Bitmap image;

    public screen8RVAdapter(List<Recent_Contact> ls, Context c) {
        this.c=c;
        this.ls=ls;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @NonNull
    @Override
    public screen8ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.recent_contact_row,parent,false);
        return new screen8ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull screen8ViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, screen5.class);
                intent.putExtra("image", image);
                intent.putExtra("name", holder.name.getText().toString());
                c.startActivity(intent);
                ((Activity)c).finish();
            }
        });
    }

    @Override
    public int getItemCount() {

        return ls.size();
    }

    public class screen8ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RelativeLayout relativeLayout;
        public screen8ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            relativeLayout = itemView.findViewById(R.id.rl_contact);
        }
    }
}
