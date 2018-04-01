package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    String email, password;
    private FirebaseAuth mAuth;
    DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        dbUser = FirebaseDatabase.getInstance().getReference(HomeActivity.table3);
    }

    public void Masuk(View view) {
        if (mEmail.getText().toString().length() == 0) {
            mEmail.setError("Required");
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (mPassword.getText().toString().length() == 0){
            mPassword.setError("Required");
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public void Daftar(View view) {
        if (mEmail.getText().toString().length() == 0) {
            mEmail.setError("Required");
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (mPassword.getText().toString().length() == 0){
            mPassword.setError("Required");
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser current = mAuth.getCurrentUser();
        if (current != null){
            Home();
        }
    }

    private void createAccount(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getUid();
                            String[] username = email.split("@");
                            User user = new User(id, username[0], email);
                            dbUser.child(id).setValue(user);
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {

                            Toast.makeText(LoginActivity.this, "Akun Belum Terdaftar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void Home(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
