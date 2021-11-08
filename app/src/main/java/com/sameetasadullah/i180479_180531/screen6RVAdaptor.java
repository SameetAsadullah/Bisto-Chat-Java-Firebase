package com.sameetasadullah.i180479_180531;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class screen6RVAdaptor extends RecyclerView.Adapter<screen6RVAdaptor.screen6ViewHolder> {
    Context context;
    List<contact> contactList;

    public screen6RVAdaptor(Context context, List<contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public screen6ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.contact_row, null, false);
        return new screen6ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull screen6ViewHolder holder, int position) {
        holder.name.setText(contactList.get(position).getName());
        holder.number.setText(contactList.get(position).getNumber());

        holder.rl_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked on recent contact", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class screen6ViewHolder extends RecyclerView.ViewHolder{
        TextView name, number;
        RelativeLayout rl_contact;

        public screen6ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            rl_contact = itemView.findViewById(R.id.rl_contact);
        }
    }
}
