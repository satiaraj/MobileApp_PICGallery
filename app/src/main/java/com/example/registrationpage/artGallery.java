package com.example.registrationpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class artGallery extends AppCompatActivity {
    CardView dasToUser, dasToGallery;
    EditText ArtNamaUserA1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_art_gallery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dasToUser = findViewById(R.id.dasToUser);
        dasToGallery = findViewById(R.id.dasToGallery);
        ArtNamaUserA1 = findViewById(R.id.ArtNamaUserA1);
        Intent terimaHalaman = getIntent();
        String NAMA = terimaHalaman.getStringExtra("nama");
        ArtNamaUserA1.setText(NAMA);

        dasToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilData();
            }
        });

        dasToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilData2();
            }
        });
    }

    public void AmbilData(){
        String cekNama = ArtNamaUserA1.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("nama").equalTo(cekNama);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String namaFromDb = dataSnapshot.child(cekNama).child("nama").getValue(String.class);
                if(namaFromDb.equals(cekNama)){
                    String emailFromDb = dataSnapshot.child(cekNama).child("email").getValue(String.class);
                    String tanggalFromDb = dataSnapshot.child(cekNama).child("tanggal").getValue(String.class);
                    String passFromDb = dataSnapshot.child(cekNama).child("pass").getValue(String.class);
                    Intent pindahHalaman = new Intent(artGallery.this, UserPage.class);
                    pindahHalaman.putExtra("nama", namaFromDb);
                    pindahHalaman.putExtra("email", emailFromDb);
                    pindahHalaman.putExtra("pass", passFromDb);
                    pindahHalaman.putExtra("tanggal", tanggalFromDb);
                    startActivity(pindahHalaman);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void AmbilData2(){
        String cekNama = ArtNamaUserA1.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("nama").equalTo(cekNama);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String namaFromDb = dataSnapshot.child(cekNama).child("nama").getValue(String.class);
                if(namaFromDb.equals(cekNama)){
                    Intent pindahHalaman = new Intent(artGallery.this, GalleryPage.class);
                    pindahHalaman.putExtra("nama", namaFromDb);
                    startActivity(pindahHalaman);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}