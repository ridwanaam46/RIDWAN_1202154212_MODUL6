package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    TextView tvusername, tvtitle, tvdesc;
    ImageView imagedetail;
    EditText comment;
    DatabaseReference dbComment;
    DatabaseReference dbUser;
    private RecyclerView rcView;
    FirebaseAuth mAuth;
    private ArrayList<Comment>list_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String username = intent.getStringExtra("username");
        String image = intent.getStringExtra("image");
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        dbComment = FirebaseDatabase.getInstance().getReference(HomeActivity.table2).child(id);
        dbUser = FirebaseDatabase.getInstance().getReference(HomeActivity.table3);

        rcView = findViewById(R.id.rc_detail);

        list_comment = new ArrayList<>();

        tvusername = findViewById(R.id.username_detail);
        tvtitle = findViewById(R.id.title_detail);
        tvdesc = findViewById(R.id.desc_detail);
        imagedetail = findViewById(R.id.image_detail);

        Glide.with(DetailActivity.this).load(image).into(imagedetail);

        comment = findViewById(R.id.comment_detail);

        tvusername.setText(username);
        tvtitle.setText(title);
        tvdesc.setText(desc);
    }

    public void post(View view) {
        dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                String textReview = comment.getText().toString().trim();
                if (!TextUtils.isEmpty(textReview)){
                    String id = dbComment.push().getKey();
                    Comment track = new Comment(id, user.getUsername(), textReview);
                    dbComment.child(id).setValue(track);
                    Toast.makeText(DetailActivity.this, "Comment Sent", Toast.LENGTH_LONG).show();
                    comment.setText("");
                }else {
                    Toast.makeText(DetailActivity.this, "Please enter comment", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        dbComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_comment.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Comment comment = postSnapshot.getValue(Comment.class);
                    list_comment.add(comment);
                }

                rcView.setHasFixedSize(true);
                rcView.setLayoutManager(new GridLayoutManager(DetailActivity.this, 1));
                CommentActivity commentList = new CommentActivity(DetailActivity.this, list_comment);
                rcView.setAdapter(commentList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
