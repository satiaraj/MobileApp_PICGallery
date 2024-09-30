package com.example.registrationpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryPage extends AppCompatActivity {
    CardView addPicG1;
    TextView pictureGalleryPage;
    RecyclerView pictureRecyclerView;
    ArrayList<DataClass> dataList;
    MyAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pictureGalleryPage = findViewById(R.id.pictureGalleryPage);
        Intent terimaHalaman = getIntent();
        String tes = terimaHalaman.getStringExtra("nama");
        pictureGalleryPage.setText(tes);
        pictureRecyclerView = findViewById(R.id.pictureRecyclerView);
        addPicG1 = findViewById(R.id.addPicG1);

        pictureRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList, this);
        pictureRecyclerView.setAdapter(adapter);
        Query checkUserGambar = databaseReference.orderByChild("nama").equalTo(tes);

        checkUserGambar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        addPicG1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilData();
            }
        });
    }

    public void ambilData(){
        String cekNama = pictureGalleryPage.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("nama").equalTo(cekNama);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namaFromDb = snapshot.child(cekNama).child("nama").getValue(String.class);
                if(namaFromDb.equals(cekNama)){
                    Intent pindahHalaman = new Intent(GalleryPage.this, addPicture.class);
                    pindahHalaman.putExtra("nama", namaFromDb);
                    startActivity(pindahHalaman);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}