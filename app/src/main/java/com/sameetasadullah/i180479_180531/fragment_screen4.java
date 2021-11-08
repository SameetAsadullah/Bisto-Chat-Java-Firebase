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
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class fragment_screen4 extends Fragment {
    RecyclerView recyclerView;
    screen4RVAdaptor rvAdaptor;
    List<chat> chatList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen4, container, false);

        recyclerView = view.findViewById(R.id.rv_chats);

//        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(),new String[]{
//                    Manifest.permission.CAMERA
//            },100);
//
//        }

        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(chatsContract.Chats._SENDER_NAME, "Janet Fowler");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "John");
//        cv.put(chatsContract.Chats._MESSAGE, "Hello John, what are you going to do this weekend?");
//        cv.put(chatsContract.Chats._TIME, "17:45");
//        cv.put(chatsContract.Chats._SENDER_NAME, "John");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "Janet Fowler");
//        cv.put(chatsContract.Chats._MESSAGE, "Nothing planned, and you?");
//        cv.put(chatsContract.Chats._TIME, "18:04");
//        cv.put(chatsContract.Chats._SENDER_NAME, "Janet Fowler");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "John");
//        cv.put(chatsContract.Chats._MESSAGE, "I'm going to San Francisco and I'm booking and Airbnb. Would you like to come?");
//        cv.put(chatsContract.Chats._TIME, "now");
//        cv.put(chatsContract.Chats._SENDER_NAME, "John");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "Jason Boyd");
//        cv.put(chatsContract.Chats._MESSAGE, "Hey Jason, do you like our new plan?");
//        cv.put(chatsContract.Chats._TIME, "15:00");
//        cv.put(chatsContract.Chats._SENDER_NAME, "Jason Boyd");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "John");
//        cv.put(chatsContract.Chats._MESSAGE, "Sounds good.");
//        cv.put(chatsContract.Chats._TIME, "16:00");
//        cv.put(chatsContract.Chats._SENDER_NAME, "Nicholas Dunn");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, "John");
//        cv.put(chatsContract.Chats._MESSAGE, "See you there!");
//        cv.put(chatsContract.Chats._TIME, "09:10");
        double result = sqLiteDatabase.insert(chatsContract.Chats.TABLENAME, null, cv);
        if (result == -1) {
            System.out.println("Insertion query failed");
        } else {
            System.out.println("Insertion query passed");
        }
        sqLiteDatabase.close();
        dbHelper.close();

        chatList = new ArrayList<>();
        getDataFromDB();
        checkOnlineStatus();
        checkReadMessageStatus();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        rvAdaptor = new screen4RVAdaptor(getActivity(), chatList);
        recyclerView.setAdapter(rvAdaptor);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));

        return view;
    }

    private void checkOnlineStatus() {
        for (int i = 0; i < chatList.size(); ++i) {
            if (chatList.get(i).getName().equals("Janet Fowler")) {
                chatList.get(i).setOnline(true);
            }
        }
    }

    private void checkReadMessageStatus() {
        for (int i = 0; i < chatList.size(); ++i) {
            if (chatList.get(i).getName().equals("Jason Boyd")) {
                chatList.get(i).setRead(true);
            }
        }
    }

    private void getDataFromDB() {
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String[] projection = new String[] {
                chatsContract.Chats._ID,
                chatsContract.Chats._SENDER_NAME,
                chatsContract.Chats._RECEIVER_NAME,
                chatsContract.Chats._MESSAGE,
                chatsContract.Chats._TIME
        };

        chatList.clear();
        Cursor cursor = sqLiteDatabase.query(chatsContract.Chats.TABLENAME, projection, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(chatsContract.Chats._SENDER_NAME);
            String senderName = cursor.getString(index);
            index = cursor.getColumnIndex(chatsContract.Chats._RECEIVER_NAME);
            String receiverName = cursor.getString(index);

            if (senderName.equals("John") || receiverName.equals("John")) {
                index = cursor.getColumnIndex(chatsContract.Chats._ID);
                String id = cursor.getString(index);
                index = cursor.getColumnIndex(chatsContract.Chats._MESSAGE);
                String message = cursor.getString(index);
                index = cursor.getColumnIndex(chatsContract.Chats._TIME);
                String time = cursor.getString(index);

                if (message.length() > 26) {
                    message = message.substring(0, 26) + " ...";
                }

                boolean updated = false;
                for (int i = 0; i < chatList.size(); ++i) {
                    if (chatList.get(i).getName().equals(senderName) ||
                            chatList.get(i).getName().equals(receiverName)) {
                        chatList.get(i).setMessage(message);
                        chatList.get(i).setTime(time);
                        updated = true;
                    }
                }
                if (!updated) {
                    if (senderName.equals("John")) {
                        chatList.add(new chat(id, receiverName, message, time, false, false));
                    } else {
                        chatList.add(new chat(id, senderName, message, time, false, false));
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((fragmentsContainer)getActivity()).changeImageColorToBlue(0);
        getDataFromDB();
        checkOnlineStatus();
        checkReadMessageStatus();
        rvAdaptor.notifyDataSetChanged();
    }
}