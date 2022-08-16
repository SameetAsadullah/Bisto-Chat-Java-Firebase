package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fragment_screen10 extends Fragment {
    RecyclerView recyclerView;
    screen10RVAdaptor adaptor;
    List<call> callList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen10, container, false);

        recyclerView = view.findViewById(R.id.rv_calls);

        callList = new ArrayList<>();
        callList.add(new call("Janet Fowler", "inbound", "10:21", false));
        callList.add(new call("Jason Boyd", "lost", "22:14", true));
        callList.add(new call("Nicholas Dunn", "inbound", "monday", false));
        callList.add(new call("Carol Clark", "lost", "friday", true));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adaptor = new screen10RVAdaptor(getActivity(), callList, fragment_screen10.this);
        recyclerView.setAdapter(adaptor);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((fragmentsContainer)getActivity()).changeImageColorToBlue(2);
    }

    public void applicationNotMinimized() {
        ((fragmentsContainer)getActivity()).minimized = false;
    }
}