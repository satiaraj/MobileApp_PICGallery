package com.example.registrationpage;

import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText MainNama, MainPass;
    Button RegisBut, mainSubmitBtnL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainNama = findViewById(R.id.MainNamaR2);
        MainPass = findViewById(R.id.MainPass);
        RegisBut = findViewById(R.id.RegisBut);
        mainSubmitBtnL = findViewById(R.id.mainSubmitBtnL);



        RegisBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindahIntent = new Intent(MainActivity.this, registerPage.class);
                startActivity(pindahIntent);
            }
        });

//        Intent terimaIntent = getIntent();
//        String NAMA =terimaIntent.getStringExtra("inputNama");
//        String PASS = terimaIntent.getStringExtra("inputPass");
        mainSubmitBtnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = MainNama.getText().toString();
                String Pass = MainPass.getText().toString();
//                if(MainNama.length()!=0 && MainPass.length()!=0){
//
//                }
                if(nama.isEmpty()|| Pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Tolong isi data dengan benar", Toast.LENGTH_SHORT).show();

                }
                else{
                    checkUser();

                }

//                Intent pindahIntent = new Intent(MainActivity.this, artGallery.class);
//                startActivity(pindahIntent);
//                if(MainPass.length()==0){
//                    MainPass.setError("Masukan Password!");
//                }
//                if(MainNama.length()==0){
//                    MainNama.setError("Masukan Nama!");
//
//                }

            }
        });

    }

    public Boolean ValidateUserName(){
        String val = MainNama.getText().toString();
        if(val.isEmpty()){
            MainNama.setError("Tolong diisi namanya");
            return false;
        }
        else{
            MainNama.setError(null);
            return true;
        }
    }

    public Boolean ValidatePassword(){
        String val = MainPass.getText().toString();
        if(val.isEmpty()){
            MainPass.setError("Tolong diisi namanya");
            return false;
        }
        else{
            MainPass.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String cekNama = MainNama.getText().toString().trim();
        String cekPass = MainPass.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("nama").equalTo(cekNama);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    MainNama.setError(null);
                    String PassfromDb = dataSnapshot.child(cekNama).child("pass").getValue(String.class);

                    if(PassfromDb.equals(cekPass)){
                        MainPass.setError(null);
                        String NamaFromDB = dataSnapshot.child(cekNama).child("nama").getValue(String.class);
                        String EmailFromDB = dataSnapshot.child(cekNama).child("email").getValue(String.class);
                        String DateFromDB = dataSnapshot.child(cekNama).child("tanggal").getValue(String.class);
                        Intent pindahHalaman = new Intent(MainActivity.this, artGallery.class);
                        pindahHalaman.putExtra("nama", NamaFromDB);
                        pindahHalaman.putExtra("pass", PassfromDb);
                        pindahHalaman.putExtra("email", EmailFromDB);
                        pindahHalaman.putExtra("tanggal", DateFromDB);
                        startActivity(pindahHalaman);
                        finish();
                    }
                    else{
                        MainPass.setError("password Salah");
                        MainPass.requestFocus();
                    }
                }
                else{
                    MainNama.setError("User tidak ditemukan");
                    MainNama.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}