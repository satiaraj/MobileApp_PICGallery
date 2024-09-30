package com.example.registrationpage;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addPicture extends AppCompatActivity {
    Button addButtonAP;
    EditText addNamaPhoto, addDeskripsiPhot;
    ImageView addGambarGaller;
    Uri imageUri;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_picture);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addButtonAP = findViewById(R.id.addSumbitPageBtn);
        addNamaPhoto = findViewById(R.id.addNamaPhoto);
        addDeskripsiPhot = findViewById(R.id.addDeskripsiPhot);
        addGambarGaller = findViewById(R.id.addGambarGaller);



        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            addGambarGaller.setImageURI(imageUri);
                        }
                        else{
                            Toast.makeText(addPicture.this, "Tidak ada gambar", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        addGambarGaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pilihPhoto = new Intent();
                pilihPhoto.setAction(Intent.ACTION_GET_CONTENT);
                pilihPhoto.setType("image/*");
                activityResultLauncher.launch(pilihPhoto);
            }
        });

        addButtonAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null){
                    inputGamabar(imageUri);
                    cekNama();
                }
                else{
                    Toast.makeText(addPicture.this, "Gagal mengungah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cekNama(){
        Intent terimaHalaman = getIntent();
        String namaUser = terimaHalaman.getStringExtra("nama");
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference("users");
        Query cekuser = userref.orderByChild("nama").equalTo(namaUser);

        cekuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namaFromDb = snapshot.child(namaUser).child("nama").getValue(String.class);
                if(namaFromDb.equals(namaUser)){
                    Intent pindahHalaman = new Intent(addPicture.this, GalleryPage.class);
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

    public void inputGamabar(Uri uri){
        String namaGambar = addNamaPhoto.getText().toString();
        String deskripsiGambar = addDeskripsiPhot.getText().toString();
        final StorageReference refrensiGambar = storageReference.child(System.currentTimeMillis() + "," +getFileEXC(uri));
        Intent terimaHalaman = getIntent();
        String namaUser = terimaHalaman.getStringExtra("nama");
        refrensiGambar.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                refrensiGambar.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DataClass dataClass = new DataClass(uri.toString(), namaGambar, deskripsiGambar, namaUser);
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).setValue(dataClass);
                        Toast.makeText(addPicture.this, "Teruggah", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public String getFileEXC(Uri Fileuri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(Fileuri));
    }
}