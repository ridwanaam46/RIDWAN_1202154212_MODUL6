package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    EditText etitle, edesc;
    ImageView Image;
    Button choose;

    DatabaseReference databaseR;
    FirebaseAuth mAuth;
    private Uri imageUri;
    private StorageReference storageR;
    Query databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imageUri = null;
        mAuth = FirebaseAuth.getInstance();
        storageR = FirebaseStorage.getInstance().getReference().child("Image");
        databaseR = FirebaseDatabase.getInstance().getReference(HomeActivity.table1);
        databaseUser = FirebaseDatabase.getInstance().getReference(HomeActivity.table3);

        mAuth = FirebaseAuth.getInstance();

        etitle = (EditText) findViewById(R.id.title);
        edesc = (EditText) findViewById(R.id.desc);
        Image = (ImageView) findViewById(R.id.image);
        choose = (Button) findViewById(R.id.choose);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    public void choose(View view) {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);

                final String name = user.getUser();
                final String title = etitle.getText().toString();
                final String desc = edesc.getText().toString();
                final String id = databaseR.push().getKey();
                final String userId = mAuth.getUid();

                if (imageUri != null && !TextUtils.isEmpty(name)) {
                    final StorageReference image = storageR.child(id + ".jpg");
                    image.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {
                            if (uploadTask.isSuccessful()) {
                                String download_url = uploadTask.getResult().getDownloadUrl().toString();
                                Post post = new Post(id, userId, name, download_url, title, desc);
                                databaseR.child(id).setValue(post);
                            } else {
                                Toast.makeText(AddActivity.this, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(AddActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddActivity.this, "Please Fill the form and choose image", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            imageUri = data.getData();
            Image.setImageURI(imageUri);
        }
    }
}

