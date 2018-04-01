package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PotoSayaActivity extends Fragment {
    private RecyclerView rcView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Post>list_post;
    Query dbQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_poto_saya, container, false);
        rcView = view.findViewById(R.id.rv_poto_saya);
        rcView.setHasFixedSize(true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        dbQuery = FirebaseDatabase.getInstance().getReference(HomeActivity.table1).orderByChild("userId").equalTo(mAuth.getUid());
        list_post = new ArrayList<>();
        return view;
    }

    public void onStart(){
        super.onStart();
        dbQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_post.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    list_post.add(post);
                }
                rcView.setLayoutManager(new GridLayoutManager(getContext(),2));
                PostActivity postList = new PostActivity(getContext(), list_post);
                rcView.setAdapter(postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
