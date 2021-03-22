package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapters.UserAdapter;
import com.example.chatapp.Models.Users;
import com.example.chatapp.databinding.FragmentChatsFragmentsBinding;
import com.example.chatapp.databinding.FragmentUsersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    FragmentChatsFragmentsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    ArrayList<String>  senderReceiverRoom;
    UserAdapter adapter;

    public ChatsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentChatsFragmentsBinding.inflate(inflater, container, false);
        senderReceiverRoom = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        adapter = new UserAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        addSenderReceiverRoom();

        return binding.getRoot();
    }

    public void addUser()
    {
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        String senderRoom = users.getUserId() + FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String receiverRoom = FirebaseAuth.getInstance().getUid() + users.getUserId();

                        if (senderReceiverRoom.contains(senderRoom) || senderReceiverRoom.contains(receiverRoom))
                            list.add(users);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addSenderReceiverRoom()
    {

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        for (DataSnapshot roomSnapshot : snapshot.getChildren())
                        {
                                senderReceiverRoom.add(roomSnapshot.getKey());

                        }

                        addUser();
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
    }
}