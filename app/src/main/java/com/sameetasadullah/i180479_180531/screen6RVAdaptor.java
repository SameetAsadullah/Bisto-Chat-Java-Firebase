package com.sameetasadullah.i180479_180531;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class screen6RVAdaptor extends RecyclerView.Adapter<screen6RVAdaptor.screen6ViewHolder> {
    Context context;
    List<contact> contactList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Account> accounts;
    fragment_screen6 fragment;

    public screen6RVAdaptor(Context context, List<contact> contactList, fragment_screen6 fragment) {
        this.context = context;
        this.contactList = contactList;
        this.fragment = fragment;
        database = FirebaseDatabase.getInstance();
        accounts = new ArrayList<>();
        myRef = database.getReference("Accounts");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                accounts.add(snapshot.getValue(Account.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        Picasso.get().load(contactList.get(position).getDp()).into(holder.dp);
        holder.rl_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < accounts.size(); ++i) {
                    if (accounts.get(i).getPhoneNumber().equals(contactList.get(holder.getAdapterPosition()).getNumber())) {
                        Intent intent = new Intent(context, screen5.class);
                        intent.putExtra("name", accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName());
                        intent.putExtra("receiverID", accounts.get(i).getID());
                        fragment.applicationNotMinimized();
                        context.startActivity(intent);
                    }
                }
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
        CircleImageView dp;

        public screen6ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            rl_contact = itemView.findViewById(R.id.rl_contact);
            dp = itemView.findViewById(R.id.dp);
        }
    }
}
