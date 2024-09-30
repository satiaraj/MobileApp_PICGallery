package com.example.registrationpage;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class registerPage extends AppCompatActivity {
    EditText MainNamaR2, MainPassR2, MainDateR2, MainEmailR2;
    Button MainSubmitBtnR2;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainNamaR2 = findViewById(R.id.MainNamaR2);
        MainPassR2 = findViewById(R.id.MainPassR2);
        MainDateR2 = findViewById(R.id.MainDateR2);
        MainEmailR2 = findViewById(R.id.MainEmailR2);
        MainSubmitBtnR2 = findViewById(R.id.MainSubmitBtnR2);

        MainDateR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int tahun =  calendar.get(Calendar.YEAR);
                int bulan = calendar.get(Calendar.MONTH);
                int hari = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pilihTangal = new DatePickerDialog(registerPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                                MainDateR2.setText(hari + "/" + (bulan + 1) + "/" + tahun);
                            }
                        }, tahun, bulan, hari);
                pilihTangal.show();
            }
        });

        MainSubmitBtnR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String nama = MainNamaR2.getText().toString();
                String Pass = MainPassR2.getText().toString();
                String Email = MainEmailR2.getText().toString();
                String Date = MainDateR2.getText().toString();
                HelperClass helperClass = new HelperClass(nama, Email, Pass, Date);
                reference.child(nama).setValue(helperClass);


                if (nama.isEmpty() || Pass.isEmpty() || Email.isEmpty() || Date.isEmpty()) {
                    Toast.makeText(registerPage.this, "Tolong Penuhi semua field yang tersedia", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent pindahIntent = new Intent(registerPage.this, MainActivity.class);
                    pindahIntent.putExtra("inputNama", nama);
                    pindahIntent.putExtra("inputPass", Pass);
                    pindahIntent.putExtra("inputEmail", Email);
                    pindahIntent.putExtra("inputDate", Date);
                    startActivity(pindahIntent);
                    finish();
                }
            }
        });
    }
}