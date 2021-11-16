package com.sameetasadullah.i180479_180531;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class screen4RVAdaptor extends RecyclerView.Adapter<screen4RVAdaptor.screen4ViewHolder> implements Filterable {
    Context context;
    List<chat> chatList;
    List<chat> filteredList;
    fragment_screen4 fragment;

    public screen4RVAdaptor(Context context, List<chat> chatList, fragment_screen4 fragment) {
        this.context = context;
        this.chatList = chatList;
        this.filteredList = chatList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public screen4RVAdaptor.screen4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chat_row, null, false);
        return new screen4ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull screen4RVAdaptor.screen4ViewHolder holder, int position) {
        holder.name.setText(filteredList.get(position).getName());
        holder.message.setText(filteredList.get(position).getMessage());
        holder.time.setText(filteredList.get(position).getTime());
        Picasso.get().load(filteredList.get(position).getDp()).into(holder.dp);

        if (filteredList.get(position).getState().equals("online")) {
            holder.online_status.setAlpha((float)1);
        } else {
            holder.online_status.setAlpha((float)0);
        }

        if (!filteredList.get(position).getRead()) {
            holder.read_status.setAlpha((float)1);
        }

        holder.rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, screen5.class);
                intent.putExtra("name", holder.name.getText().toString());
                intent.putExtra("receiverID", filteredList.get(holder.getAdapterPosition()).getId());
                fragment.applicationNotMinimized();
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    filteredList = chatList;
                }
                else{
                    List<chat> listFiltered = new ArrayList<>();
                    for (chat row: chatList){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            listFiltered.add(row);
                        }
                    }
                    filteredList = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList =  (List<chat>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class screen4ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message, time;
        RelativeLayout rl_chat;
        CircleImageView online_status, read_status, dp;

        public screen4ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            rl_chat = itemView.findViewById(R.id.rl_chat);
            online_status = itemView.findViewById(R.id.online_status);
            read_status = itemView.findViewById(R.id.read_status);
            dp = itemView.findViewById(R.id.dp);
        }
    }
}
