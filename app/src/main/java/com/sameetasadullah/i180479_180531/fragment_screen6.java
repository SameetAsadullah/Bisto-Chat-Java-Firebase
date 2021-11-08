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

import java.util.ArrayList;
import java.util.List;

public class fragment_screen6 extends Fragment {
    RecyclerView recyclerView;
    screen6RVAdaptor adaptor;
    List<contact> contactList;
    RelativeLayout newContact, newGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen6, container, false);

        recyclerView = view.findViewById(R.id.rv_contacts);
        newContact = view.findViewById(R.id.rl_new_contact);
        newGroup = view.findViewById(R.id.rl_new_group);

        contactList = new ArrayList<>();
        addPhoneContactsToList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adaptor = new screen6RVAdaptor(getActivity(), contactList);
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
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                int index = cur.getColumnIndex(ContactsContract.Contacts._ID);
                String id = cur.getString(index);
                index = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cur.getString(index);

                index = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                if (cur.getInt(index) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        index = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNo = pCur.getString(index);
                        contactList.add(new contact(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((fragmentsContainer)getActivity()).changeImageColorToBlue(1);
    }
}