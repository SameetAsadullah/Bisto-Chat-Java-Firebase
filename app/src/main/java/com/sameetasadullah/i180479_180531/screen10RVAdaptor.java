package com.sameetasadullah.i180479_180531;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class screen10RVAdaptor extends RecyclerView.Adapter<screen10RVAdaptor.screen10ViewHolder> {
    Context context;
    List<call> callList = new ArrayList<>();

    public screen10RVAdaptor(Context context, List<call> callList) {
        this.context = context;
        this.callList = callList;
    }

    @NonNull
    @Override
    public screen10RVAdaptor.screen10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context).inflate(R.layout.call_row, null, false);
        return new screen10ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull screen10RVAdaptor.screen10ViewHolder holder, int position) {
        holder.name.setText(callList.get(position).getName());
        holder.status.setText(callList.get(position).getStatus());
        holder.time.setText(callList.get(position).getTime());

        if (callList.get(position).getReceived())
            holder.imageView.setImageResource(R.drawable.call_received_icon);
        else
            holder.imageView.setImageResource(R.drawable.call_made_icon);

        holder.rl_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, screen11.class);
                intent.putExtra("name", holder.name.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public class screen10ViewHolder extends RecyclerView.ViewHolder{
        TextView name, status, time;
        RelativeLayout rl_call;
        ImageView imageView;

        public screen10ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);
            rl_call = itemView.findViewById(R.id.rl_call);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
