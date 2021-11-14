package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class fragment_screen6 extends Fragment {
    RecyclerView recyclerView;
    screen6RVAdaptor adaptor;
    List<contact> contactList;
    RelativeLayout newContact, newGroup;
    List<Account> accounts;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen6, container, false);

        recyclerView = view.findViewById(R.id.rv_contacts);
        newContact = view.findViewById(R.id.rl_new_contact);
        newGroup = view.findViewById(R.id.rl_new_group);
        accounts = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("Accounts");
        mAuth = FirebaseAuth.getInstance();

        contactList = new ArrayList<>();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                accounts.add(snapshot.getValue(Account.class));
                contactList.clear();
                addPhoneContactsToList();
                adaptor.notifyDataSetChanged();
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
        adaptor = new screen6RVAdaptor(getActivity(), contactList, fragment_screen6.this);
        recyclerView.setAdapter(adaptor);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(50));

        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Clicked on new contact", Toast.LENGTH_LONG).show();
            }
        });
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Clicked on new group", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void addPhoneContactsToList() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor phones=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNo;
        while (phones.moveToNext()) {
            int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = phones.getString(index).replace("+92","0");

            if (phoneNo.charAt(4) == ' ') {
                StringBuilder stringBuilder = new StringBuilder(phoneNo);
                stringBuilder.deleteCharAt(4);
                phoneNo = stringBuilder.toString();
            }

            for (int i = 0; i < accounts.size(); ++i) {
                if (accounts.get(i).getPhoneNumber().equals(phoneNo) && !accounts.get(i).getID().equals(mAuth.getUid())) {
                    contactList.add(new contact(accounts.get(i).getFirstName() + " " +
                            accounts.get(i).getLastName(), phoneNo));
                    break;
                }
            }
        }

        phones.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((fragmentsContainer)getActivity()).changeImageColorToBlue(1);
    }

    public void applicationNotMinimized() {
        ((fragmentsContainer)getActivity()).minimized = false;
    }
}