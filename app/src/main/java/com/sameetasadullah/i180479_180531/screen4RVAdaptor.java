package com.sameetasadullah.i180479_180531;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class screen4RVAdaptor extends RecyclerView.Adapter<screen4RVAdaptor.screen4ViewHolder> {
    Context context;
    List<chat> chatList;

    public screen4RVAdaptor(Context context, List<chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public screen4RVAdaptor.screen4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chat_row, null, false);
        return new screen4ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull screen4RVAdaptor.screen4ViewHolder holder, int position) {
        holder.name.setText(chatList.get(position).getName());
        holder.message.setText(chatList.get(position).getMessage());
        holder.time.setText(chatList.get(position).getTime());

        if (chatList.get(position).getOnline()) {
            holder.online_status.setAlpha((float)1);
        }
        if (!chatList.get(position).getRead()) {
            holder.read_status.setAlpha((float)1);
        }

        holder.rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, screen5.class);
                intent.putExtra("name", holder.name.getText().toString());
                if (chatList.get(holder.getAdapterPosition()).getOnline()) {
                    intent.putExtra("onlineStatus", 1);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class screen4ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message, time;
        RelativeLayout rl_chat;
        CircleImageView online_status, read_status;

        public screen4ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            rl_chat = itemView.findViewById(R.id.rl_chat);
            online_status = itemView.findViewById(R.id.online_status);
            read_status = itemView.findViewById(R.id.read_status);
        }
    }
}
