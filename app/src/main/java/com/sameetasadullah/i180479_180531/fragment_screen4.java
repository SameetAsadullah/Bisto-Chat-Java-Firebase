package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fragment_screen4 extends Fragment {
    RecyclerView recyclerView;
    screen4RVAdaptor rvAdaptor;
    List<chat> chatList;
    ImageView newMessage;
    DatabaseReference myRef, myRef1;
    FirebaseAuth mAuth;
    List<Account> accounts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen4, container, false);

        recyclerView = view.findViewById(R.id.rv_chats);
        newMessage = view.findViewById(R.id.new_message);
        myRef = FirebaseDatabase.getInstance().getReference("Messages");
        myRef1 = FirebaseDatabase.getInstance().getReference("Accounts");
        mAuth = FirebaseAuth.getInstance();
        accounts = new ArrayList<>();

        chatList = new ArrayList<>();

        myRef1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                accounts.add(snapshot.getValue(Account.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Account account = snapshot.getValue(Account.class);
                for (int i = 0; i < accounts.size(); ++i) {
                    if (accounts.get(i).getID().equals(account.getID())) {
                        accounts.set(i, account);
                        break;
                    }
                }
                for (int i = 0; i < chatList.size(); ++i) {
                    if (chatList.get(i).getId().equals(account.getID())) {
                        chatList.get(i).setState(account.getState());
                        chatList.get(i).setLastSeenTime(account.getLastSeenTime());
                        chatList.get(i).setLastSeenDate(account.getLastSeenDate());
                        rvAdaptor.notifyDataSetChanged();
                        break;
                    }
                }
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

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                message msg = snapshot.getValue(message.class);
                if (msg.getSenderID().equals(mAuth.getUid()) || msg.getReceiverID().equals(mAuth.getUid())) {
                    String tempMsg = msg.getMessage();
                    if (tempMsg.length() > 26) {
                        tempMsg = tempMsg.substring(0, 26) + "...";
                    }
                    else if (tempMsg.equals("")) {
                        if (msg.getSenderID().equals(mAuth.getUid())) {
                            tempMsg = "You sent a photo";
                        } else if (msg.getReceiverID().equals(mAuth.getUid())) {
                            tempMsg = "User sent a photo";
                        }
                    }
                    boolean updated = false;
                    for (int i = 0; i < chatList.size(); ++i) {
                        if (chatList.get(i).getId().equals(msg.getSenderID()) ||
                                chatList.get(i).getId().equals(msg.getReceiverID())) {
                            chatList.get(i).setMessage(tempMsg);
                            chatList.get(i).setTime(msg.getTime());
                            updated = true;
                        }
                    }
                    if (!updated) {
                        if (msg.getSenderID().equals(mAuth.getUid())) {
                            String name = "", dp = "", state = "", lastSeenTime = "", lastSeenDate = "";
                            for (int i = 0; i < accounts.size(); ++i) {
                                if (accounts.get(i).getID().equals(msg.getReceiverID())) {
                                    name = accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName();
                                    dp = accounts.get(i).getDp();
                                    state = accounts.get(i).getState();
                                    lastSeenTime = accounts.get(i).getLastSeenTime();
                                    lastSeenDate = accounts.get(i).getLastSeenDate();
                                }
                            }
                            chatList.add(new chat(msg.getReceiverID(), name, tempMsg, msg.getTime(),
                                    true, dp, state, lastSeenTime, lastSeenDate));
                        }
                        else if (msg.getReceiverID().equals(mAuth.getUid())) {
                            String name = "", dp = "", state = "", lastSeenTime = "", lastSeenDate = "";
                            for (int i = 0; i < accounts.size(); ++i) {
                                if (accounts.get(i).getID().equals(msg.getSenderID())) {
                                    name = accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName();
                                    dp = accounts.get(i).getDp();
                                    state = accounts.get(i).getState();
                                    lastSeenTime = accounts.get(i).getLastSeenTime();
                                    lastSeenDate = accounts.get(i).getLastSeenDate();
                                }
                            }
                            chatList.add(new chat(msg.getSenderID(), name, tempMsg, msg.getTime(),
                                    true, dp, state, lastSeenTime, lastSeenDate));
                        }
                    }
                    rvAdaptor.notifyDataSetChanged();
                }
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        rvAdaptor = new screen4RVAdaptor(getActivity(), chatList);
        recyclerView.setAdapter(rvAdaptor);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((fragmentsContainer)getActivity()).changeViewPager(1);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((fragmentsContainer)getActivity()).changeImageColorToBlue(0);
     }
}