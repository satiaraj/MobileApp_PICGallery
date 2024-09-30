package com.example.registrationpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPage extends AppCompatActivity {
    EditText userPageNama, userPageEmail, userPageTanggal, userPagePass;
    Button userPageUpdayeBtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    String UpdateUserNama, UpdateUserEmail, UpdateUserTanggal,UpdateUserPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance();
        reference= database.getReference("users");
        userPageNama = findViewById(R.id.userPageNama);
        userPagePass = findViewById(R.id.userPagePass);
        userPageEmail = findViewById(R.id.userPageEmail);
        userPageTanggal= findViewById(R.id.userPageTanggal);
        userPageUpdayeBtn= findViewById(R.id.userPageUpdayeBtn);

        showData();

        userPageUpdayeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gantiNama() || gantiEmail() || gantiDate() || gantiPass()){
                    Toast.makeText(UserPage.this, "Disimpan", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserPage.this, "Tidak tersimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean gantiEmail(){
        if(!UpdateUserEmail.equals(userPageEmail.getText().toString())){
            reference.child(UpdateUserNama).child("email").setValue(userPageEmail.getText().toString());
            UpdateUserEmail = userPageEmail.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean gantiPass(){
        if(!UpdateUserPass.equals(userPagePass.getText().toString())){
            reference.child(UpdateUserNama).child("pass").setValue(userPagePass.getText().toString());
            UpdateUserPass = userPagePass.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean gantiDate(){
        if(!UpdateUserTanggal.equals(userPageTanggal.getText().toString())){
            reference.child(UpdateUserNama).child("tanggal").setValue(userPageTanggal.getText().toString());
            UpdateUserTanggal = userPageTanggal.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean gantiNama(){
        if(!UpdateUserNama.equals(userPageNama.getText().toString())){
            reference.child(UpdateUserNama).child("nama").setValue(userPageNama.getText().toString());
            UpdateUserNama = userPageNama.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public void showData(){
        Intent terimaHalaman = getIntent();
        UpdateUserNama = terimaHalaman.getStringExtra("nama");
        UpdateUserEmail = terimaHalaman.getStringExtra("email");
        UpdateUserTanggal = terimaHalaman.getStringExtra("tanggal");
        UpdateUserPass = terimaHalaman.getStringExtra("pass");

        userPageNama.setText(UpdateUserNama);
        userPageEmail.setText(UpdateUserEmail);
        userPageTanggal.setText(UpdateUserTanggal);
        userPagePass.setText(UpdateUserPass);
    }




}