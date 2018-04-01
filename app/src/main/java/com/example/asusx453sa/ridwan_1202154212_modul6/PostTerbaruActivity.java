package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostTerbaruActivity extends Fragment {
    private RecyclerView rcView;
    private RecyclerView.Adapter adapter;

    ProgressDialog progressDialog;
    private ArrayList<Post>list_post;
    DatabaseReference dbReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_post_terbaru, container, false);
        dbReference = FirebaseDatabase.getInstance().getReference(HomeActivity.table1);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        rcView = view.findViewById(R.id.rv_post_terbaru);
        list_post = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_post.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    list_post.add(post);
                }
                rcView.setHasFixedSize(true);
                rcView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                PostActivity postList = new PostActivity(getContext(), list_post);
                rcView.setAdapter(postList);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
